package com.company.polarisstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    private TextView tvStudentName , tvStudentEmail , tvBusStop , tvEnroll;
    private ImageView studentImage;
    private Button btnLogout;

    private FirebaseUser user;
    private DatabaseReference reference;

    String studentID;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentProfile.this,StudentHomepage.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        tvStudentName = findViewById(R.id.tvStudentName);
        tvStudentEmail = findViewById(R.id.tvStudentEmail);
        tvBusStop = findViewById(R.id.tvBusStop);
        tvEnroll = findViewById(R.id.tvEnroll);

        studentImage = findViewById(R.id.studentImage);

        btnLogout = findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener( view -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StudentProfile.this,StudentLogin.class));
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("Students");
        studentID = user.getUid();

        reference.child(studentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentModel student = snapshot.getValue(StudentModel.class);

                if(student != null){
                    String name = student.name;
                    String enroll = student.enroll;
                    String email = student.email;
                    String imgurl = student.imgurl;
                    String busstop = student.busstop;

                    tvStudentName.setText(name);
                    tvBusStop.setText(busstop);
                    tvStudentEmail.setText(email);
                    tvEnroll.setText(enroll);

                    Glide.with(StudentProfile.this).load(imgurl).placeholder(R.drawable.profileicon)
                            .into(studentImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}