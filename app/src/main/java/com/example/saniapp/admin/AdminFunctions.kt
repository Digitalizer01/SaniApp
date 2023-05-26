package com.example.saniapp.admin

import android.util.Log
import com.example.saniapp.residence.Residence
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

sealed interface AdminFunctions

suspend fun findResidenceById(idResidence: String): Residence {
    var residence: Residence = Residence(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );

    FirebaseDatabase.getInstance().reference.child("Residences").get().addOnSuccessListener {
        Log.i("firebase", "Got value ${it.value}")
        var residences = it.value as HashMap<String, HashMap<String, String>>;
        var residenceinfo = residences.get(idResidence) as HashMap<String, HashMap<String, String>>;
        var residencedata = residenceinfo.get("Data")
        residence.city = residencedata?.get("City")
        residence.country = residencedata?.get("Country")
        residence.email = residencedata?.get("Email")
        residence.name = residencedata?.get("Name")
        residence.phone = residencedata?.get("Phone")
        residence.province = residencedata?.get("Province")
        residence.street = residencedata?.get("Street")
        residence.timetable = residencedata?.get("Timetable")
        residence.zc = residencedata?.get("ZC")
        residence.id = idResidence
    }.await()
    return residence;
}

suspend fun findResidenceAll(): ArrayList<Residence>? {
    var residencelist: ArrayList<Residence>? = ArrayList<Residence>();

    FirebaseDatabase.getInstance().reference.child("Residences").get().addOnSuccessListener {
        Log.i("firebase", "Got value ${it.value}")
        try {
            var residences = it.value as HashMap<String, HashMap<String, String>>;
            val itr = residences?.iterator();
            while (itr?.hasNext() == true) {
                var itr2 = itr.next();
                var residencekey = itr2.key;
                var residencedata = itr2.value["Data"] as HashMap<String, String>;
                var residence = Residence(
                    residencedata["City"],
                    residencedata["Country"],
                    residencedata["Email"],
                    residencedata["Name"],
                    residencedata["Phone"],
                    residencedata["Province"],
                    residencedata["Street"],
                    residencedata["Timetable"],
                    residencedata["ZC"],
                    residencekey
                )
                residencelist?.add(residence);


            }
        } catch (e: Exception) {

        }
    }.await()

    return residencelist;
}

suspend fun createResidence(
    residence: Residence,
    password: String,
    adminEmail: String,
    adminPass: String
): Boolean? {
    var success = false;
    FirebaseAuth.getInstance()
        .createUserWithEmailAndPassword(
            residence.email.toString(),
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                success = true;

                Thread.sleep(1000);
                val user = FirebaseAuth.getInstance().currentUser

                // Name
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Name")
                    .setValue(residence.name.toString())
                // City
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/City")
                    .setValue(residence.city.toString())
                // Province
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Province")
                    .setValue(residence.province.toString())
                // Country
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Country")
                    .setValue(residence.country.toString())
                // Street
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Street")
                    .setValue(residence.street.toString())
                // ZC
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/ZC")
                    .setValue(residence.zc.toString())
                // Email
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Email")
                    .setValue(residence.email.toString())
                // Phone
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Phone")
                    .setValue(residence.phone.toString())
                // Timetable
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + user?.uid.toString() + "/Data/Timetable")
                    .setValue(residence.timetable.toString())

                Firebase.auth.signOut();

                val adminCredential = EmailAuthProvider
                    .getCredential(adminEmail, adminPass);

                var adminUser = Firebase.auth.signInWithCredential(adminCredential);
                Thread.sleep(1000);
            }
        }.await();

    return success;
}

fun updateResidenceByResidence(residence: Residence) {
    // Name
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Name")
        .setValue(residence.name);

    // City
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/City")
        .setValue(residence.city);

    // Province
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Province")
        .setValue(residence.province);

    // Country
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Country")
        .setValue(residence.country);

    // Street
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Street")
        .setValue(residence.street);

    // ZC
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/ZC")
        .setValue(residence.zc);

    // Phone
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Phone")
        .setValue(residence.phone);

    // Timetable
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + residence.id + "/Data/Timetable")
        .setValue(residence.timetable);
}
