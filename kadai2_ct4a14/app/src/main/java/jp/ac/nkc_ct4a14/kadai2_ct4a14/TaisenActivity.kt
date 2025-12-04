package jp.ac.nkc_ct4a14.kadai2_ct4a14

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class TaisenActivity : AppCompatActivity() {

    companion object {
        private const val JUDGE_DRAW = 0
        private const val JUDGE_WIN = 1
        private const val JUDGE_LOSE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_taisen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ivMyHand = findViewById<ImageView>(R.id.ivMyHand)
        val ivComHand = findViewById<ImageView>(R.id.ivComHand)
        val tvJudge = findViewById<TextView>(R.id.tvJudge)
        val btBack = findViewById<Button>(R.id.btBack)

        val myHand = intent.getIntExtra(MainActivity.EXTRA_MY_HAND, MainActivity.HAND_GU)
        val comHand = Random.nextInt(3)

        ivMyHand.setImageResource(getMyHandDrawable(myHand))
        ivComHand.setImageResource(getComHandDrawable(comHand))

        val judge = judgeJanken(myHand, comHand)
        tvJudge.setText(getJudgeStringRes(judge))

        btBack.setOnClickListener {
            finish()
        }
    }

    /**
     * 自分の手に対応するdrawableリソースIDを返す
     */
    private fun getMyHandDrawable(hand: Int): Int =
        when (hand) {
            MainActivity.HAND_GU -> R.drawable.my_gu
            MainActivity.HAND_CHOKI -> R.drawable.my_choki
            MainActivity.HAND_PA -> R.drawable.my_pa
            else -> R.drawable.my_gu
        }

    /**
     * コンピュータの手に対応するdrawableリソースIDを返す
     */
    private fun getComHandDrawable(hand: Int): Int =
        when (hand) {
            MainActivity.HAND_GU -> R.drawable.com_gu
            MainActivity.HAND_CHOKI -> R.drawable.com_choki
            MainActivity.HAND_PA -> R.drawable.com_pa
            else -> R.drawable.com_gu
        }

    /**
     * ジャンケンの勝敗を判定する
     * (コンピュータの手 - 自分の手 + 3) % 3
     * 0: あいこ, 1: 負け, 2: 勝ち
     */
    private fun judgeJanken(myHand: Int, comHand: Int): Int =
        (comHand - myHand + 3) % 3

    /**
     * 判定結果に対応する文字列リソースIDを返す
     */
    private fun getJudgeStringRes(judge: Int): Int =
        when (judge) {
            JUDGE_WIN -> R.string.tv_win
            JUDGE_LOSE -> R.string.tv_lose
            JUDGE_DRAW -> R.string.tv_draw
            else -> R.string.tv_draw
        }
}
