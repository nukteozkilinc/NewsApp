package com.nukte.denemedeneme.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nukte.denemedeneme.MainActivity
import com.nukte.denemedeneme.R
import com.nukte.denemedeneme.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.imageView.alpha = 0f

            binding.imageView.animate().setDuration(1500).alpha(1f).withEndAction{
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                    )
                finish()
            }
        }
    }
}