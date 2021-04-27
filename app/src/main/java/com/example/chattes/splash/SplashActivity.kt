package com.example.chattes.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chattes.R
import com.example.chattes.tabbar.MainActivity
import com.example.chattes.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val sharedPref =
            getSharedPreferences(LoginActivity.SHARED_PREF_FILENAME, Context.MODE_PRIVATE)
        val userIsLoggedIn = sharedPref.getBoolean(LoginActivity.LOGGED_IN_KEY, false)

        val activityIntent = if (userIsLoggedIn) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }

        activityIntent.flags = activityIntent.flags or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(activityIntent)
    }
}