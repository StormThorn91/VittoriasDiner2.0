package com.sistosotoledoorcullo.vittorasdiner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin, buttonRegistrationIntent;

    private FirebaseAuth selfAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private FirebaseUser fbUser;

    private Intent selfIntent;

    private String stringEmail, stringPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText)findViewById(R.id.editTextLoginActivityEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextLoginActivityPassword);

        buttonLogin = (Button)findViewById(R.id.buttonLoginActivityLogin);
        buttonRegistrationIntent = (Button)findViewById(R.id.buttonLoginActivityRegistrationLink);

        selfAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                fbUser = FirebaseAuth.getInstance().getCurrentUser();
                if(fbUser != null){
                    selfIntent = new Intent(LoginActivity.this, TransactionViewActivity.class);
                    startActivity(selfIntent);
                    finish();
                    return;
                }
            }
        };

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    stringEmail = editTextEmail.getText().toString();
                    stringPassword = editTextPassword.getText().toString();
                    selfAuth.signInWithEmailAndPassword(stringEmail, stringPassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Sign in error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Errol", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegistrationIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(selfIntent);
                finish();
                return;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        selfAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        selfAuth.addAuthStateListener(firebaseAuthListener);
    }
}
