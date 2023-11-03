package com.nc.simplecontactstatusapp;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    static Context cnt;
    PhoneCallReceiver phoneCallReceiver = null;
    int requestCode = 200;
    SharedPreferences sharedPreferences;
    final static String sPFileName = "myFile";
    TextInputEditText statusTV;
    MaterialButton saveBtn;
    ToggleButton toggleButton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        statusTV = findViewById(R.id.msg_textview);
        saveBtn = findViewById(R.id.save_btn);
        toggleButton1 = findViewById(R.id.toggleButton);
        sharedPreferences = getSharedPreferences(sPFileName, MODE_PRIVATE);
        cnt = this;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, requestCode);
        } else {
            // Permission is already granted;
            phoneCallReceiver = new PhoneCallReceiver();

        }
        toggleButton1.setChecked(true);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(phoneCallReceiver, intentFilter);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = statusTV.getText().toString();
                if (msg == "" || msg.length() < 3)
                    Toast.makeText(MainActivity.this, " Enter valid input", Toast.LENGTH_SHORT).show();
                else {
                    saveData(msg);
                }
            }
        });
        toggleButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleButton1.isChecked()) {
                    setToggleStatus(true);
                } else {
                    setToggleStatus(false);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == this.requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is  granted; you can proceed with your logic
//                phoneCallReceiver = new PhoneCallReceiver();

            } else {
                // Permission is denied
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(phoneCallReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleButton1.setChecked(getToggleStatus());
    }

    void saveData(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("status_meg", text);
        editor.apply();
        Toast.makeText(cnt, "Message saved", Toast.LENGTH_SHORT).show();
    }

    Boolean getToggleStatus() {
        if (sharedPreferences.contains("status1"))
            return sharedPreferences.getBoolean("status1", false);
        else
            return false;
    }

    void setToggleStatus(boolean b) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("status1", b);
        editor.apply();
    }


}

