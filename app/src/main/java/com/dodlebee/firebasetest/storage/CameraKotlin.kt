package com.dodlebee.firebasetest.storage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dodlebee.firebasetest.R
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.log.fileLogger
import io.fotoapparat.log.logcat
import io.fotoapparat.log.loggers
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.result.transformer.scaled
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.activity_camerakotlin.*
import java.io.File

class CameraKotlin : AppCompatActivity() {

    private lateinit var fotoapparat: Fotoapparat
    private val LOGGING_TAG = "Fotoapparat Example"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        setContentView(R.layout.activity_camerakotlin)
        super.onCreate(savedInstanceState, persistentState)

        val cameraConfiguration = CameraConfiguration(
                pictureResolution = highestResolution(), // (optional) we want to have the highest possible photo resolution
                previewResolution = highestResolution(), // (optional) we want to have the highest possible preview resolution
                previewFpsRange = highestFps(),          // (optional) we want to have the best frame rate
                focusMode = firstAvailable(              // (optional) use the first focus mode which is supported by device
                        continuousFocusPicture(),
                        autoFocus(),                       // if continuous focus is not available on device, auto focus will be used
                        fixed()                            // if even auto focus is not available - fixed focus mode will be used
                ),
                flashMode = firstAvailable(              // (optional) similar to how it is done for focus mode, this time for flash
                        autoRedEye(),
                        autoFlash(),
                        torch(),
                        off()
                ),
                antiBandingMode = firstAvailable(       // (optional) similar to how it is done for focus mode & flash, now for anti banding
                        auto(),
                        hz50(),
                        hz60(),
                        none()
                ),
                jpegQuality = manualJpegQuality(90),     // (optional) select a jpeg quality of 90 (out of 0-100) values
                sensorSensitivity = lowestSensorSensitivity(), // (optional) we want to have the lowest sensor sensitivity (ISO)
                frameProcessor = { frame -> }            // (optional) receives each frame from preview stream
        )


        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,                   // view which will draw the camera preview
                scaleType = ScaleType.CenterCrop,    // (optional) we want the preview to fill the view
                lensPosition = back(),               // (optional) we want back camera
                cameraConfiguration = cameraConfiguration, // (optional) define an advanced configuration
                logger = loggers(                    // (optional) we want to log camera events in 2 places at once
                        logcat(),                   // ... in logcat
                        fileLogger(this)            // ... and to file
                ),
                cameraErrorCallback = { Log.e(LOGGING_TAG, "Camera error: ", it) }   // (optional) log fatal errors
        )

        imageButton_CameraFragment_TakePicture.setOnClickListener { takePicture() }

    }

    private fun takePicture() {
        val photoResult = fotoapparat
                .autoFocus()
                .takePicture()

        photoResult
                .saveToFile(File(
                        getExternalFilesDir("photos"),
                        "photo.jpg"
                ))

        photoResult
                .toBitmap(scaled(scaleFactor = 0.25f))
                .whenAvailable { photo ->
                    photo
                            ?.let {
                                Log.i(LOGGING_TAG, "New photo captured. Bitmap length: ${it.bitmap.byteCount}")

//                                val imageView = findViewById<ImageView>(R.id.result)
//
//                                imageView.setImageBitmap(it.bitmap)
//                                imageView.rotation = (-it.rotationDegrees).toFloat()
                            }
                            ?: Log.e(LOGGING_TAG, "Couldn't capture photo.")
                }
    }


    override fun onStart() {
        super.onStart()
//        fotoapparat.start()
    }

    override fun onStop() {
        super.onStop()
        fotoapparat.stop()
    }

    fun startFotoapparat() {
        fotoapparat.start()
        Log.e("*****", "*****Start")
    }

    private fun checkPermissionPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        } else {
            startFotoapparat()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                var permissionResult = true
                var i = 0
                while (i < permissions.size) {
                    val permission = permissions[i]
                    val grantResult = grantResults[i]
                    if (permission == Manifest.permission.CAMERA) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            permissionResult = false
                        }
                    }
                    i++
                }
                if (permissionResult) {
                    startFotoapparat()
                } else {
                    finish()
                }
            }
        }
    }
}