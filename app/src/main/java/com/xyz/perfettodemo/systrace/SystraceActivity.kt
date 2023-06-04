package com.xyz.perfettodemo.systrace

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Trace
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            val bitmap = zipBitMap(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.adventure_time
                )
            )
            // 设置压缩后图片显示
            binding.opencvSampleImageView.setImageBitmap(bitmap)
            // Toast提示
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            // 修改按钮文字
            binding.opencvSampleButton.text = "zipBitMap After"
        }
    }

    fun zipBitMap(bitmap: Bitmap): Bitmap {
        var bitmap = bitmap
        // TODO 这里是模拟增加耗时，对应 Profielr 的 Systrace 中可以看到
        val width = bitmap.width
        val height = bitmap.height
        var scaledWidth = width / 2
        var scaledHeight = height / 2

        var i = 1
        while (i < 200) {  // 重复500次 模拟耗时操作
            bitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)
            scaledWidth /= 2
            scaledHeight /= 2

            if (i % 2 == 0) {
                scaledWidth = width / 2
                scaledHeight = height / 2
            }

            i++
        }
        return bitmap
    }
}