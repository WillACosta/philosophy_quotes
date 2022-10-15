package com.example.philosophyquotes.core.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.philosophyquotes.BuildConfig
import java.io.File

enum class ShareContentType { Image, Text }

class HelperFunctions {
    companion object {
        fun <T> startActivity(context: Context, className: Class<T>) {
            context.startActivity(Intent(context, className))
        }

        fun shareContent(context: Context, contentType: ShareContentType, textToShare: String) {
            when (contentType) {
                ShareContentType.Image -> {
                    MultilineTextDraw.drawTextToImage(context, textToShare)

                    val contentUri = createImageFileFromCacheDir(context)

                    if (contentUri != null) {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                            setDataAndType(contentUri, context.contentResolver.getType(contentUri))
                            putExtra(Intent.EXTRA_STREAM, contentUri)
                        }

                        context.startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "Hey check out an awesome philosophy quote at: https://play.google.com/store/apps/details?id= + ${BuildConfig.APPLICATION_ID}"
                            )
                        )
                    }
                }

                ShareContentType.Text -> {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            textToShare
                        )
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            }
        }

        private fun createImageFileFromCacheDir(context: Context): Uri? {
            val imagePath = File(context.cacheDir, "images")
            val newFile = File(imagePath, "image.png")

            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, newFile)
        }
    }
}