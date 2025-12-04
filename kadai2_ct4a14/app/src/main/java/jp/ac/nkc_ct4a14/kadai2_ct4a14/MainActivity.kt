package jp.ac.nkc_ct4a14.kadai2_ct4a14

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MY_HAND = "my_hand"
        const val HAND_GU = 0
        const val HAND_CHOKI = 1
        const val HAND_PA = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ibMyGu = findViewById<ImageButton>(R.id.ibMyGu)
        val ibMyChoki = findViewById<ImageButton>(R.id.ibMyChoki)
        val ibMyPa = findViewById<ImageButton>(R.id.ibMyPa)

        ibMyGu.setOnClickListener {
            startTaisenActivity(HAND_GU)
        }

        ibMyChoki.setOnClickListener {
            startTaisenActivity(HAND_CHOKI)
        }

        ibMyPa.setOnClickListener {
            startTaisenActivity(HAND_PA)
        }
    }

    private fun startTaisenActivity(myHand: Int) {
        val intent = Intent(this, TaisenActivity::class.java)
        intent.putExtra(EXTRA_MY_HAND, myHand)
        startActivity(intent)
    }
}
