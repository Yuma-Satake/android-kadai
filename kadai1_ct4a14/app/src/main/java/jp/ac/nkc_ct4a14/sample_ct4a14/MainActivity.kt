package jp.ac.nkc_ct4a14.sample_ct4a14

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var tvOutput: TextView
    private lateinit var btAsa: Button
    private lateinit var btHiru: Button
    private lateinit var btYoru: Button

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
    }

    private val initializeViews = {
        tvOutput = findViewById(R.id.tvOutput)
        btAsa = findViewById(R.id.btAsa)
        btHiru = findViewById(R.id.btHiru)
        btYoru = findViewById(R.id.btYoru)
    }

    private val setupListeners = {
        btAsa.setOnClickListener { showMorningGreeting() }
        btHiru.setOnClickListener { showAfternoonGreeting() }
        btYoru.setOnClickListener { showEveningGreeting() }
    }

    private val showMorningGreeting = {
        tvOutput.text = getString(R.string.greeting_morning)
    }

    private val showAfternoonGreeting = {
        tvOutput.text = getString(R.string.greeting_afternoon)
    }

    private val showEveningGreeting = {
        tvOutput.text = getString(R.string.greeting_evening)
    }
}