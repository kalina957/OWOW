package com.example.kalina.softuni_intents

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import okhttp3.*


class TestMain {

    var client : OkHttpClient = OkHttpClient()

    val JSON = MediaType.parse("application/json; charset=utf-8")

    // test data
    fun doPostRequest(url: String,json: String): String{
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()
        val response = client.newCall(request).execute()
        return response.body()!!.string()
    }
}
