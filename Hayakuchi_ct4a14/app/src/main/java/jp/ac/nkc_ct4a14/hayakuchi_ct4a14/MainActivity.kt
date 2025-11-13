package jp.ac.nkc_ct4a14.hayakuchi_ct4a14

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.util.Locale

private const val SPEECH_RATE_FAST = 2.0f
private const val SPEECH_RATE_NORMAL = 1.0f
private const val SPEECH_RATE_SLOW = 0.5f

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var inputText: EditText
    private lateinit var buttonGroup: LinearLayout
    private lateinit var btnFast: MaterialButton
    private lateinit var btnNormal: MaterialButton
    private lateinit var btnSlow: MaterialButton

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
        textToSpeech = TextToSpeech(this, this)
    }

    private val initializeViews = {
        inputText = findViewById(R.id.inputText)
        buttonGroup = findViewById(R.id.buttonGroup)
        btnFast = findViewById(R.id.btnFast)
        btnNormal = findViewById(R.id.btnNormal)
        btnSlow = findViewById(R.id.btnSlow)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.JAPAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                return
            }
            setupListeners()
            buttonGroup.visibility = View.VISIBLE
        }
    }

    private val setupListeners = {
        btnFast.setOnClickListener { speakText(SPEECH_RATE_FAST) }
        btnNormal.setOnClickListener { speakText(SPEECH_RATE_NORMAL) }
        btnSlow.setOnClickListener { speakText(SPEECH_RATE_SLOW) }
    }

    private val speakText: (Float) -> Unit = { rate ->
        val text = inputText.text.toString()
        if (text.isNotEmpty()) {
            textToSpeech.setSpeechRate(rate)
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}