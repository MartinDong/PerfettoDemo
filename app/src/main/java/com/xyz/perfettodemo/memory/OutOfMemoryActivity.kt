package com.xyz.perfettodemo.memory

import android.os.Bundle
import android.os.Trace
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xyz.perfettodemo.databinding.ActivityOutOfMemoryBinding

class OutOfMemoryActivity : AppCompatActivity(){

    private lateinit var binding: ActivityOutOfMemoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutOfMemoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}