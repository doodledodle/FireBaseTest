package com.dodlebee.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dodlebee.firebasetest.authentication.LoginActivity;
import com.dodlebee.firebasetest.cloudfirestore.CloudFireStore;
import com.dodlebee.firebasetest.dynamiclinks.DynamicLinks;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    private Button buttonDynamicLinks;
    private Button buttonGoogleLogin;
    private Button buttonCloudFireStore;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = GoogleSignIn.getLastSignedInAccount(this);

        buttonDynamicLinks = (Button)findViewById(R.id.button_DynamicLinks);
        buttonDynamicLinks.setOnClickListener(on_ButtonClick);

        buttonGoogleLogin = (Button)findViewById(R.id.button_GoogleLogin);
        buttonGoogleLogin.setOnClickListener(on_ButtonClick);

        buttonCloudFireStore = (Button)findViewById(R.id.button_CloudFireStore);
        buttonCloudFireStore.setOnClickListener(on_ButtonClick);
    }

    View.OnClickListener on_ButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (account!=null){
                if (view.getId() == buttonDynamicLinks.getId()) {
                    createDynamicLinks();
                } else if (view.getId() == buttonGoogleLogin.getId()) {
                    createLoginActivity();
                } else if (view.getId() == buttonCloudFireStore.getId()) {
                    createFireStoreActivity();
                }
            } else {
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
        bundle.putString("Title","구글로그인");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createFireStoreActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","파이어스토어");
        Intent intent = new Intent(MainActivity.this, CloudFireStore.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}