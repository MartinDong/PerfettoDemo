package com.xyz.perfettodemo.systrace

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Trace
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.xyz.perfettodemo.databinding.ActivityRenderBlockBinding

class RenderBlockActivity : AppCompatActivity() {
    var count =0
    private val handler = Handler()
    private val runnable = object : Runnable {
        override fun run() {
            val start = System.currentTimeMillis()
            // 卡顿操作
            val bitmap = Bitmap.createBitmap(2560, 1920, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            if (count%2==0){
                canvas.drawColor(Color.RED)
            }else{
                canvas.drawColor(Color.GREEN)
            }
            binding.imageView.setImageBitmap(bitmap)
            val end = System.currentTimeMillis()
            Log.d("RenderBlock", "Cost: ${end - start}ms")
            handler.postDelayed(this, 16)   // 16ms后重新运行
            count++
        }
    }

    private lateinit var binding: ActivityRenderBlockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRenderBlockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Trace.beginAsyncSection("onCreate", 111)
        binding.btnStartRender.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                handler.post(runnable)
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}