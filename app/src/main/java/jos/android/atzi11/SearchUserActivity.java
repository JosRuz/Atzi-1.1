package jos.android.atzi11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import jos.android.atzi11.adapter.SearchUserRecyclerAdapter;
import jos.android.atzi11.model.UserModel;
import jos.android.atzi11.utils.FirebaseUtil;

public class SearchUserActivity extends AppCompatActivity {

    EditText txt_buscar_usuario;
    ImageButton btn_buscar;
    ImageButton btn_atras;
    RecyclerView recyclerView;

    SearchUserRecyclerAdapter adapter;

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

        Query query= FirebaseUtil.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("username",searchTerm);

        FirestoreRecyclerOptions<UserModel> options=new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query,UserModel.class).build();

        adapter=new SearchUserRecyclerAdapter(options,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter!=null) {
            adapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter!=null) {
            adapter.startListening();
        }
    }
}