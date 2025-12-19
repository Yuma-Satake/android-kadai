package jp.ac.nkc_ct4a14.quiz_ct4a14

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.ac.nkc_ct4a14.quiz_ct4a14.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rightAnswerCount = intent.getIntExtra(MainActivity.RIGHT_ANSWER_COUNT, 0)
        binding.resultLabel.text = getString(R.string.result_score, rightAnswerCount)

        binding.tryAgainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
