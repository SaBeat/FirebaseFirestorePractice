package com.example.firebasepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_another_verification.*
import kotlinx.android.synthetic.main.activity_varification.*
import java.util.concurrent.TimeUnit

class VarificationActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varification)

        auth = FirebaseAuth.getInstance()

        btn_write_phone_number.setOnClickListener {
            val phoneNumber = et_write_phone_number.text.toString()
            verifyPhoneNumber(phoneNumber)
        }
    }

    fun verifyPhoneNumber(phone: String) {
        val number = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mAuthProvider)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(number)
    }

    private val mAuthProvider: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                val intent =
                    Intent(this@VarificationActivity, AnotherVerificationActivity::class.java)
                intent.putExtra("Code", p0)
                startActivity(intent)
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@VarificationActivity, "Error $p0", Toast.LENGTH_SHORT).show()
            }

        }
}