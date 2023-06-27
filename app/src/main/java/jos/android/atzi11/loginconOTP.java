package jos.android.atzi11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import jos.android.atzi11.utils.AndroidUtil;

public class loginconOTP extends AppCompatActivity {

    String numeroCelular;
    Long timeoutSeconds=60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;

    EditText otpEntrada;
    Button siguiente;
    TextView reenviarOTP;
    ProgressBar progresoOTP;
    FirebaseAuth mAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logincon_otp);

        otpEntrada=findViewById(R.id.txt_codigo_celular);
        siguiente=findViewById(R.id.btn_codigo_ingresado);
        progresoOTP=findViewById(R.id.pb_progreso_verificar_codigo);
        reenviarOTP=findViewById(R.id.lbl_reenviar_codigo);

        numeroCelular=getIntent().getExtras().getString("numero");

        enviarOtp(numeroCelular,false);

        siguiente.setOnClickListener(v -> {
            String otpIngresado=otpEntrada.getText().toString();
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,otpIngresado);

            signIn(credential);
            ponerEnProgreso(true);
        });

    }

    void enviarOtp(String numeroCelular,boolean seReenvio){

        ponerEnProgreso(true);
        PhoneAuthOptions.Builder builder=
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(numeroCelular)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                ponerEnProgreso(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                AndroidUtil.showToast(getApplicationContext(),"Verificación fallida");
                                ponerEnProgreso(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode=s;
                                resendingToken=forceResendingToken;
                                AndroidUtil.showToast(getApplicationContext(),"Código enviado");
                                ponerEnProgreso(false);
                            }
                        });
        if (seReenvio){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }

    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {

        ponerEnProgreso(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                ponerEnProgreso(false);
                if (task.isSuccessful()){
                    Intent intent=new Intent(loginconOTP.this, LoginNombreUsu.class);
                    intent.putExtra("numero",numeroCelular);
                    startActivity(intent);
                }else {
                    AndroidUtil.showToast(getApplicationContext(),"Verificación fallida");
                }
            }
        });

    }

    void ponerEnProgreso(boolean enProgreso){

        if (enProgreso){
            progresoOTP.setVisibility(View.VISIBLE);
            siguiente.setVisibility(View.GONE);
        }else {
            progresoOTP.setVisibility(View.GONE);
            siguiente.setVisibility(View.VISIBLE);
        }

    }

}