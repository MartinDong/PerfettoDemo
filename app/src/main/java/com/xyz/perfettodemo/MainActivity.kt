package com.xyz.perfettodemo

import android.content.Intent
import android.os.Bundle
import android.os.Trace
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.xyz.perfettodemo.systrace.SystraceActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Trace.beginAsyncSection("onCreate", 111)

    }

    fun openSystraceDemo(view: View) {
        val systraceIntent = Intent(this, SystraceActivity::class.java)

        startActivity(systraceIntent)
    }


}