package com.example.budgetmanager

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
private lateinit var firebaseAuth: FirebaseAuth
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val textview =findViewById<TextView>(R.id.textView)
        val login=findViewById<Button>(R.id.login)
        val Gmail=findViewById<EditText>(R.id.loggmail)
        val Password=findViewById<EditText>(R.id.logpassword)
        textview.setOnClickListener {
            val intent=Intent(this,Register::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
                firebaseAuth= FirebaseAuth.getInstance()
                val email=Gmail.text.toString()
                val pass=Password.text.toString()
                if (email.isNotEmpty() && pass.isNotEmpty() )
                {
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent=Intent(this,Budget::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Empty Fields are not allowed",Toast.LENGTH_SHORT).show()
                }
        }
    }
}