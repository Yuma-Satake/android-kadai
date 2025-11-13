package jp.ac.nkc_ct4a14.slider_ct4a14

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val imageResources = listOf(
        R.drawable.dog,
        R.drawable.horse,
        R.drawable.kitten
    )

    private val quotes = listOf(
        "人生は自転車に乗るようなものだ。バランスを保つには動き続けなければならない。\n- アルベルト・アインシュタイン",
        "成功とは、情熱を失わずに失敗から失敗へと進んでいくことである。\n- ウィンストン・チャーチル",
        "明日死ぬかのように生きよ。永遠に生きるかのように学べ。\n- マハトマ・ガンジー"
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView = findViewById<ImageView>(R.id.imageView)
        val quoteText = findViewById<TextView>(R.id.quoteText)
        val prevButton = findViewById<TextView>(R.id.prevButton)
        val nextButton = findViewById<TextView>(R.id.nextButton)

        applySepiaFilter(imageView)
        updateContent(imageView, quoteText)

        prevButton.setOnClickListener {
            currentIndex = if (currentIndex == 0) {
                imageResources.size - 1
            } else {
                currentIndex - 1
            }
            updateContent(imageView, quoteText)
        }

        nextButton.setOnClickListener {
            currentIndex = if (currentIndex == imageResources.size - 1) {
                0
            } else {
                currentIndex + 1
            }
            updateContent(imageView, quoteText)
        }
    }

    private val applySepiaFilter = { imageView: ImageView ->
        val sepiaMatrix = ColorMatrix().apply {
            setSaturation(0f)
        }
        val sepiaColorMatrix = ColorMatrix(
            floatArrayOf(
                1.07f, 0.17f, 0.05f, 0f, 0f,
                0.20f, 0.90f, 0.15f, 0f, 0f,
                0.16f, 0.16f, 0.56f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        sepiaMatrix.postConcat(sepiaColorMatrix)
        imageView.colorFilter = ColorMatrixColorFilter(sepiaMatrix)
    }

    private val updateContent = { imageView: ImageView, quoteText: TextView ->
        imageView.setImageResource(imageResources[currentIndex])
        quoteText.text = quotes[currentIndex]
    }
}