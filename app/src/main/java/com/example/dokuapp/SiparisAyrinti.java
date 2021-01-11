package com.example.dokuapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dokuapp.Adapters.SiparişAyrintiAdapter;
import com.example.dokuapp.Values.SepetUrun;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SiparisAyrinti extends AppCompatActivity {

    private ImageButton kopyala, onayla;
    private TextView total;
    private RecyclerView recyclerView;
    String siparisId;
    SiparişAyrintiAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ayrinti);

        kopyala = findViewById(R.id.kopyala);
        onayla = findViewById(R.id.siparisionayla);
        total = findViewById(R.id.sepettoplamfiyat);
        recyclerView = findViewById(R.id.recyclerview);

        /*String siparişno = getIntent().getExtras().getString("Sipariş Numarası");
        String sipariştarihi = getIntent().getExtras().getString("Sipariş Tarihi");
        String toplamtutar = getIntent().getExtras().getString("Toplam Tutar");
        String siparişdurumu = getIntent().getExtras().getString("Sipariş Durumu");*/

        siparisId = getIntent().getExtras().getString("Sipariş Numarası");




        Query query = FirebaseFirestore.getInstance().collection("Kullanıcılar").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Siparişler").document(siparisId)
                .collection("Ürünler").orderBy("sepetUrunAdi");

        FirestoreRecyclerOptions<SepetUrun> options = new FirestoreRecyclerOptions.Builder<SepetUrun>()
                .setQuery(query, SepetUrun.class)
                .build();

        adapter = new SiparişAyrintiAdapter(options);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        Log.d("sdf", String.valueOf(siparisId));

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}