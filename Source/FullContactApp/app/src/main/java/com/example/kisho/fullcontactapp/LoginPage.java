package com.example.kisho.fullcontactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    Button LoginPageActivity_LoginButton;
    EditText LoginPageActivity_UserName, LoginPageActivity_Password;
    TextView LoginPageActivity_DisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        LoginPageActivity_UserName = (EditText)findViewById(R.id.LoginPageContent_UserName);
        LoginPageActivity_Password = (EditText)findViewById(R.id.LoginPageContent_Password);
        LoginPageActivity_LoginButton = (Button)findViewById(R.id.LoginPageContent_LoginButton);
        LoginPageActivity_DisplayText = (TextView)findViewById(R.id.LoginPageContent_DisplayText);

        LoginPageActivity_LoginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.LoginPageContent_LoginButton:
                if(LoginPageActivity_UserName.getText().toString().equals("Admin") && LoginPageActivity_Password.getText().toString().equals("Admin")) {
                    startActivity(new Intent(LoginPage.this, ConvertPage.class));

                }
                else {
                    LoginPageActivity_DisplayText.setVisibility(View.VISIBLE);
                    LoginPageActivity_DisplayText.setText("User Credentials are invalid");
                    LoginPageActivity_Password.setText(null);

                }


                break;
        }
    }




}
