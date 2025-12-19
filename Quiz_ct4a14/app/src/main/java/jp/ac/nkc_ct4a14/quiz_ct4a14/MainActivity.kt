package jp.ac.nkc_ct4a14.quiz_ct4a14

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import jp.ac.nkc_ct4a14.quiz_ct4a14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val QUIZ_COUNT = 5
        const val RIGHT_ANSWER_COUNT = "right_answer_count"
    }

    private lateinit var binding: ActivityMainBinding

    private val quizData = mutableListOf(
        listOf("膃肭臍", "おっとせい"),
        listOf("馴鹿", "となかい"),
        listOf("水豚", "かぴばら"),
        listOf("饂飩", "うどん"),
        listOf("石刁柏", "あすぱらがす"),
        listOf("馬穴", "ばけつ"),
        listOf("杓文字", "しゃもじ")
    )

    private var rightAnswer: String? = null
    private var rightAnswerCount = 0
    private var quizCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizData.shuffle()
        showNextQuiz()

        binding.inputAnswer.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                checkAnswer()
                true
            } else {
                false
            }
        }
    }

    private fun showNextQuiz() {
        binding.countLabel.text = getString(R.string.count_label, quizCount)

        val quiz = quizData.removeAt(0)
        binding.questionLabel.text = quiz[0]
        rightAnswer = quiz[1]

        binding.inputAnswer.text.clear()
    }

    private fun checkAnswer() {
        val userAnswer = binding.inputAnswer.text.toString()
        val isCorrect = userAnswer == rightAnswer

        if (isCorrect) {
            rightAnswerCount++
        }

        val dialog = AnswerDialogFragment.newInstance(isCorrect, rightAnswer ?: "")
        dialog.show(supportFragmentManager, "answer_dialog")
    }

    fun checkQuizCount() {
        if (quizCount < QUIZ_COUNT) {
            quizCount++
            showNextQuiz()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(RIGHT_ANSWER_COUNT, rightAnswerCount)
            startActivity(intent)
            finish()
        }
    }
}
