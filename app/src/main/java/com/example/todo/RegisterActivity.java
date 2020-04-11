//Omkar Kulkarni 11925596
package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText email = null, pass = null;
    private Button reg=null;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_registration );
        FirebaseAuth mAuth;
        //wire the objects
        email = findViewById( R.id.regemail );
        pass = findViewById( R.id.regpass );
        reg = findViewById( R.id.register );
    }
    public void onRegister(View v)
    {
        final String em = email.getText().toString();
        final String pas = pass.getText().toString();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(em, pas)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                            Bundle bundle = new Bundle( );
                            bundle.putString( "username", em );
                            bundle.putString( "password", pas);
                            intent.putExtras( bundle);
                            Toast.makeText(RegisterActivity.this, "Registration Successful",
                                    Toast.LENGTH_SHORT).show();
                            startActivity( intent );


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    public void onLogin(View v)
    {
        //Intent to login page
        Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
        startActivity( intent );

    }
}
