package com.example.philosophyquotes.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.example.philosophyquotes.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

abstract class MultilineTextDraw {
    companion object {
        fun drawTextToImage(context: Context, text: String) {
            val bitmap = drawMultilineTextToBitmap(context, text)
            saveImageToCacheDirectory(context, bitmap)
        }

        private fun drawMultilineTextToBitmap(context: Context, text: String): Bitmap {
            val resources = context.resources
            val scale: Float = resources.displayMetrics.density
            var bitmap = BitmapFactory.decodeResource(resources, R.drawable.share_content_bg)

            if (bitmap.config == null) {
                bitmap.config = Bitmap.Config.ARGB_8888
            }

            bitmap = bitmap.copy(bitmap.config, true)

            val canvas = Canvas(bitmap)

            val textPaint = TextPaint().apply {
                isAntiAlias = true
                textSize = (60 * scale).toInt().toFloat()
                color = Color.BLACK
                setShadowLayer(1f, 0f, 1f, Color.WHITE)
            }

            val textWidth = canvas.width - (16 * scale).toInt()

            val staticTextLayout =
                StaticLayout.Builder.obtain(text, 0, text.length, textPaint, textWidth)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0.0f, 1.0f)
                    .setIncludePad(false)
                    .build()

            val textHeight = staticTextLayout.height

            val x = ((bitmap.width - textWidth) / 2).toFloat()
            val y = ((bitmap.height - textHeight) / 2).toFloat()

            canvas.save()
            canvas.translate(x, y)
            staticTextLayout.draw(canvas)
            canvas.restore()

            return bitmap
        }

        private fun saveImageToCacheDirectory(context: Context, bitmap: Bitmap) {
            try {
                val cachePath = File(context.cacheDir, "images")
                cachePath.mkdirs()
                val stream =
                    FileOutputStream("$cachePath/image.png")
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}