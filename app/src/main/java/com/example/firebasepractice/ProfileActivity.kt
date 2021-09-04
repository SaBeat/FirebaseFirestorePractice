package com.example.firebasepractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    var auth:FirebaseAuth?=null
    var database:FirebaseDatabase?=null
    var databaseRefernce:DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseRefernce=database?.reference!!.child("profile")

        showText()
    }

    private fun showText(){
        val user=auth?.currentUser
        val userReference=databaseRefernce?.child(user?.uid!!)

        text_profile_mail.text="Mail: "+user?.email
        userReference?.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                text_profile_firstname.text="Firstname: "+snapshot.child("firstname").value.toString()
                text_profile_lastname.text="Lastname: "+snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        btn_logout.setOnClickListener {
            auth?.signOut()
            startActivity(Intent(this@ProfileActivity,LoginActivity::class.java))
            finish()
        }
    }

}