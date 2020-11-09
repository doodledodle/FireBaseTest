package com.dodlebee.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dodlebee.firebasetest.authentication.LoginActivity;
import com.dodlebee.firebasetest.dynamiclinks.DynamicLinks;

public class MainActivity extends AppCompatActivity {

    private Button buttonDynamicLinks;
    private Button buttonGoogleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonDynamicLinks = (Button)findViewById(R.id.button_DynamicLinks);
        buttonDynamicLinks.setOnClickListener(on_ButtonClick);

        buttonGoogleLogin = (Button)findViewById(R.id.button_GoogleLogin);
        buttonGoogleLogin.setOnClickListener(on_ButtonClick);

    }

    View.OnClickListener on_ButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == buttonDynamicLinks.getId()) {
                createDynamicLinks();
            } else if (view.getId() == buttonGoogleLogin.getId()) {
                createLoginActivity();
            }
        }
    };

    private void createDynamicLinks() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","다이나믹링크");
        Intent intent = new Intent(MainActivity.this, DynamicLinks.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createLoginActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","다이나믹링크");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}