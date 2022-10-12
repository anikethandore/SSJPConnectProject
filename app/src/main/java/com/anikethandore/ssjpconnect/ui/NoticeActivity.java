package com.anikethandore.ssjpconnect.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.anikethandore.ssjpconnect.ModelSourceListNotice;
import com.anikethandore.ssjpconnect.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class NoticeActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    adapterUserNotice adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        recyclerView =findViewById(R.id.nrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ModelSourceListNotice> options =
                new FirebaseRecyclerOptions.Builder<ModelSourceListNotice>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("notics"), ModelSourceListNotice.class)
                        .build();
        adapter=new adapterUserNotice(options);
        recyclerView.setAdapter(adapter);

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