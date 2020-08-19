package com.khurshid.kamkora.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.khurshid.kamkora.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()


        Handler().postDelayed({
            var intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}