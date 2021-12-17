package com.example.petminder.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.petminder.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slash_screen)

        Handler().postDelayed({
            val intent = Intent(this, PetListActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

}