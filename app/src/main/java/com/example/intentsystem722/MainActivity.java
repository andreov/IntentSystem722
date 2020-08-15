package com.example.intentsystem722;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText mEditTextPhone;
    private Button mBtnPhone;
    private EditText mEditTextSMS;
    private Button mBtnSMS;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SMS =22;
    private String textPhone;
    private String textSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditTextPhone=findViewById(R.id.editTextPhone);
        mBtnPhone=findViewById(R.id.btnPhone);
        mEditTextSMS=findViewById(R.id.editTextSMS);
        mBtnSMS=findViewById(R.id.btnSMS);


        mBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPhone=mEditTextPhone.getText().toString();
                myCall(textPhone);
            }
        });

        mBtnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textPhone=mEditTextPhone.getText().toString();
                textSMS=mEditTextSMS.getText().toString();
                mySMS(textPhone,textSMS);

            }
        });
    }

    public void myCall(String textPhone){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// Разрешение не получено
// Делаем запрос на добавление разрешения звонка
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
// Разрешение уже получено
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textPhone));
            startActivity(dialIntent);
        }
    }

    public void mySMS(String textPhone,String textSMS){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SMS);
        } else {
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(textPhone,null,textSMS,null,null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
// Проверяем результат запроса на право позвонить
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textPhone));
                    startActivity(dialIntent);

                } else {
// Разрешение не дано. Закрываем приложение
                    finish();
                }
            }
            case MY_PERMISSIONS_REQUEST_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smgr = SmsManager.getDefault();
                    smgr.sendTextMessage(textPhone,null,textSMS,null,null);

                } else {
                    finish();
                }
            }
        }
    }
}