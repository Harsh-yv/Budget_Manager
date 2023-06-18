package com.example.budgetmanager
import android.view.LayoutInflater
                import android.view.View
                import android.view.ViewGroup
                import android.widget.Button
                import android.widget.TextView
                import androidx.recyclerview.widget.RecyclerView
                import android.content.Intent
import androidx.appcompat.view.menu.MenuView.ItemView
import android.widget.Toast
class CustomAdapter(var bud: List<wow>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Item: TextView
        var Amount: TextView
        var Date:TextView
        init {
            // Define click listener for the ViewHolder's View
            Item = itemView.findViewById(R.id.tvitem)
            Amount=itemView.findViewById(R.id.tvamount)
            Date=itemView.findViewById(R.id.date)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.transactions,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return bud.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Item.text=bud[position].title
        holder.Amount.text=bud[position].amount
        holder.Date.text=bud[position].date
        val context = holder.itemView.getContext()
        holder.itemView.setOnClickListener()
        {val Intent= Intent(context,Update::class.java).also {
                it.putExtra("amount",bud.get(position).amount)
                it.putExtra("item",bud[position].title)
                it.putExtra("id",bud[position].id)
                it.putExtra("title","Update Transaction")

            context.startActivity(it)}
        }


    }

}
