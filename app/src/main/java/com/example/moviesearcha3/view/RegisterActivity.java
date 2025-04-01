package com.example.moviesearcha3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesearcha3.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // register event
        binding.registerBtn.setText("Register");
        binding.registerBtn.setOnClickListener(v -> {
            String email = binding.registerEmailET.getText().toString().trim();
            String password = binding.registerPasswordET.getText().toString().trim();

            if(email.isEmpty()|| password.isEmpty()){
                Toast.makeText(this,"Please fill in both email and password.",Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(this,"Registration is successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this,LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(this,"Registration failed: " +task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
        });


        // already have account back to login page
        binding.registerLink.setText("Already have an account? Login");
        binding.registerLink.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });
    }


}
