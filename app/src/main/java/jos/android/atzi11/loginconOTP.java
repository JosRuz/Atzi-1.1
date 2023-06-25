package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class loginconOTP extends AppCompatActivity {

    String numeroCelular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincon_otp);

        numeroCelular=getIntent().getExtras().getString("numero");
        Toast.makeText(this, numeroCelular, Toast.LENGTH_LONG).show();

        //comentario inutil para mantener mi racha

    }
}