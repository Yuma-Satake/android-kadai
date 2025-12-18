package jp.ac.nkc_ct4a14.webapi_ct4a14

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.button)
        val postCodeText = findViewById<TextView>(R.id.postCodeText)
        val mainUrl = "http://zipcloud.ibsnet.co.jp/api/search?zipcode="

        button.setOnClickListener {
            val postCode = postCodeText.text.toString()
            val postCodeUrl = mainUrl + postCode

            postCodeTask(postCodeUrl)
        }
    }

    private fun postCodeTask(postCodeUrl: String) {
        lifecycleScope.launch {
            val result = postCodeBackgroundTask(postCodeUrl)

            postCodeJsonTask(result)
        }
    }

    private suspend fun postCodeBackgroundTask(postCodeUrl: String): String {
        val response = withContext(Dispatchers.IO) {
            var httpResult = ""

            try {
                val urlObj = URL(postCodeUrl)

                val br = BufferedReader(InputStreamReader(urlObj.openStream()))

                httpResult = br.readText()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return@withContext httpResult
        }

        return response
    }

    private fun postCodeJsonTask(result: String) {
        val add1 = findViewById<TextView>(R.id.add1)
        val add2 = findViewById<TextView>(R.id.add2)
        val add3 = findViewById<TextView>(R.id.add3)
        val errText = findViewById<TextView>(R.id.errText)

        try {
            val jsonObj = JSONObject(result)
            val errorText: String = jsonObj.getString("message")

            if (jsonObj.isNull("results")) {
                add1.text = ""
                add2.text = ""
                add3.text = ""
                errText.text = "該当する住所が見つかりませんでした"
            } else {
                val perentJsonArray = jsonObj.getJSONArray("results")
                val detailJsonObj = perentJsonArray.getJSONObject(0)

                add1.text = detailJsonObj.getString("address1")
                add2.text = detailJsonObj.getString("address2")
                add3.text = detailJsonObj.getString("address3")
                errText.text = errorText
            }
        } catch (e: JSONException) {
            errText.text = "エラーが発生しました"
        }
    }
}
