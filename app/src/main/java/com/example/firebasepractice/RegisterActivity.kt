package com.example.firebasepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var database: FirebaseDatabase? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.reference.child("profile")

        register()

    }

    private fun register() {

        btn_register.setOnClickListener {
            if (TextUtils.isEmpty(et_register_firstname.text.toString())) {
                et_register_firstname.setError("Please enter first name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(et_register_lastname.text.toString())) {
                et_register_lastname.setError("Please enter last name ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(et_register_mail.text.toString())) {
                et_register_mail.setError("Please enter mail ")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(et_register_password.text.toString())) {
                et_register_password.setError("Please enter password ")
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(et_register_mail.text.toString(),
                et_register_password.text.toString())
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val current = auth.currentUser
                        val currentUSerDb = databaseReference?.child((current?.uid!!))

                        currentUSerDb?.child("firstname")?.setValue(et_register_firstname.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(et_register_lastname.text.toString())

                        Toast.makeText(this@RegisterActivity,
                            "Registration Success.",
                            Toast.LENGTH_LONG).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity,
                            "Registration failed, please try again!",
                            Toast.LENGTH_LONG).show()
                    }
                }

        }
    }
}