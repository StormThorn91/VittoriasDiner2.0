package com.sistosotoledoorcullo.vittorasdiner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TransactionViewActivity extends AppCompatActivity {

    private Button buttonLogout;

    private FirebaseAuth selfAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser fbUser;
    private Intent selfIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        buttonLogout = (Button)findViewById(R.id.buttonTransactionViewLogout);
        selfAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                fbUser = FirebaseAuth.getInstance().getCurrentUser();
                if(fbUser != null){
                    selfIntent = new Intent(TransactionViewActivity.this, MainActivity.class);
                    startActivity(selfIntent);
                    finish();
                    return;
                }
            }
        };

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                selfIntent = new Intent(TransactionViewActivity.this, MainActivity.class);
                startActivity(selfIntent);
                finish();
                return;
            }
        });
    }
}
