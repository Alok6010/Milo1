package com.example.milo010

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class UploadUserPhotosActivity : AppCompatActivity() {

    private lateinit var previewPhoto: ImageView
    private lateinit var btnselectPhoto: Button
    private lateinit var btnuploadPhoto: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_user_photos)

        previewPhoto = findViewById(R.id.edt_Photo_preview)
        btnselectPhoto = findViewById(R.id.btn_Select_Photo)
        btnuploadPhoto = findViewById(R.id.btn_Upload_Photo)
        progressBar = findViewById(R.id.progress_Bar)

        btnselectPhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,101)
        }


    }

    //private var uploadedPhotosCount = 0

    private var uploadedPhotosCount = 0
    private var totalPhotosCount = 0



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK){
            val uri = data?.data
            previewPhoto.setImageURI(uri)

            // Increment totalPhotosCount
            totalPhotosCount++

            btnuploadPhoto.setOnClickListener {
                //image code to upload image
                //to get image link
                progressBar.visibility = android.view.View.VISIBLE
//                val hobby1 = edthobby1.text.toString()
//                val hobby2 = edthobby2.text.toString()
//                val hobby3 = edthobby3.text.toString()

                val fileName = UUID.randomUUID().toString()+".jpg"
                val storageRef = FirebaseStorage.getInstance().reference.child("personPhoto/$fileName")
                storageRef.putFile(uri!!).addOnSuccessListener {
                    val result = it.metadata?.reference?.downloadUrl
                    result?.addOnSuccessListener {
                        Log.i("abcd",it.toString())

                        uploadPhoto(
//                            hobby1,
//                            hobby2,
//                            hobby3,
                            it.toString()

                        )

                        // Increment uploadedPhotosCount
                        uploadedPhotosCount++

                        //uploadedPhotosCount++
//                        if (uploadedPhotosCount== 3) { // Assuming you're uploading 3 photos
//                            progressBar.visibility = android.view.View.GONE
//                            finishUpload()
//                        }
                        // Remove ProgressBar on UI thread after the upload is complete
                       if(uploadedPhotosCount==totalPhotosCount) {
                           runOnUiThread {
                               progressBar.visibility = android.view.View.GONE
                           }
                           finish()

                           startActivity(Intent(this, ProfileActivity::class.java))


                       }
                    }
                }
            }

        }


    }

//    private fun finishUpload() {
//        // Clear activity back stack and start profile activity
//        val intent = Intent(this, ProfileActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//    }




    private fun uploadPhoto(link: String) {


        val userUid = FirebaseAuth.getInstance().currentUser?.uid
        val userPhotosRef = Firebase.database.getReference("users/$userUid/listOfUserPhotos")
        userPhotosRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentPhotos = snapshot.getValue<List<String>>() ?: emptyList()
                val updatedPhotos = currentPhotos.toMutableList()
                updatedPhotos.add(link)

                userPhotosRef.setValue(updatedPhotos)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        // Rest of your existing code...
    }
}