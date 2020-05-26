package com.example.kalina.softuni_intents

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {


    private var CAMERA_REQUEST_CODE: Int = 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initListeners()
    }

    private fun initListeners() {
        this.btn_register.setOnClickListener {
            val data = Intent()
            data.putExtra("user_email", this.txt_email.text.toString())
            setResult(Activity.RESULT_OK, data)
            finish()
        }
        this.btn_contact.setOnClickListener {
            dialNumber()
        }
        this.btn_take_picture.setOnClickListener {
            startCamera()
        }
    }

    private fun startCamera() {
        val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(callCameraIntent.resolveActivity(packageManager)!=null){
            startActivityForResult(callCameraIntent,CAMERA_REQUEST_CODE)
        }
    }

    private fun setResult() {

    }

    private fun dialNumber() {
        val intentDial = Intent().setAction(Intent.ACTION_DIAL)
        intentDial.setData(Uri.parse("tel:0687659758"))
        startActivity(intentDial)
    }

    private fun startEmail(data: String) {
        val sendIntent = Intent()
        sendIntent.setAction(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, data)
        sendIntent.setType("text/plain")

        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                this.img_user.setImageBitmap(data.extras?.get("data") as Bitmap)
            }
        }
    }
}