package com.example.milo010

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.example.milo010.Data.Photo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadPhotoActivity : AppCompatActivity() {

    private lateinit var edthobby1: EditText
    private lateinit var edthobby2: EditText
    private lateinit var edthobby3: EditText
    private lateinit var previewPhoto: ImageView
    private lateinit var btnselectPhoto: Button
    private lateinit var btnuploadPhoto: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)


        edthobby1 = findViewById(R.id.edt_hobby_1)
        previewPhoto = findViewById(R.id.edt_Photo_preview)
        btnselectPhoto = findViewById(R.id.btn_Select_Photo)
        btnuploadPhoto = findViewById(R.id.btn_Upload_Photo)
        progressBar = findViewById(R.id.progress_Bar)

        btnselectPhoto.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 101)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val uri = data?.data
            previewPhoto.setImageURI(uri)

            btnuploadPhoto.setOnClickListener {
                //image code to upload image
                //to get image link
                progressBar.visibility = android.view.View.VISIBLE
                val hobby1 = edthobby1.text.toString()

                val fileName = UUID.randomUUID().toString()+".jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("personPhoto/$fileName")
                storageRef.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                        Log.i("abcd",it.toString())

                        uploadPhoto(
                            hobby1,
                            it.toString()
                        )
                        // Remove ProgressBar on UI thread after the upload is complete
                        runOnUiThread {
                            progressBar.visibility = android.view.View.GONE
                        }

                    }
                }
            }
        }
    }

    private fun uploadPhoto(hobby01:String, link: String){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        Log.d("UploadPhotoActivity", "User ID: $userId") // Log the userId

        userId?.let {
            Firebase.database.getReference("photos").push().setValue(
                Photo(

                    hobby1 = hobby01,
                    personPhoto = link
                )
            ).addOnSuccessListener {
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Photo is successfully uploaded", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener { exception ->
                progressBar.visibility = View.GONE
                Log.e("UploadPhotoActivity", "Error uploading Photo: $exception")
                Toast.makeText(this, "Failed to upload Photo", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
