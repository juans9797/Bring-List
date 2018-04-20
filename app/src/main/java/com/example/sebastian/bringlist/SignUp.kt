package com.example.sebastian.bringlist

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        val btn_signUp = findViewById<Button>(R.id.button4) as Button
        //val btn_clear = findViewById<Button>(R.id.button5) as Button
        val vrName=findViewById<EditText>(R.id.editText4)
        val vrMail=findViewById<EditText>(R.id.editText5)
        val vrPassword=findViewById<EditText>(R.id.editText6)
        val vrPhone=findViewById<EditText>(R.id.editText8)
        btn_signUp.setOnClickListener{
            if(vrMail.text.toString().trim().isNotEmpty() and vrPassword.text.toString().trim().isNotEmpty() and vrName.text.toString().trim().isNotEmpty() and vrPhone.text.toString().trim().isNotEmpty()) {
                signUp(vrMail.text.toString(), vrPassword.text.toString(), vrName.text.toString(), vrPhone.text.toString())
            }
            else{

                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("At least one of the fields it`s empty, please check")
                alertDialog.setNegativeButton("Ok"){_,_-> }
                alertDialog.show()

            }
        }
    }


    private fun signUp(email: String,password:String,name:String,phone:String){
        mAuth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth!!.currentUser!!.uid
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("name").setValue(name)
                        currentUserDb.child("mail").setValue(email)
                        currentUserDb.child("phone").setValue(phone)
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Created User")
                        alertDialog.setMessage("The user has been created successfully")
                        alertDialog.setNegativeButton("Ok"){_,_-> }
                        alertDialog.show()
                    } else {
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Error")
                        alertDialog.setMessage("The user could`t be created, try again")
                        alertDialog.setNegativeButton("Ok"){_,_-> }
                        alertDialog.show()
                    }
                }

    }
}
