package com.example.moviesearcha3.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviesearcha3.databinding.ActivityLoginBinding;
import com.example.moviesearcha3.model.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        //login event
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.emailET.getText().toString().trim();
            String password = binding.passwordET.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please enter email and password",Toast.LENGTH_SHORT).show();
                return;
            }
        // Firebase login
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show();
                        // after successful login to main page
                        startActivity(new Intent(this, Movie.class));
                        finish();
                    }else {
                        Toast.makeText(this,"Login Failed: "+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
        });

        //jump to register page
        binding.registerLink.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });


    }


}
