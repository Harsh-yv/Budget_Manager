package com.example.budgetmanager
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
private lateinit var firebaseAuth: FirebaseAuth
class Register:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        val login=findViewById<Button>(R.id.register)
        val textview =findViewById<TextView>(R.id.textViewbro)
        textview.setOnClickListener {
            val intent=Intent(this,Login::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            firebaseAuth= FirebaseAuth.getInstance()

            val Gmail=findViewById<EditText>(R.id.gmail)
            val Password=findViewById<EditText>(R.id.password)
            val ConfirmPassword=findViewById<EditText>(R.id.conpassword)
                val email=Gmail.text.toString()
                val pass=Password.text.toString()
                val confirmpass=ConfirmPassword.text.toString()
                if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty())
                {
                    if(pass==confirmpass){
                        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){
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
                                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(this,"Password is not matchiong", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"Empty Fields are not allowed", Toast.LENGTH_SHORT).show()
                }

        }
    }
}