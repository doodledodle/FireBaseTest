package com.dodlebee.firebasetest.cloudfirestore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dodlebee.firebasetest.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_cloudfirestore.*

class CloudFireStore : AppCompatActivity() {
    private val TAG = "CloudFireStore"

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloudfirestore)

        getDB()
    }

    private fun getDB() {
        db.collection("user")
                .whereEqualTo("name", "dodle")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        Log.d(TAG, document.data["name"] as String)

                        textView_CloudFireStore_Value.text = "${document.data}"
                    }
                }
                .addOnFailureListener { e->
                    Log.w(TAG, "Error", e) }
    }

}