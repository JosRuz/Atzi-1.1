package jos.android.atzi11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import jos.android.atzi11.model.UserModel;
import jos.android.atzi11.utils.FirebaseUtil;

public class LoginNombreUsu extends AppCompatActivity {

    EditText txt_nombreusuario;
    Button btn_entrar;
    ProgressBar pb_entrar;
    String numeroCelular;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_nombre_usu);

        txt_nombreusuario=findViewById(R.id.txt_username);
        btn_entrar=findViewById(R.id.btn_entrar);
        pb_entrar=findViewById(R.id.pb_progreso_entrar);

        numeroCelular= getIntent().getExtras().getString("numero");
        obtenerUsuario();

        btn_entrar.setOnClickListener(v -> {
            setUsername();
        });

    }

    void setUsername(){

        String username=txt_nombreusuario.getText().toString();
        if (username.isEmpty() || username.length()<3){
            txt_nombreusuario.setError("Nombre de usuario debería ser mayor a 3 caracteres");
            return;
        }

        ponerEnProgreso(true);
        if (userModel!=null){
            userModel.setUsername(username);
        }else {
            userModel=new UserModel(numeroCelular,username, Timestamp.now(),FirebaseUtil.currentUserId());
        }
        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(task -> {
            ponerEnProgreso(false);
            if (task.isSuccessful()){
                Intent intent=new Intent(LoginNombreUsu.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void obtenerUsuario() {
        ponerEnProgreso(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ponerEnProgreso(false);
                if (task.isSuccessful()){
                    userModel = task.getResult().toObject(UserModel.class);
                    if (userModel!=null){
                        txt_nombreusuario.setText(userModel.getUsername());
                    }
                }
            }
        });
    }

    void ponerEnProgreso(boolean enProgreso){

        if (enProgreso){
            pb_entrar.setVisibility(View.VISIBLE);
            btn_entrar.setVisibility(View.GONE);
        }else {
            pb_entrar.setVisibility(View.GONE);
            btn_entrar.setVisibility(View.VISIBLE);
        }

    }

}