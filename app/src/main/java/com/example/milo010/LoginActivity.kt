package com.example.milo010

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var texttogoSinUp : TextView
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        texttogoSinUp = findViewById(R.id.go_sign_up)
        btnLogin = findViewById(R.id.btn_login)
        edtEmail =findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        auth = Firebase.auth
        sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        clearInputFields()


        if (auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        texttogoSinUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }



        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            login(email,password)
        }
    }


    private fun login(email:String, password : String) {

        if(TextUtils.isEmpty(edtEmail.text.toString())){
            edtEmail.setError("please enter Email")
            return
        }else if(TextUtils.isEmpty(edtPassword.text.toString())) {
            edtPassword.setError("please enter Password")
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //sharedPreferences.edit().putBoolean("is_logged_in", true).apply()
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()


                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()

                }
            }
    }


    private fun clearInputFields() {
        edtEmail.text = null
        edtPassword.text = null
        //Log.d("LoginActivity11", "Input fields cleared")
    }


}