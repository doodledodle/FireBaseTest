package com.dodlebee.firebasetest.naver;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dodlebee.firebasetest.R;
import com.google.firebase.firestore.util.Util;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NaverLogin extends AppCompatActivity {
    private final String OAUTH_CLIENT_ID = "TEXlZhV0XDBKk6ObS4to";
    private final String OAUTH_CLIENT_SECRET = "x7OVLnWyU6";
    private final String OAUTH_CLIENT_NAME = "API테스트";

    static OAuthLogin mOAuthLoginModule;

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naverlogin);
    }
}
