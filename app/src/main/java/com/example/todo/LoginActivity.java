package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView username=null, password=null;
    private Button login=null;
    private FirebaseAuth mAuth;
    public void onReg(View v)
    {
        //Intent for Registering page
        Intent intent = new Intent( LoginActivity.this,RegisterActivity.class );
        startActivity( intent );
    }
    public void validate(View v){

        if(username.getText().equals( "" ) || password.getText().equals( "" ))
        {
            Toast.makeText( getApplicationContext(),"All fields are mandatory",Toast.LENGTH_SHORT ).show();
        }
        else {
            final String un = username.getText().toString();
            final String pass = password.getText().toString();
            Log.d( "----",un+"--"+pass+"");
            //validation and login of user data
            mAuth.signInWithEmailAndPassword( un, pass )
                    .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent( LoginActivity.this, todo.class );
                                Bundle bundle = new Bundle();
                                bundle.putString( "username", un );
                                bundle.putString( "password", pass );
                                Toast.makeText( getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT ).show();

                                intent.putExtras( bundle );
                                startActivity( intent );
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText( LoginActivity.this, "Invalid Credential's", Toast.LENGTH_SHORT ).show();

                            }

                        }
                    } );
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //wire the device
        username = findViewById( R.id.username );
        password = findViewById( R.id.password );
        login = findViewById( R.id.login );

    }

}