package com.example.saniapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
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

        val inputText = testing?.get(1) + "\n" + testing?.get(2);
        val algorithm = "AES/CBC/PKCS5Padding";
        val key = SecretKeySpec("1234567890123456".toByteArray(), "AES");
        val iv = IvParameterSpec(ByteArray(16));

        val cipherText = encrypt(algorithm, inputText, key, iv);
        val plainText = decrypt(algorithm, cipherText, key, iv);

        assert(inputText == plainText);

        print("");

        var file = File(getFilesDir().getAbsolutePath(), "admindata.txt");
        file.writeText(cipherText);

        setContentView(R.layout.activity_admin);
    }
}