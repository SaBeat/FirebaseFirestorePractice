package com.example.firebasepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add.setOnClickListener {
            val firstName=et_firstName.text.toString()
            val lastName=et_lastName.text.toString()

            saveFirestore(firstName,lastName)
        }
        readFirestore()
    }

    private fun saveFirestore(firstName: String, lastName: String) {
       val db= FirebaseFirestore.getInstance()
        val user:MutableMap<String,Any> =HashMap()
        user["firstName"]=firstName
        user["lastName"]=lastName
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Succesfully added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(this@MainActivity, "Faild!", Toast.LENGTH_SHORT).show()
            }
    }
    fun readFirestore(){
        val db=FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener{
                val result=StringBuffer()

                if(it.isSuccessful){
                    for(document in it.result!!){
                        result.append(document.data.getValue("firstName")).append(" ")
                            .append(document.data.getValue("lastName")).append("\n\n")
                    }
                }
                text_firstname.setText(result)
            }
    }
}