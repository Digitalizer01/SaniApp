package com.example.saniapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    // 0: admin, 1: residence, 2: staff
    fun checkID(user: String): Int {
        var option: Int = -1;

        // Check if it is admin
        var info = Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Admin/").addValueEventListener(object : ValueEventListener {  override fun onDataChange(dataSnapshot: DataSnapshot) {
                val snapshotIterator = dataSnapshot.children;
                val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    var hashmap = iterator.next();
                    var idadmin = hashmap.value;

                    if(idadmin == user){
                        option = 1;
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })

        if (option != -1){
            return option;
        }

        // Check if it is a residence
        info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Residences/"
                ).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            var idresidence = hashmap.key;

                            if(idresidence == user){
                                option = 2;
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })

        if (option != -1){
            return option;
        }

        // Check if it is a residence staff
       info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Residences/"
                ).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            var residence = hashmap.value as HashMap<String, HashMap<String, HashMap<String, String>>>;
                            var idresidence = residence["Staff"]?.containsKey(user);
                            if(idresidence == true){
                                option = 2;
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })

        return option;
    }

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
                                val user = FirebaseAuth.getInstance().currentUser

                                var toast = Toast.makeText(this, "Logeado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()

                                var option = checkID(user?.uid.toString());
                                var i: Intent? = null;
                                when(option){
                                    0->{
                                        // Admin
                                        i = Intent(this, AdminActivity::class.java).apply {
                                            putExtra("Data", "DatoNuevo")
                                        }
                                    }
                                    1->{
                                        // Residence
                                        i = Intent(this, ResidenceActivity::class.java).apply {
                                            putExtra("Data", "DatoNuevo")
                                        }
                                    }
                                    2->{
                                        // Residence staff
                                        i = Intent(this, UserActivity::class.java).apply {
                                            putExtra("Data", "DatoNuevo")
                                        }
                                    }
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