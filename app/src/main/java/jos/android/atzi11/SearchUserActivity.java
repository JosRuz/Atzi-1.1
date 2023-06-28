package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchUserActivity extends AppCompatActivity {

    EditText txt_buscar_usuario;
    ImageButton btn_buscar;
    ImageButton btn_atras;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        txt_buscar_usuario=findViewById(R.id.buscar_usuario_edittext);
        btn_buscar=findViewById(R.id.btn_buscar_usuario);
        btn_atras=findViewById(R.id.btn_atras);
        recyclerView=findViewById(R.id.buscar_usuario_recycler);

        txt_buscar_usuario.requestFocus();

        btn_atras.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_buscar.setOnClickListener(v -> {
            String searchTerm=txt_buscar_usuario.getText().toString();
            if (searchTerm.isEmpty() || searchTerm.length()<3){
                txt_buscar_usuario.setError("Usuario no valido");
                return;
            }
            prepararBusquedaRecycler(searchTerm);
        });

    }

    private void prepararBusquedaRecycler(String searchTerm) {

    }
}