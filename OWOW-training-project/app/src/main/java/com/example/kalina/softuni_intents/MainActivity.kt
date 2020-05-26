package com.example.kalina.softuni_intents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.androidnetworking.AndroidNetworking
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.txt_email
import kotlinx.android.synthetic.main.activity_second.*
import com.androidnetworking.error.ANError
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.androidnetworking.common.Priority
import android.R.id
import com.androidnetworking.interfaces.ParsedRequestListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.fragment.app.FragmentActivity
import org.json.JSONObject
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.AsyncTask
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var b: Button
    private lateinit var txt_register: TextView
    private var request_code: Int = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initListeners()

        val thread : Thread = Thread(Runnable(){
     run() {
    try {
        var example: TestMain = TestMain()
        var json: String = ""
        var getResponse: String = example.doPostRequest("https://api.github.help",json)
        Log.d("TestResponse",getResponse)
    } catch (e: java.lang.Exception) {
      e.printStackTrace()
    }
  }
});
        thread.start()
      // runTest()

    }

    private fun runTest() {
        var client : OkHttpClient = OkHttpClient();
        var urlBuilder : HttpUrl.Builder= HttpUrl.parse("https://api.github.help")?.newBuilder() ?: HttpUrl.Builder();
        urlBuilder.addQueryParameter("v", "1.0");
        urlBuilder.addQueryParameter("user", "vogella");
        var url: String= urlBuilder.build().toString()
        var request : Request = Request.Builder()
            .url("https://www.vogella.com/index.html")
            .build()
        var response : Response = client.newCall(request).execute()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle this
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("Hello","Hello")
            }
        })
    }


    private fun parseTagsAPI(response: String): ArrayList<String> {
        val tagTemp = ArrayList<String>()
        try {
            var tag = ""
            var logtag = "test, test2"

            val jsonObject = JSONObject(response)
            if (jsonObject.has("track")) {
                val jsonTrack = jsonObject.getJSONObject("track")
                Log.d("API (tag)", "track")

                if (jsonObject.has("toptags")) {
                    val jsonArray = jsonTrack.getJSONObject("toptags").getJSONArray("tag")
                    Log.d("API (tag)", "track > album > toptags > tag(array)")

                    for (i in 0 until jsonArray.length()) {
                        if (jsonArray.getJSONObject(i).has("name")) {
                            val tagArray = jsonArray.getJSONObject(i).getJSONObject("name")
                            tag = tagArray.optString("name")

                            logtag += "$tag, "
                            Log.d("API (tag)", "tags : $tag")
                        }
                        tagTemp.add(tag)
                    }
                }
            }
            Log.d("API (tag)", "Logtag : $logtag")
        } catch (e: Exception) {
            e.printStackTrace()
            return ArrayList()
        }

        return tagTemp
    }
    private fun startMainScreen(view: View) {
        val emailTxt: EditText = findViewById(R.id.txt_email)
        val email = emailTxt.text.toString()
        val intent = Intent(view.context, SecondActivity::class.java)
        intent.putExtra("user_email", email)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    private fun initViews() {
        this.b = findViewById(R.id.btn_login)
        this.txt_register = findViewById(R.id.txt_register)
    }

    private fun initListeners() {
        b.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Works", LENGTH_LONG).show()
            startMainScreen(b)
        })
        txt_register.setOnClickListener {
            startRegisterScreen()
        }
    }

    private fun startRegisterScreen() {

        val intentRegister = Intent(this, RegisterActivity::class.java)
        startActivityForResult(intentRegister, this.request_code)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.request_code) {
            if(resultCode == Activity.RESULT_OK){
                var email = data?.getStringExtra("user_email")
                this.txt_email.setText(email.toString())
            }
        }
    }

}
