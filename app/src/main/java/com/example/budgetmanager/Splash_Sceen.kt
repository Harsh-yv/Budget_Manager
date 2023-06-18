package com.example.budgetmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Splash_Sceen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_sceen)
        val iv=findViewById<ImageView>(R.id.iv)
        iv.alpha=0f
        iv.animate().setDuration(1500).alpha(1f).withEndAction{
            val intent= Intent(this,Login::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }
    }
}