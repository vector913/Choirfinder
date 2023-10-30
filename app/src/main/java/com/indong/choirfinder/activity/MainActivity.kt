package com.indong.choirfinder.activity

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.indong.choirfinder.R
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainlayout)

        val btn_click = findViewById<Button>(R.id.button)
        val inputText = findViewById<EditText>(R.id.editTextText)
        val outputText = findViewById<TextView>(R.id.textView)

        btn_click.setOnClickListener{
            val strData = getTubeData(inputText.text.toString()).execute()
            outputText.text = strData.toString()
        }
    }

    class getTubeData(string:String) : AsyncTask<Void, Void, String>(){

        var API_KEY = "AIzaSyC809SSy6K9bzrWWsh6EPI5GJiFr8YM7cE" //Note 개인키므로 이건 업데이트 되지 않게 주의.
        var vodid = ""
        var videoID = ""
        var intentSearch = string


        override fun doInBackground(vararg params: Void?): String {
            // ...
            try {
                val jsonObject: JSONObject? = getUtube()
                if(jsonObject !=null){
                    paringJsonData(jsonObject)
                }else{
                    Log.e("AsyncTask","json is null")
                }

            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
        }

        fun getUtube(): JSONObject? {
            val originUrl = ("https://www.googleapis.com/youtube/v3/search?"
                    + "part=snippet&q=" + intentSearch
                    ) + "&key=" + API_KEY + "&maxResults=50"
            //&maxResults=3 여기는 몇개의 항목을 나타낼 것 인지임
            //나는 상위 한개의 항목만 필요하지만 그냥 3개 했음
            //최대 50개의 항목까지 쓸 수 있는걸로 알고 있음

            //&maxResults=3 여기는 몇개의 항목을 나타낼 것 인지임
            //나는 상위 한개의 항목만 필요하지만 그냥 3개 했음
            //최대 50개의 항목까지 쓸 수 있는걸로 알고 있음
            val url = URL(originUrl)

            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.readTimeout = 10000
            connection.connectTimeout = 15000
            connection.connect()

            var line: String?
            var result = ""
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuffer()

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            result = response.toString()

            val jsonObject: JSONObject
            try {
                jsonObject = JSONObject(result)
                return jsonObject
            } catch (e: JSONException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
            return null
        }

        @Throws(JSONException::class)
        private fun paringJsonData(jsonObject: JSONObject) {
            val contacts = jsonObject.getJSONArray("items")

            //앞서 말했다 싶이 나는 상위 1개 영상만을 재생할 것이기에
            //반복문을 사용하지 않고 바로 인덱스 0번 정보를 가져왔다
            val c = contacts.getJSONObject(0)
            val kind = c.getJSONObject("id").getString("kind") // 종류를 체크하여 playlist도 저장
            if (kind == "youtube#video") {
                vodid = c.getJSONObject("id").getString("videoId") // 유튜브
            } else {
                vodid = c.getJSONObject("id").getString("channelId") // 유튜브
            }
            videoID = vodid
            //사실상 videoID와 vodid는 같은 String형이라 그냥 vodid 갖다써도 되는데
            // 왜 이렇게 했는지;;
            Log.i("비디오", vodid)
        }

    }
}