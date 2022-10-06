package com.example.philosophyquotes.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.philosophyquotes.BuildConfig
import com.example.philosophyquotes.R
import com.example.philosophyquotes.databinding.ActivityHomeBinding
import com.example.philosophyquotes.viewmodel.HomeState
import com.example.philosophyquotes.viewmodel.HomeViewModel
import com.example.philosophyquotes.viewmodel.state.UiState
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    private var quoteToSend = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        setListeners()

        setContentView(binding.root)
    }

    private fun setListeners() {
        viewModel.apply {
            uiState.observe(this@HomeActivity) {
                when (it) {
                    is UiState.Loading -> {
                        binding.shimmerLayout.startShimmer()
                        handleContentVisibility("content", View.GONE)
                        handleContentVisibility("shimmer", View.VISIBLE)
                    }

                    is UiState.Success -> {
                        handleSuccessState(it.data)
                    }

                    else -> {}
                }
            }
        }

        binding.refreshQuoteButton.setOnClickListener {
            viewModel.getRandomQuote()
        }

        binding.buttonSend.setOnClickListener {
            shareImage(quoteToSend)
        }
    }

    private fun shareText() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out an awesome philosophy quote at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun shareImage(quote: String) {
        val image = drawMultilineTextToBitmap(quote)
        saveImageToCacheDirectory(image)

        val imagePath = File(this.cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri =
            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)

            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
    }

    private fun drawMultilineTextToBitmap(text: String): Bitmap {
        val resources = this.resources
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

    private fun saveImageToCacheDirectory(bitmap: Bitmap) {
        try {
            val cachePath = File(this.cacheDir, "images")
            cachePath.mkdirs()
            val stream =
                FileOutputStream("$cachePath/image.png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        supportActionBar?.hide()

        handleContentVisibility("content", View.GONE)
    }

    private fun handleSuccessState(state: HomeState?) {
        val name = state?.userName
        val quote = state?.quote

        quoteToSend = quote?.quote ?: ""

        if (name?.isNotEmpty() == true) {
            binding.textUserName.text = buildString {
                append("Hello, ")
                append(name)
                append("!")
            }
        }

        if (quote != null) {
            binding.quoteDescription.text = quote.quote
            binding.quoteAuthor.text = buildString {
                append("- ")
                append(quote.author)
            }

            handleContentVisibility("shimmer", View.GONE)
            handleContentVisibility("content", View.VISIBLE)
            binding.shimmerLayout.stopShimmer()
        }
    }

    private fun handleContentVisibility(element: String, id: Int) {
        when (element) {
            "content" -> {
                binding.homeContent.apply {
                    visibility = id
                }
            }
            "shimmer" -> {
                binding.shimmerLayout.apply {
                    visibility = id
                }
            }
        }
    }
}
