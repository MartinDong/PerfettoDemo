package com.xyz.perfettodemo

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object  BitmapUtils {
    /**
     * 这里呈现的时序图是一个完整的大块，方便演示
     */
    fun zipBitMap(resources: Resources,resId:Int): Bitmap {
        var bitmap: Bitmap
        val options = BitmapFactory.Options()
        //只得到图片的宽和高
        options.inJustDecodeBounds = true

        //设置图片的压缩比例
        options.inSampleSize = computSampleSize(options, 200, 320)
        //设置完压缩比酷之后，必须将这个属性改为false
        options.inJustDecodeBounds = false
        // 尺寸压缩1/2
        options.inSampleSize = 4

        var i = 1
        while (i < 10) {  // 重复500次 模拟耗时操作
            bitmap = BitmapFactory.decodeResource(resources, resId, options)
            i++
        }

        //得到传递过来的图片的信息
        return BitmapFactory.decodeResource(resources, resId, options)
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