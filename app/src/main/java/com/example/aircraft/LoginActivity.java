package com.example.aircraft;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraft.conn.DAO.User;
import com.example.aircraft.conn.service.Impl.UserServiceImpl;
import com.example.aircraft.conn.service.UserService;


public class LoginActivity extends AppCompatActivity {
    public static final String settingsPath = "userSettings";
    EditText edit_name,edit_pwd;
    CheckBox check_remember;
    Button btn_login;
    Button btn_register;
    UserService userService = new UserServiceImpl();

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_name=findViewById(R.id.editText_inputname);
        edit_pwd=findViewById(R.id.editText_inputpwd);
        check_remember=findViewById(R.id.checkBox_reme);
        btn_login=findViewById(R.id.button_login);
        btn_register = findViewById(R.id.button_register);

        LoginHandler loginHandler = new LoginHandler();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = edit_name.getText().toString();
                String inputPwd = edit_pwd.getText().toString();
                boolean isSaved = check_remember.isChecked();

                new Thread(() -> {
                    Message msg = Message.obtain();
                    boolean loginSuccess = userService.verifyUser(new User(inputName, inputPwd));
                    if(loginSuccess) {
                        saveInput(inputName,inputPwd,isSaved);
                        saveUserId(userService.getUserIdByName(inputName));
                        msg.what = 1;
                    }
                    else {
                        msg.what = 2;
                    }
                    loginHandler.sendMessage(msg);
                }).start();

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = edit_name.getText().toString();
                String inputPwd = edit_pwd.getText().toString();
                boolean isSaved = check_remember.isChecked();
                new Thread(() -> {
                    Message msg = Message.obtain();
                    boolean registerSuccess = userService.insertUser(new User(inputName, inputPwd));
                    if(registerSuccess) {
                        saveInput(inputName,inputPwd,isSaved);
                        saveUserId(userService.getUserIdByName(inputName));
                        msg.what = 3;
                    }
                    else {
                        msg.what = 4;
                    }
                    loginHandler.sendMessage(msg);
                }).start();
            }
        });
        loadPastInput();
    }

    public void saveInput(String name, String pwd, boolean isSaved) {
        SharedPreferences.Editor editor=getSharedPreferences(settingsPath,0).edit();
        editor.putString("name",name);
        editor.putString("pwd",pwd);
        editor.putBoolean("isSaved",isSaved);
        editor.commit();
    }
    public void saveUserId(int userId) {
        SharedPreferences.Editor editor=getSharedPreferences(settingsPath,0).edit();
        editor.putInt("userId",userId);
        editor.commit();
    }

    public void loadPastInput() {
        SharedPreferences sharedPreferences = getSharedPreferences(settingsPath, 0);
        String name = sharedPreferences.getString("name","");
        String pwd = sharedPreferences.getString("pwd","");
        boolean isSaved = sharedPreferences.getBoolean("isSaved",false);
        check_remember.setChecked(isSaved);
        if(isSaved) {
            edit_name.setText(name);
            edit_pwd.setText(pwd);
        } else {
            edit_name.setText("");
            edit_pwd.setText("");
        }
    }

    class LoginHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //loginSuccess
                case 1: {
                    Log.i("database connect","login success");
                    Toast.makeText(LoginActivity.this,"WelCome!",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.MenuActivity"));
                    startActivity(intent);
                    finish();
                    break;
                }
                //loginFailure
                case 2: {
                    Toast.makeText(LoginActivity.this,"Login failure! Please check your userName and password again.",Toast.LENGTH_LONG).show();
                    Log.i("database connect:","login failure");
                    break;
                }
                //registerSuccess
                case 3: {
                    Toast.makeText(LoginActivity.this,"WelCome!",Toast.LENGTH_LONG).show();
                    Log.i("database connect","register success");

                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("com.example.aircraft", "com.example.aircraft.MenuActivity"));
                    startActivity(intent);
                    finish();
                }
                //registerFailure
                case 4: {
                    Log.i("database connect","register failure");
                    Toast.makeText(LoginActivity.this,"Register Failure. Please change your userName.",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
