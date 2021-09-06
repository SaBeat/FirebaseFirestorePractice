package com.example.firebasepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if (auth?.currentUser != null) {
            startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
            finish()
            Toast.makeText(this@LoginActivity, "You have alreade account", Toast.LENGTH_SHORT)
                .show()
        }

        login()
        text_forgot_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity,VarificationActivity::class.java))
            finish()
        }
    }

    private fun login() {
        btn_login.setOnClickListener {
            if (TextUtils.isEmpty(et_login_mail.text.toString())) {
                et_login_mail.setError("Enter mail please")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(et_login_password.text.toString())) {
                et_login_password.setError("Enter password please")
                return@setOnClickListener
            }

            auth?.signInWithEmailAndPassword(et_login_mail.text.toString(),
                et_login_password.text.toString())
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this@LoginActivity, ProfileActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity,
                            "Login failed, please try again! ",
                            Toast.LENGTH_LONG).show()
                    }
                }


        }
        text_go_to_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}