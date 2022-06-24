package com.example.perrodex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perrodex.auth.LoginActivity
import com.example.perrodex.databinding.ActivityMainBinding
import com.example.perrodex.model.User
import com.example.perrodex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if(user == null){
            openLoginActivity()
            return
        }

        binding.settingsFab.setOnClickListener {
            openSettingsActivity()
        }
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}