package com.example.firebasepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_another_verification.*
import kotlinx.android.synthetic.main.activity_varification.*

class AnotherVerificationActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another_verification)

        auth= FirebaseAuth.getInstance()

        val code=intent.getStringExtra("Code")

        btn_verify_code.setOnClickListener {

            if (code != null) {
                verifyCode(code,et_verify_code.text.toString())
            }

        }
    }

    fun verifyCode(authCode:String,internalCode:String){
        val credential=PhoneAuthProvider.getCredential(authCode,internalCode)
        signWithCredentials(credential)
    }
    fun signWithCredentials(credential:PhoneAuthCredential){
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {
                if(it.isSuccessful){
                    startActivity(Intent(this@AnotherVerificationActivity,ProfileActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@AnotherVerificationActivity, "Error is handling", Toast.LENGTH_SHORT).show()
                }
            }
    }
}