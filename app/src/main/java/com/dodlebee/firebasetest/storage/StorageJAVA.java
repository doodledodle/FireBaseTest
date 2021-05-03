package com.dodlebee.firebasetest.storage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dodlebee.firebasetest.KaKao.KaKaoMap;
import com.dodlebee.firebasetest.MainActivity;
import com.dodlebee.firebasetest.R;

import java.io.File;
import java.io.IOException;

public class StorageJAVA extends AppCompatActivity {

    private Button buttonCreateDirectory;
    private Button buttonDeleteDirectory;
    private Button buttonCamera;
    private Button buttonAlbum;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_storage_java);

        buttonCreateDirectory = (Button) findViewById(R.id.button_CreateDirectory);
        buttonDeleteDirectory = (Button) findViewById(R.id.button_DeleteDirectory);
        buttonCamera = (Button) findViewById(R.id.button_Camera);
        buttonAlbum = (Button) findViewById(R.id.button_Album);

        buttonCreateDirectory.setOnClickListener(on_MenuClick);
        buttonDeleteDirectory.setOnClickListener(on_MenuClick);
        buttonCamera.setOnClickListener(on_MenuClick);
        buttonAlbum.setOnClickListener(on_MenuClick);
    }

    private View.OnClickListener on_MenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == buttonCreateDirectory.getId()) {

            } else if (v.getId() == buttonDeleteDirectory.getId()) {
                
            } else if (v.getId() == buttonCamera.getId()) {
                createCameraActivity();
            } else if (v.getId() == buttonAlbum.getId()) {

            }
        }
    };

    public static void DirectoryCreate(String directoryName) throws IOException {
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED))  {
            throw new IOException("SD Card is not mounted.  It is " + state + ".");
        }

        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), directoryName) ;
        if (!dir.isDirectory()) {
            if (!dir.mkdirs()) {
                throw new IOException("Path to file could not be created.");
            }
        }
    }

    private void createCameraActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("Title","카메라");
        Intent intent = new Intent(StorageJAVA.this, CameraKotlin.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
