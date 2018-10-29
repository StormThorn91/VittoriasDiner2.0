package com.sistosotoledoorcullo.vittorasdiner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonLogin, buttonRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonLogin = (Button)findViewById(R.id.buttonMainActivityLogin);
        buttonRegistration = (Button)findViewById(R.id.buttonMainActivityRegistration);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selfIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(selfIntent);
                finish();
                return;
            }
        });

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selfIntent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(selfIntent);
                finish();
                return;
            }
        });

    }
}
