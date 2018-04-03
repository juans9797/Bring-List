package com.example.sebastian.bringlist
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        val btn_signIn = findViewById<Button>(R.id.button2) as Button
        val btn_signUp = findViewById<Button>(R.id.button3) as Button
        val vrMail=findViewById<EditText>(R.id.editText)
        val vrPassword=findViewById<EditText>(R.id.editText2)
        val signUpA = Intent(this,SignUp::class.java)
        btn_signIn.setOnClickListener {
            if(vrMail.text.toString().trim().isNotEmpty() and vrPassword.text.toString().trim().isNotEmpty()){
                signIn(vrMail.text.toString(), vrPassword.text.toString())
            }
            else{
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle("Error")
                alertDialog.setMessage("The mail or password are empty")
                alertDialog.setNegativeButton("Ok"){_,_-> }
                alertDialog.show()
            }
        }
        btn_signUp.setOnClickListener{
            startActivity(signUpA)
        }

    }

    private fun signIn(email: String,password:String){
        val ppal = Intent(this,myLists::class.java)
        mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                        startActivity(ppal)
                    } else {
                        val alertDialog = AlertDialog.Builder(this)
                        alertDialog.setTitle("Error")
                        alertDialog.setMessage("Mail or password incorrect, try again")
                        alertDialog.setNegativeButton("Ok"){_,_-> }
                        alertDialog.show()
                    }
                }
    }
}
