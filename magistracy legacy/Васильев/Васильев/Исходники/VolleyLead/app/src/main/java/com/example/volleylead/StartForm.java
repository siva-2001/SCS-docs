package com.example.volleylead;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartForm extends AppCompatActivity {
    EditText pName, pSurname, pFName;
    String protocolist;
    private static final int PERMISSION_STORAGE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_form);
        pName = findViewById(R.id.pName);
        pSurname = findViewById(R.id.pSurname);
        pFName = findViewById(R.id.pFname);
        if (PermissionUtils.hasPermissions(StartForm.this)) return;
        PermissionUtils.requestPermissions(StartForm.this, PERMISSION_STORAGE);

    }
    public void nextAct(View view){
        protocolist = pName.getText().toString() + " " + pSurname.getText().toString() + " " + pFName.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("prName", protocolist);
        startActivity(intent);
    }
}