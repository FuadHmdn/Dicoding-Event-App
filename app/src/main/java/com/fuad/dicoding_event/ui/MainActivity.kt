package com.fuad.dicoding_event.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fuad.dicoding_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}