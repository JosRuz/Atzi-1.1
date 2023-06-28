package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import jos.android.atzi11.model.UserModel;
import jos.android.atzi11.utils.AndroidUtil;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser;

    EditText messagelnput;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //obtener modelo de usuario
        otherUser= AndroidUtil.getUsermodelFromInten(getIntent());

        messagelnput = findViewById(R.id.txt_mensaje_chat_entrada);
        sendMessageBtn=findViewById(R.id.btn_enviar_mensaje);
        backBtn=findViewById(R.id.btn_atras);
        otherUsername=findViewById(R.id.other_username);
        recyclerView=findViewById(R.id.chat_recycler_view);

        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        otherUsername.setText(otherUser.getUsername());
    }
}