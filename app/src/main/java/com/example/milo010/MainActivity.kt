package com.example.milo010

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.milo010.Adapter.PhotoAdapter
import com.example.milo010.Data.Photo
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var fabUpload: MaterialButton
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var auth: FirebaseAuth
    val listofPhoto: MutableList<Photo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        fabUpload = findViewById(R.id.fab_upload)

        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        FirebaseDatabase.getInstance().getReference("photos")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    listofPhoto.clear()
                    for (dataSnapshot in snapshot.children){
                        val photo= dataSnapshot.getValue(Photo::class.java)

                        listofPhoto.add(photo!!)
                    }

                    // Inside onDataChange after updating listofPhoto
//                    photoAdapter.notifyDataSetChanged()

                    //code to show one ss image at a time when we swipe ,completely goes to second activity
                    val snapHelper : SnapHelper = LinearSnapHelper()
                    snapHelper.attachToRecyclerView(rv)
                    photoAdapter = PhotoAdapter(listofPhoto,this@MainActivity)
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv.adapter = photoAdapter

                }


                override fun onCancelled(error: DatabaseError) {

                }
            })

//        photoAdapter = PhotoAdapter(listofPhoto,this@MainActivity)
//        rv.layoutManager = LinearLayoutManager(this@MainActivity)
//        rv.adapter = photoAdapter


        fabUpload.setOnClickListener(){
            //Toast.makeText(this, "Liked", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,UploadPhotoActivity::class.java))

        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_search -> {
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.menu_chat -> {
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(this,ChatListActivity::class.java))
                return true
            }

            R.id.menu_profile -> {
                //startActivity(Intent(this, ProfileActivity::class.java))
                return true
            }

            R.id.menu_setting -> {
                Toast.makeText(this, "clik", Toast.LENGTH_SHORT).show()
                //startActivity(Intent(this, SettingActivity::class.java))
                return true
            }

            R.id.menu_Share -> {
                Toast.makeText(this, "clik", Toast.LENGTH_SHORT).show()
                shareApp()
                return true
            }

            R.id.menu_rateus -> {
                Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()
                return true
            }

            R.id.menu_logout -> {
                // write the logic for logout
                auth.signOut()
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun shareApp() {
        val appMsg: String = "Hey !, Check out this app for New Friends:- "+
                "https://play.google.com/store/apps/details?id=com.example.milo010"

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,appMsg)
        intent.type ="text/plain"
        startActivity(intent)
    }
}