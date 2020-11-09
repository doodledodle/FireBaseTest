package com.dodlebee.firebasetest.dynamiclinks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.dodlebee.firebasetest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class DynamicLinks extends AppCompatActivity {
    private Bundle p_bundle = new Bundle();
    private String deepLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_links);
        p_bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(p_bundle.getString("Title"));

        deepLink = "http://dodlebee.com/";
        createShortLink();

    }


    private void setDeepLink() {
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(deepLink))
                .setDomainUriPrefix("https://dodlebee.page.link/")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildDynamicLink();

        Uri dynamicLinkUri = dynamicLink.getUri();

        Log.i("@@Uri@@",dynamicLinkUri.toString());
    }

    public void createShortLink() {
        // [START create_short_link]
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(deepLink))
                .setDomainUriPrefix("https://dodlebee.page.link")
                .setAndroidParameters(
                        new DynamicLink.AndroidParameters.Builder(deepLink)
                        .setFallbackUrl(Uri.parse("https://m.blog.naver.com/jaseazer/221317744472"))
                        .build()
                )
                .setIosParameters(
                        new DynamicLink.IosParameters.Builder(deepLink)
                        .setFallbackUrl(Uri.parse("https://hisastro.com/1478"))
                        .build()
                )

                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            Log.i("@@Uri@@",shortLink.toString());
                        } else {
                            // Error
                            // ...
                        }
                    }
                });


    }



}