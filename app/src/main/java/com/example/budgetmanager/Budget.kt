package com.example.budgetmanager

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.core.Tag
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import android.content.ContentValues.TAG
import android.content.Intent
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Spinner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private lateinit var firebaseAuth: FirebaseAuth
class Budget : AppCompatActivity() {

    var Listy: MutableList<wow> = mutableListOf()
    val adapter= CustomAdapter(Listy)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        val fstore=FirebaseFirestore.getInstance()
        firebaseAuth= FirebaseAuth.getInstance()
        var firebaseUser= firebaseAuth.currentUser
        val voe=findViewById<RecyclerView>(R.id.rvtodo)
        val add1=findViewById<Button>(R.id.addd)
        val logout=findViewById<ImageButton>(R.id.logout)
        val income1=findViewById<TextView>(R.id.income1)
        val expense1=findViewById<TextView>(R.id.expense1)
        val balance1=findViewById<TextView>(R.id.balance1)
        val spoptiins=findViewById<Spinner>(R.id.sp)
        voe.adapter=adapter
        voe.layoutManager= LinearLayoutManager(this)
        fstore.collection("Expenses")
            .document(firebaseAuth.uid!!)
            .collection("Notes").get().addOnSuccessListener(){documents->
                for(i in documents){
                    income1.text="10000"
                    val title=i["Item"].toString()
                    val amoun=i["Amount"].toString()
                    val ID=i["Id"].toString()
                    val date =i["Date"].toString()
                    val budg= wow(title,amoun,ID,date)
                    Listy.add(budg)
                    var sum = 0
                    for (w in Listy) {
                        val (am, expense) = w
                        sum += expense.toInt()
                    }
                    balance1.text = "${10000-sum}"
                    expense1.text=sum.toString()
                    adapter.notifyItemInserted(Listy.size - 1)
                }
            }
        logout.setOnClickListener {
            firebaseAuth.signOut()
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
        }
        add1.setOnClickListener{
           /* add.visibility=View.VISIBLE
            amount.visibility=View.VISIBLE
            name.visibility=View.VISIBLE
            rectangle.visibility=View.VISIBLE
            add1.visibility=View.INVISIBLE
            */
            Intent(this,Update::class.java).also {
                it.putExtra("title","Add Transaction ")
                this.startActivity(it)}
        }


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                val deletedCourse: wow =
                    Listy.get(viewHolder.adapterPosition)

                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition

                // this method is called when item is swiped.
                // below line is to remove item from our array list.
                Listy.removeAt(viewHolder.adapterPosition)

                // below line is to notify our item is removed from adapter.
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                // below line is to display our snackbar with action.
                var del=0
                Snackbar.make(voe, "Deleted " , Snackbar.LENGTH_LONG).setAction(
                        "Undo",
                        View.OnClickListener {
                            // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            Listy.add(position, deletedCourse)

                            // below line is to notify item is
                            // added to our adapter class.
                            adapter.notifyItemInserted(position)
                            del=-1
                        }).show()
                if (del==0){
                    fstore.collection("Expenses")
                        .document(firebaseAuth.uid!!).collection("Notes")
                        .document(deletedCourse.id!!).delete()}
            }
            // at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(voe)
        spoptiins.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapte: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                if (pos==0){
                    Listy.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER){it.id})
                    adapter.notifyDataSetChanged()
                }
                if (pos==1){
                    Listy.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.title })
                    adapter.notifyDataSetChanged()
                }
                if (pos==2){
                    Listy.sortBy{ it.amount }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

    }
}
