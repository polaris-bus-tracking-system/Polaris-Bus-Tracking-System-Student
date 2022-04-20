package com.company.polarisstudent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;


public class StudentHomepage extends AppCompatActivity {


    FloatingActionButton btnProfile;
    RecyclerView recyclerView;
    BusAdapter busAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homepage);

        btnProfile = findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(view -> {
            startActivity(new Intent(StudentHomepage.this,StudentProfile.class));
        });

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<BusModel> options =
                new FirebaseRecyclerOptions.Builder<BusModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Bus"), BusModel.class)
                        .build();

        busAdapter = new BusAdapter(options);
        busAdapter.startListening();
        recyclerView.setAdapter(busAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        busAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        busAdapter.stopListening();
    }
}