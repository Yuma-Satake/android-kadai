package jp.ac.nkc_ct4a14.kadai3_ct4a14

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
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

    companion object {
        private const val BASE_URL = "https://weather.tsukumijima.net/api/forecast/city/"
        private const val CITY_TOKYO = "130010"
        private const val CITY_NAGOYA = "230010"
        private const val CITY_OSAKA = "270000"
    }

    private lateinit var cityTitle: TextView
    private lateinit var descriptionText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorText: TextView

    private lateinit var dateLabels: Array<TextView>
    private lateinit var weatherTexts: Array<TextView>
    private lateinit var tempTexts: Array<TextView>
    private lateinit var weatherImages: Array<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupButtons()

        fetchWeather(CITY_NAGOYA)
    }

    private fun initViews() {
        cityTitle = findViewById(R.id.cityTitle)
        descriptionText = findViewById(R.id.descriptionText)
        progressBar = findViewById(R.id.progressBar)
        errorText = findViewById(R.id.errorText)

        dateLabels = arrayOf(
            findViewById(R.id.dateLabel0),
            findViewById(R.id.dateLabel1),
            findViewById(R.id.dateLabel2)
        )

        weatherTexts = arrayOf(
            findViewById(R.id.weather0),
            findViewById(R.id.weather1),
            findViewById(R.id.weather2)
        )

        tempTexts = arrayOf(
            findViewById(R.id.temp0),
            findViewById(R.id.temp1),
            findViewById(R.id.temp2)
        )

        weatherImages = arrayOf(
            findViewById(R.id.weatherImage0),
            findViewById(R.id.weatherImage1),
            findViewById(R.id.weatherImage2)
        )
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.buttonTokyo).setOnClickListener {
            fetchWeather(CITY_TOKYO)
        }

        findViewById<Button>(R.id.buttonNagoya).setOnClickListener {
            fetchWeather(CITY_NAGOYA)
        }

        findViewById<Button>(R.id.buttonOsaka).setOnClickListener {
            fetchWeather(CITY_OSAKA)
        }
    }

    private fun fetchWeather(cityCode: String) {
        val url = BASE_URL + cityCode

        progressBar.visibility = View.VISIBLE
        errorText.visibility = View.GONE

        lifecycleScope.launch {
            val result = fetchWeatherData(url)
            parseAndDisplayWeather(result)
        }
    }

    private suspend fun fetchWeatherData(url: String): String {
        return withContext(Dispatchers.IO) {
            var httpResult = ""
            try {
                val urlObj = URL(url)
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            httpResult
        }
    }

    private suspend fun loadImage(imageUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                BitmapFactory.decodeStream(url.openStream())
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun parseAndDisplayWeather(result: String) {
        progressBar.visibility = View.GONE

        if (result.isEmpty()) {
            errorText.text = "データの取得に失敗しました"
            errorText.visibility = View.VISIBLE
            return
        }

        try {
            val jsonObj = JSONObject(result)

            val title = jsonObj.getString("title")
            cityTitle.text = title

            val description = jsonObj.getJSONObject("description")
            val bodyText = description.getString("bodyText")
            descriptionText.text = bodyText

            val forecasts = jsonObj.getJSONArray("forecasts")

            for (i in 0 until minOf(forecasts.length(), 3)) {
                val forecast = forecasts.getJSONObject(i)

                val dateLabel = forecast.getString("dateLabel")
                dateLabels[i].text = dateLabel

                val telop = forecast.getString("telop")
                weatherTexts[i].text = telop

                val temperature = forecast.getJSONObject("temperature")
                val maxTemp = if (!temperature.isNull("max") && !temperature.getJSONObject("max").isNull("celsius")) {
                    temperature.getJSONObject("max").getString("celsius")
                } else {
                    "-"
                }
                val minTemp = if (!temperature.isNull("min") && !temperature.getJSONObject("min").isNull("celsius")) {
                    temperature.getJSONObject("min").getString("celsius")
                } else {
                    "-"
                }
                tempTexts[i].text = "$maxTemp\u2103 / $minTemp\u2103"

                val image = forecast.getJSONObject("image")
                val imageUrl = image.getString("url")

                val index = i
                lifecycleScope.launch {
                    val bitmap = loadImage(imageUrl)
                    bitmap?.let {
                        weatherImages[index].setImageBitmap(it)
                    }
                }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
            errorText.text = "データの解析に失敗しました"
            errorText.visibility = View.VISIBLE
        }
    }
}
