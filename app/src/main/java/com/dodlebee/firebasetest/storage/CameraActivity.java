package com.dodlebee.firebasetest.storage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dodlebee.firebasetest.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.error.CameraErrorListener;
import io.fotoapparat.exception.camera.CameraException;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;
import kotlin.Unit;

import static com.google.android.material.animation.AnimationUtils.DECELERATE_INTERPOLATOR;
import static io.fotoapparat.result.transformer.ResolutionTransformersKt.scaled;
import static io.fotoapparat.selector.LensPositionSelectorsKt.back;

public class CameraActivity extends AppCompatActivity {
    private CameraView cameraView;
    private LinearLayout cameraViewBack;
    private Fotoapparat fotoapparat;
    private View vShutter;
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private ImageButton imageButtonCameraFragment_TakePicture;

    private ContentValues contentValues;
    private Uri photouri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraView = (CameraView) findViewById(R.id.cameraView);
        fotoapparat = createFotoapparat();
        double photoRate = 1.3333;

        cameraView = (CameraView) findViewById(R.id.cameraView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (getDisplayWidth(CameraActivity.this) * photoRate));
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        cameraView.setLayoutParams(layoutParams);

        cameraViewBack = (LinearLayout) findViewById(R.id.cameraViewBack);
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, (int) (getDisplayWidth(CameraActivity.this) / photoRate));
        layoutParams2.gravity = Gravity.CENTER_VERTICAL;
        cameraViewBack.setLayoutParams(layoutParams2);

        vShutter = (View) findViewById(R.id.vShutter);

        cameraView.setVisibility(View.VISIBLE);
        fotoapparat = createFotoapparat();

        imageButtonCameraFragment_TakePicture = (ImageButton) findViewById(R.id.imageButton_CameraFragment_TakePicture);
        imageButtonCameraFragment_TakePicture.setOnClickListener(on_MenuClick);

        contentValues = new ContentValues();


        if(Build.VERSION.SDK_INT >= 29){
            setFileSaveSetting();
        }

        checkPermissionPhoto();




    }

    public static int getDisplayWidth(Context context) {
        int realWidth;

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        realWidth = metrics.widthPixels;

        return realWidth;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private View.OnClickListener on_MenuClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == imageButtonCameraFragment_TakePicture.getId()) {
                if (v.getId() == imageButtonCameraFragment_TakePicture.getId()) {
                    imageButtonCameraFragment_TakePicture.setEnabled(false);
                    takePicture();
                }
            }

        }
    };

    private Fotoapparat createFotoapparat() {
        return Fotoapparat
                .with(CameraActivity.this)
                .into(cameraView)
                .previewScaleType(ScaleType.CenterCrop)
                .lensPosition(back())
                .cameraErrorCallback(new CameraErrorListener() {
                    @Override
                    public void onError(@NotNull CameraException e) {
                        Toast.makeText(CameraActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    private void takePicture() {
        animateShutter();
        PhotoResult photoResult = fotoapparat.takePicture();

        photoResult.saveToFile(new File(CameraActivity.this.getDir("fitest",Context.MODE_PRIVATE) ,"photo.jpg")).whenDone(new WhenDoneListener<Unit>() {
            @Override
            public void whenDone(@org.jetbrains.annotations.Nullable Unit unit) {
                Toast.makeText(CameraActivity.this, "저장오류",Toast.LENGTH_SHORT).show();
            }
        });
        photoResult.toBitmap(scaled(0.5f))
                .whenDone(new WhenDoneListener<BitmapPhoto>() {
                    @Override
                    public void whenDone(@Nullable BitmapPhoto bitmapPhoto) {
                        if (bitmapPhoto == null) {
                            imageButtonCameraFragment_TakePicture.setEnabled(true);
                            Toast.makeText(CameraActivity.this, "사진오류",Toast.LENGTH_SHORT).show();

                            return;
                        }
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    }
                });
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            imageButtonCameraFragment_TakePicture.setEnabled(true);


        }
    };

    private void animateShutter() {
        vShutter.setVisibility(View.VISIBLE);
        vShutter.setAlpha(0.f);

        ObjectAnimator alphaInAnim = ObjectAnimator.ofFloat(vShutter, "alpha", 0f, 0.8f);
        alphaInAnim.setDuration(100);
        alphaInAnim.setStartDelay(100);
        alphaInAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        ObjectAnimator alphaOutAnim = ObjectAnimator.ofFloat(vShutter, "alpha", 0.8f, 0f);
        alphaOutAnim.setDuration(200);
        alphaOutAnim.setInterpolator(DECELERATE_INTERPOLATOR);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(alphaInAnim, alphaOutAnim);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                vShutter.setVisibility(View.GONE);
            }
        });
        animatorSet.start();
    }

    public void startFotoapparat() {
        fotoapparat.start();
        Log.e("*****", "*****Start");
    }

    public void stopFotoapparat() {
        fotoapparat.stop();
        Log.e("*****", "*****Stop");
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setFileSaveSetting() {
        setContentValues();


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void setContentValues(){
        contentValues.put(MediaStore.Audio.Media.RELATIVE_PATH, "DCIM/FireBase");
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "firebase.jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 1);

        ContentResolver contentResolver = getContentResolver();
        Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri item = contentResolver.insert(collection,contentValues);

    }

    private void checkPermissionPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        } else {
            startFotoapparat();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                Boolean permissionResult = true;
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CAMERA)) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            permissionResult = false;
                        }
                    }
                }
                if (permissionResult) {
                    startFotoapparat();
                } else {
                    finish();
                }
                break;
        }
    }
}