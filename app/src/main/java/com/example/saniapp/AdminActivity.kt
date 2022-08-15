package com.example.saniapp

import android.media.tv.TvContract.Programs.Genres.encode
import android.net.Uri.encode
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64.encode
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.Base64Utils.encode
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder.encode
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.Security
import java.util.*
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AdminActivity : AppCompatActivity() {

    fun decrypt(algorithm: String, cipherText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
        return String(plainText)
    }

    fun encrypt(algorithm: String, inputText: String, key: SecretKeySpec, iv: IvParameterSpec): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.ENCRYPT_MODE, key, iv)
        val cipherText = cipher.doFinal(inputText.toByteArray())
        return Base64.getEncoder().encodeToString(cipherText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testing: ArrayList<String>? = this.intent.getStringArrayListExtra("userinfo");

        val inputText = "abcdefghigklmnopqrstuvwxyz0123456789"
        val algorithm = "AES/CBC/PKCS5Padding"
        val key = SecretKeySpec(testing?.get(0)!!.toByteArray(), "AES")
        val iv = IvParameterSpec(ByteArray(16))

        val cipherText = encrypt(algorithm, inputText, key, iv)
        val plainText = decrypt(algorithm, cipherText, key, iv)

        try {
            val myObj = File("userdata.txt")
            myObj.appendText("");
            if (myObj.createNewFile()) {
                println("File created: " + myObj.name)
            } else {
                println("File already exists.")
            }
        } catch (e: IOException) {
            println("An error occurred.")
            e.printStackTrace()
        }

        setContentView(R.layout.activity_admin);
    }
}