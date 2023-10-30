package com.indong.choirfinder.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.indong.choirfinder.R

class LoadingScreenActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Note set Splash Image!
        setContentView(R.layout.splashlayout)

       // Handler(Looper.getMainLooper()).postDelayed({
            val startIntent = Intent(this,StartActivity::class.java)
            startActivity(startIntent)
            finish()
        //},3000)
    }

}