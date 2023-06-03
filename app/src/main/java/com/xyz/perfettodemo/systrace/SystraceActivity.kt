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
            val bitmap = zipBitMap()
            binding.opencvSampleImageView.setImageBitmap(bitmap)
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            binding.opencvSampleButton.text = "zipBitMap After"
        }
    }


    fun zipBitMap(): Bitmap {
        val options = BitmapFactory.Options()
        //只得到图片的宽和高
        options.inJustDecodeBounds = true

        //设置图片的压缩比例
        options.inSampleSize = computSampleSize(options, 200, 320)
        //设置完压缩比酷之后，必须将这个属性改为false
        options.inJustDecodeBounds = false
        // 尺寸压缩1/2
        options.inSampleSize = 4
        //得到传递过来的图片的信息
        return BitmapFactory.decodeResource(resources, R.drawable.adventure_time, options)
    }

    private fun computSampleSize(options: BitmapFactory.Options, w: Int, h: Int): Int {
        val width = options.outWidth
        val height = options.outHeight
        //图片的缩小比例，只要小于等于，就是保持原图片的大小不变
        var inSqmpleSize = 1
        if (width > w || height > h) {
            val zipSizeWidth = Math.round((width / w).toFloat())
            val zipSizeHeight = Math.round((height / h).toFloat())
            inSqmpleSize = if (zipSizeWidth < zipSizeHeight) zipSizeWidth else zipSizeHeight
        }
        return inSqmpleSize
    }
}