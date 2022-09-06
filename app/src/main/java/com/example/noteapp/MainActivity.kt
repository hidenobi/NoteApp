package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addFragment()
    }

    private fun addFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.frameContainer,
            HomeFragment()
        ).commit()
    }
}