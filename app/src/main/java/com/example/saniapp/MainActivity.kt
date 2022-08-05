package com.example.saniapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<TextView>(R.id.main_button_login)
        val emailText = findViewById<TextView>(R.id.text_main_email)
        val passText = findViewById<TextView>(R.id.text_main_password)
        loginButton.setOnClickListener {

            try {
                if (emailText.text.isNotEmpty() && passText.text.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            emailText.text.toString(),
                            passText.text.toString()
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                val toast = Toast.makeText(this, "Logeado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()

                                val i = Intent(this, AdminActivity::class.java).apply {
                                    putExtra("Data", "DatoNuevo")
                                }

                                startActivity(i)
                            } else {
                                val toast = Toast.makeText(this, "No logeado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()
                            }
                        }
                }
            } catch (e: Exception) {
                val toast = Toast.makeText(
                    this,
                    "Se ha producido un error. Inténtelo de nuevo.",
                    Toast.LENGTH_SHORT
                )
                toast.setMargin(50f, 50f)
                toast.show()
            }

        }
    }
}