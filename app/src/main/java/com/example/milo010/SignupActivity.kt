package com.example.milo010

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
    private lateinit var textTogoLogin: TextView
    private lateinit var edtUsername: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var edtConfirmPass: EditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        textTogoLogin = findViewById(R.id.go_login_page)
        btnSignUp = findViewById(R.id.btn_signup)
        edtUsername = findViewById(R.id.edt_username)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        edtConfirmPass = findViewById(R.id.edt_confirm_pass)
        auth = Firebase.auth


        textTogoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            val confirmpass = edtConfirmPass.text.toString()
            val username = edtUsername.text.toString()
            if (password == confirmpass) {
                SignUp(username, email, password)
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }


        }
    }



    private fun SignUp(username: String, email: String, password: String) {

        if(TextUtils.isEmpty(edtUsername.text.toString())){
            edtUsername.setError("please enter Username")
            return
        }else if(TextUtils.isEmpty(edtEmail.text.toString())){
            edtEmail.setError("please enter Email")
            return
        }else if(TextUtils.isEmpty(edtPassword.text.toString())){
            edtPassword.setError("please enter Password")
            return
        }else if(TextUtils.isEmpty(edtConfirmPass.text.toString())){
            edtConfirmPass.setError("please enter ConfirmPassword")
            return
        }

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    startActivity(Intent(this, MainActivity::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()
                }
            }


    }


}