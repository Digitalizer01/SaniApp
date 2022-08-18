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
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    fun enterAdmin(userid: String, intent: Intent){
        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Admin/"
                ).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var key: String = "";
                        var hashmap = iterator.next();
                        key = hashmap.value.toString();

                        if(key == userid){
                            startActivity(intent)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
    }

    fun enterResidence(userid: String, intent: Intent){
        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Residences/"
                ).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var residences: HashMap<String, HashMap<String, String>> =
                            HashMap<String, HashMap<String, String>>();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            residences = hashmap.value as HashMap<String, HashMap<String, String>>;

                            val key = hashmap.key.toString();
                            if(userid == key){
                                startActivity(intent);
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
    }

    fun enterStaff(userid: String, intent: Intent){
        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(
                    "Residences/"
                ).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var residence: HashMap<String, HashMap<String, String>> = HashMap<String, HashMap<String, String>>();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            residence = hashmap.value as HashMap<String, HashMap<String, String>>;
                            if(residence["Staff"] != null){
                                var residence_staff = residence["Staff"]!! as HashMap<String, String>;
                                if(residence_staff.containsKey(userid)){
                                    startActivity(intent);
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
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

                                var mapUser: Map<String, String>? = null;
                                var userinfo: ArrayList<String> = ArrayList()
                                userinfo.add(user?.uid.toString());
                                userinfo.add(emailText.text.toString());
                                userinfo.add(passText.text.toString());
                                enterAdmin(user?.uid.toString(), Intent(this, AdminActivity::class.java).putExtra("userinfo", userinfo));
                                enterResidence(user?.uid.toString(), Intent(this, ResidenceActivity::class.java).putExtra("userinfo", userinfo));
                                enterStaff(user?.uid.toString(), Intent(this, UserActivity::class.java));

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