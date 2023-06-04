package com.xyz.perfettodemo.systrace

import android.os.Bundle
import android.os.Trace
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xyz.perfettodemo.BitmapUtils
import com.xyz.perfettodemo.R
import com.xyz.perfettodemo.databinding.ActivitySystraceBinding


class SystraceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySystraceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystraceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Trace.beginAsyncSection("onCreate", 111)
        binding.opencvSampleButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!!.id === R.id.opencvSampleButton) {
            // 压缩图片
            val bitmap = BitmapUtils.zipBitMap(resources,R.drawable.adventure_time)
            // 设置压缩后图片显示
            binding.opencvSampleImageView.setImageBitmap(bitmap)
            // Toast提示
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            // 修改按钮文字
            binding.opencvSampleButton.text = "zipBitMap After"
        }
    }

}