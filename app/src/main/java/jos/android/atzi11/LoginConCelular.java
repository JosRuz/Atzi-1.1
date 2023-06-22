package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hbb20.CountryCodePicker;

public class LoginConCelular extends AppCompatActivity {

    CountryCodePicker codigoDePais;
    EditText numeroCelular;
    Button enviarCodigoBtn;
    ProgressBar barraProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_con_celular);

        codigoDePais=findViewById(R.id.txt_codigopais);
        numeroCelular=findViewById(R.id.txt_numero_telefono);
        enviarCodigoBtn=findViewById(R.id.btn_enviar_codigo);
        barraProgreso=findViewById(R.id.pb_progreso_enviar_codigo);

        barraProgreso.setVisibility(View.GONE);

        codigoDePais.registerCarrierNumberEditText(numeroCelular);
        enviarCodigoBtn.setOnClickListener((v)->{
            if (!codigoDePais.isValidFullNumber()){

                numeroCelular.setError("Numero de celular no valido");
                return;

            }
            Intent intent=new Intent(LoginConCelular.this, loginconOTP.class);
            intent.putExtra("numero",codigoDePais.getFullNumberWithPlus());

            startActivity(intent);
        });

    }
}