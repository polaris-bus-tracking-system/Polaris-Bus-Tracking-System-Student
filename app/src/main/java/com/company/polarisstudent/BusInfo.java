package com.company.polarisstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BusInfo extends AppCompatActivity {
    DatabaseReference busReference;
    TextView textView;
    Button btnTrack;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BusInfo.this,StudentHomepage.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        busReference = FirebaseDatabase.getInstance().getReference().child("Bus");
        String busID = getIntent().getStringExtra("busID");

        busReference.child("busID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BusModel bus = snapshot.getValue(BusModel.class);

                if(bus != null){
                    String busno = bus.busno;
                    String busroute = bus.busroute;
                    String driverid = bus.driverid;
                    String stops = bus.stops;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnTrack = findViewById(R.id.btnTrack);

        btnTrack.setOnClickListener(view -> {
            startActivity(new Intent(this,Tracking.class));
        });


    }
}