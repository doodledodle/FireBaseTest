package com.dodlebee.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dodlebee.firebasetest.KaKao.KaKaoMap;
import com.dodlebee.firebasetest.authentication.LoginActivity;
import com.dodlebee.firebasetest.cloudfirestore.CloudFireStore;
import com.dodlebee.firebasetest.dynamiclinks.DynamicLinks;
import com.dodlebee.firebasetest.naver.NaverLogin;
import com.dodlebee.firebasetest.naver.OAuthSampleActivity;
import com.dodlebee.firebasetest.storage.StorageJAVA;
import com.dodlebee.websocket.SocketActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class MainActivity extends AppCompatActivity {

    private Button buttonDynamicLinks;
    private Button buttonGoogleLogin;
    private Button buttonCloudFireStore;
    private Button buttonKaKaoMap;
    private Button buttonNaverLogin;
    private Button buttonStorage_API30;
    private Button buttonWebSocket;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        account = GoogleSignIn.getLastSignedInAccount(this);

        buttonDynamicLinks = (Button)findViewById(R.id.button_DynamicLinks);
        buttonDynamicLinks.setOnClickListener(on_GoogleLoginClick);

        buttonGoogleLogin = (Button)findViewById(R.id.button_GoogleLogin);
        buttonGoogleLogin.setOnClickListener(on_GoogleLoginClick);

        buttonCloudFireStore = (Button)findViewById(R.id.button_CloudFireStore);
        buttonCloudFireStore.setOnClickListener(on_GoogleLoginClick);

        buttonKaKaoMap = (Button)findViewById(R.id.button_KaKaoMap);
        buttonKaKaoMap.setOnClickListener(on_GoogleLoginClick);

        buttonNaverLogin = (Button)findViewById(R.id.button_NaverLogin);
        buttonNaverLogin.setOnClickListener(on_NaverClick);

        buttonStorage_API30 = (Button)findViewById(R.id.button_Storage_API30);
        buttonStorage_API30.setOnClickListener(on_ButtonClick);

        buttonWebSocket = (Button)findViewById(R.id.button_WebSocket);
        buttonWebSocket.setOnClickListener(on_WebSocket);

        Log.e("onCreate","Doodle");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","Doodle");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume","Doodle");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause","Doodle");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("onStop","Doodle");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","Doodle");
    }

    View.OnClickListener on_GoogleLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (account!=null){
                if (view.getId() == buttonDynamicLinks.getId()) {
                    createDynamicLinks();
                } else if (view.getId() == buttonGoogleLogin.getId()) {
                    createLoginActivity();
                } else if (view.getId() == buttonCloudFireStore.getId()) {
                    createFireStoreActivity();
                } else if (view.getId() == buttonKaKaoMap.getId()) {
                    createKaKaoMapActivity();
                }
            } else {
                createLoginActivity();
            }

        }
    };

    View.OnClickListener on_ButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == buttonStorage_API30.getId()) {
                createStorage_JAVA();
            }
        }
    };

    View.OnClickListener on_NaverClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == buttonNaverLogin.getId()) {
                createNaverLogin();
            }
        }
    };

    View.OnClickListener on_WebSocket = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == buttonWebSocket.getId()) {
                createWebSocket();
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

    private void createKaKaoMapActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","카카오지도");
        Intent intent = new Intent(MainActivity.this, KaKaoMap.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createNaverLogin() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","네이버로그인");
        Intent intent = new Intent(MainActivity.this, OAuthSampleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createStorage_JAVA() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","저장소 테스트");
        Intent intent = new Intent(MainActivity.this, StorageJAVA.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void createWebSocket() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","웹소켓 테스트");
        Intent intent = new Intent(MainActivity.this, SocketActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}