package com.example.perrodex.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.perrodex.auth.LoginActivity
import com.example.perrodex.databinding.ActivitySettingsBinding
import com.example.perrodex.model.User

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logoutButton.setOnClickListener {
            logout()
        }
    }

    //Borra sesion del usuario e inicia el login activity
    private fun logout() {
        User.logout(this)
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}