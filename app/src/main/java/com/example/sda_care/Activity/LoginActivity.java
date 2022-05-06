package com.example.sda_care.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sda_care.MainActivity;
import com.example.sda_care.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증처리

    private Button loginButton;
    private Button registerButton;
    private EditText editTextLoginId;
    private EditText editTextLoginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mFirebaseAuth = FirebaseAuth.getInstance();
        editTextLoginId = (EditText) findViewById(R.id.editTextLoginId);
        editTextLoginPwd = (EditText) findViewById(R.id.editTextLoginPwd);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton.setOnClickListener(view -> onClickLoginButton());
        registerButton.setOnClickListener(view -> onClickRegisterButton());
    }

    private void onClickLoginButton() {
        // 로그인 버튼 클릭했을 때 이벤트 처리...
        String id = editTextLoginId.getText().toString();
        String pwd = editTextLoginPwd.getText().toString();

        if (id.length() == 0) {
            Toast.makeText(LoginActivity.this,"아이디를 입력하세요!",Toast.LENGTH_SHORT).show();
            return;
        } else if (pwd.length() == 0) {
            Toast.makeText(LoginActivity.this,"비밀번호를 입력하세요!",Toast.LENGTH_SHORT).show();
            return;
        }
        id += "@sda.com";

        mFirebaseAuth.signInWithEmailAndPassword(id, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onClickRegisterButton() {
        // 회원가입 버튼 클릭했을 때 이벤트 처리...
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}