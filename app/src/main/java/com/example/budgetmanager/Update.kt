package com.example.budgetmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Update : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        var Listy: MutableList<wow> = mutableListOf()
        firebaseAuth= FirebaseAuth.getInstance()
        val fstore= FirebaseFirestore.getInstance()
        var firebaseUser= firebaseAuth.currentUser
        val adapter= CustomAdapter(Listy)
        val title_main=intent.getStringExtra("title")
        val amount1=findViewById<EditText>(R.id.amount)
        val title1 =findViewById<EditText>(R.id.name)
        val add=findViewById<ImageButton>(R.id.add)
        val titlema=findViewById<TextView>(R.id.gmail)
        if (title_main=="Update Transaction"){
            val title=intent.getStringExtra("item")
            val amount=intent.getStringExtra("amount")
            val ID=intent.getStringExtra("id")


            amount1.setText(amount)
            title1.setText(title)
            add.setOnClickListener{
                if (amount1.text.toString().toIntOrNull() is Int) {
                    fstore.collection("Expenses").document(firebaseAuth.uid!!).collection("Notes")
                    .document(ID!!)
                    .update("Amount",amount1.text.toString(),"title",title1.text.toString())
                    .addOnSuccessListener() {
                        val intent=Intent(this,Budget::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener() {
                        Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this,"Amount not in digits",Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            titlema.text=title_main
            add.setOnClickListener {

                if (amount1.text.toString().toIntOrNull() is Int) {
                    val title = title1.text.toString()
                    val amoun = amount1.text.toString()
                    val ID = UUID.randomUUID().toString()
                    val calendar = Calendar.getInstance()
                    val yy=calendar.get(Calendar.YEAR)
                    val mm=calendar.get(Calendar.MONTH)
                    val dd=calendar.get(Calendar.DAY_OF_MONTH)
                    val h =calendar.get(Calendar.HOUR_OF_DAY)
                    val m=calendar.get(Calendar.MINUTE)
                    val s=calendar.get(Calendar.SECOND)
                    val date ="$yy/$mm/$dd $h:$m:$s"
                    val budg= wow(title,amoun,ID,date)
                    Listy.add(budg)
                    adapter.notifyItemInserted(Listy.size - 1)
                    var transaction = HashMap<String, Any>()
                    transaction["Id"] = ID
                    transaction["Amount"] = amoun
                    transaction["Item"] = title
                    transaction["Date"]=date
                    fstore.collection("Expenses").document(firebaseAuth.uid!!).collection("Notes")
                        .document(ID)
                        .set(transaction)
                        .addOnSuccessListener() {
                            Toast.makeText(this, "HAPPY", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener() {
                            Toast.makeText(this, "Sad", Toast.LENGTH_SHORT).show()
                        }
                    Intent(this,Budget::class.java).also {
                        this.startActivity(it)}
                }
                else{
                    Toast.makeText(this,"Amount not in digits",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}