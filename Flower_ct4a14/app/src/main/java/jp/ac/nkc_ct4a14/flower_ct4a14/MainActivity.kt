package jp.ac.nkc_ct4a14.flower_ct4a14

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var currentStep: Int = 0
    private lateinit var statusText: TextView
    private lateinit var plantImage: ImageView
    private lateinit var waterButton: Button
    private lateinit var resetButton: Button

    private val maxStep: Int = 7
    private val plantImages: List<Int> = listOf(
        R.drawable.f0,
        R.drawable.f1,
        R.drawable.f2,
        R.drawable.f3,
        R.drawable.f4,
        R.drawable.f5
    )
    private val statusMessages: List<Int> = listOf(
        R.string.message0,
        R.string.message1,
        R.string.message2,
        R.string.message3,
        R.string.message4,
        R.string.message5,
        R.string.message6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeViews()
        setupListeners()
        updateUI()
    }

    private val initializeViews = {
        statusText = findViewById(R.id.statusText)
        plantImage = findViewById(R.id.plantImage)
        waterButton = findViewById(R.id.waterButton)
        resetButton = findViewById(R.id.resetButton)
    }

    private val setupListeners = {
        waterButton.setOnClickListener { giveWater() }
        resetButton.setOnClickListener { reset() }
    }

    private val giveWater = {
        if (currentStep < maxStep - 1) {
            currentStep = currentStep + 1
            updateUI()
        }
    }

    private val reset = {
        currentStep = 0
        updateUI()
    }

    private val updateUI = {
        val imageIndex = if (currentStep < plantImages.size) currentStep else plantImages.size - 1
        plantImage.setImageResource(plantImages[imageIndex])

        val messageIndex = if (currentStep < statusMessages.size) currentStep else statusMessages.size - 1
        statusText.text = getString(statusMessages[messageIndex])
    }
}