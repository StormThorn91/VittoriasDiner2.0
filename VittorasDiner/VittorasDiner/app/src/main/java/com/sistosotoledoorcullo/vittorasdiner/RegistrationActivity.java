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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private String stringEmail, stringPassword, stringConfirmPassword;
    private Button buttonRegister, buttonLoginIntent;
    private FirebaseAuth selfAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser fbUser;
    private Intent selfIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextEmail = (EditText)findViewById(R.id.editTextRegistrationActivityEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextRegistrationActivityPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextRegistrationActivityConfirmPassword);

        buttonRegister = (Button)findViewById(R.id.buttonRegistrationActivityRegister);
        buttonLoginIntent = (Button)findViewById(R.id.buttonRegistrationActivityLoginIntent);

        selfAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                fbUser = FirebaseAuth.getInstance().getCurrentUser();
                if(fbUser != null){
                    selfIntent = new Intent(RegistrationActivity.this, TransactionViewActivity.class);
                    startActivity(selfIntent);
                    finish();
                    return;
                }
            }
        };

        buttonLoginIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selfIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(selfIntent);
                finish();
                return;
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    stringEmail = editTextEmail.getText().toString();
                    stringPassword = editTextPassword.getText().toString();
                    stringConfirmPassword = editTextConfirmPassword.getText().toString();
                    selfAuth.createUserWithEmailAndPassword(stringEmail, stringPassword).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(RegistrationActivity.this, "Errol", Toast.LENGTH_SHORT).show();
                            }else if(stringPassword != stringConfirmPassword){
                                Toast.makeText(RegistrationActivity.this, "Errol", Toast.LENGTH_SHORT).show();
                            }else{
                                String user_id = selfAuth.getCurrentUser().getUid();
                                DatabaseReference currentUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("UserID").child(user_id);
                                currentUserDatabase.setValue(true);
                            }
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(RegistrationActivity.this, "Errol", Toast.LENGTH_SHORT).show();
                }
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
