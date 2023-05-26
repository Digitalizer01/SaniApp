package com.example.saniapp.residence

import android.util.Log
import com.example.saniapp.resident.Resident
import com.example.saniapp.user.Staff
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

sealed interface ResidenceFunctions

fun deleteResidenceById(idResidence: String) {
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence)
        .removeValue()

    Firebase.auth.currentUser?.delete();
    Firebase.auth.signOut();
}

// Staff

suspend fun findStaffById(idStaff: String): Staff? {
    var staff: Staff? = Staff(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    );
    FirebaseDatabase.getInstance().reference.child("Residences")
        .child(Firebase.auth.currentUser?.uid.toString()).child("Staff").child(idStaff)
        .child("Data").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var staffdata = it.value as HashMap<String, String>;
            staff?.birthdate = staffdata.get("Birthdate");
            staff?.email = staffdata.get("Email");
            staff?.gender = staffdata.get("Gender");
            staff?.name = staffdata.get("Name");
            staff?.phone = staffdata.get("Phone");
            staff?.surnames = staffdata.get("Surnames");
            staff?.id = idStaff;
            
            
            
            
        }.await()
    
    
    
    
    return staff;
}

suspend fun findStaffAllByIdResidence(idResidence: String): ArrayList<Staff>? {
    var stafflist: ArrayList<Staff>? = ArrayList<Staff>();
    
    
    
    
    try {
        FirebaseDatabase.getInstance().reference.child("Residences").child(idResidence)
            .child("Staff").get().addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                try {
                    var residenceStaff = it.value as HashMap<String, HashMap<String, String>>;
                    val itrAllStaff = residenceStaff?.iterator();
                    while (itrAllStaff?.hasNext() == true) {
                        var itrStaff = itrAllStaff.next();
                        var aux = itrStaff.value as HashMap<String, HashMap<String, String>>;
                        var staffkey = itrStaff.key
                        var data = aux.get("Data")
                        var staff = Staff(
                            data?.get("Birthdate"),
                            data?.get("Email"),
                            data?.get("Gender"),
                            data?.get("Name"),
                            data?.get("Phone"),
                            data?.get("Surnames"),
                            staffkey
                        )
                        stafflist?.add(staff);
                        
                        
                        
                        
                        

                    }
                    
                    
                    
                    
                    
                } catch (e: Exception) {
                    stafflist = null;
                }

            }.await()
        
        
        
        
    } catch (e: Exception) {
        
        
        
        
        
        
        
        
        
    }

    return stafflist;
}

suspend fun createStaff(staff: Staff, password: String, idResidence: String, residenceEmail: String, residencePass: String): Boolean {
    var success = false;
    FirebaseAuth.getInstance()
        .createUserWithEmailAndPassword(
            staff.email.toString(),
            password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                success = true;

                Thread.sleep(1000);
                val user = FirebaseAuth.getInstance().currentUser

                // Name
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Name")
                    .setValue(staff.name)
                // Surnames
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Surnames")
                    .setValue(staff.surnames)
                // Gender
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Gender")
                    .setValue(staff.gender)
                // Birthdate
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Birthdate")
                    .setValue(staff.birthdate)
                // Email
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Email")
                    .setValue(staff.email)
                // Phone
                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Residences/" + idResidence + "/Staff/" + user?.uid.toString() + "/Data/Phone")
                    .setValue(staff.phone)

                Firebase.auth.signOut();

                val residenceCredential = EmailAuthProvider
                    .getCredential(residenceEmail, residencePass);

                var residenceUser = Firebase.auth.signInWithCredential(residenceCredential);
                Thread.sleep(1000);
            }
        }.await();

    return success;
}

fun updateStaff(staff: Staff) {
    // Name
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Name")
        .setValue(staff.name);

    // Surnames
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Surnames")
        .setValue(staff.surnames);

    // Gender
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Gender")
        .setValue(staff.gender);

    // Birthdate
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Birthdate")
        .setValue(staff.birthdate);

    // Phone
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Phone")
        .setValue(staff.phone);

    // Email
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Staff/" + staff.id + "/Data/Email")
        .setValue(staff.email);
}

// Resident

suspend fun findResidentAll(): ArrayList<Resident>? {
    var residentlist: ArrayList<Resident>? = ArrayList<Resident>();
    
    
    
    
    try {
        FirebaseDatabase.getInstance().reference.child("Residences")
            .child(Firebase.auth.currentUser?.uid.toString()).child("Residents").get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                try {
                    var residenceResident = it.value as HashMap<String, HashMap<String, String>>;
                    val itrAllResident = residenceResident?.iterator();
                    while (itrAllResident?.hasNext() == true) {
                        var itrResident = itrAllResident.next();
                        var aux = itrResident.value as HashMap<String, HashMap<String, String>>;
                        var residentkey = itrResident.key
                        var data = aux.get("Data")
                        var resident = Resident(
                            data?.get("Birthdate"),
                            data?.get("Gender"),
                            data?.get("Name"),
                            data?.get("Surnames"),
                            residentkey,
                            null
                        );
                        residentlist?.add(resident);
                        
                        
                        
                        
                        

                    }
                    
                    
                    
                    
                    
                }
                catch(e: Exception){
                    residentlist = null;
                }

            }.await()
        
        
        
        
    } catch (e: Exception) {
        
    }

    return residentlist;
}

suspend fun findResidentById(idResident: String): Resident? {
    var resident: Resident? = Resident(
        null,
        null,
        null,
        null,
        null,
        null
    );
    FirebaseDatabase.getInstance().reference.child("Residences")
        .child(Firebase.auth.currentUser?.uid.toString()).child("Residents").child(idResident)
        .child("Data").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var residentdata = it.value as HashMap<String, String>;
            resident?.birthdate = residentdata.get("Birthdate");
            resident?.gender = residentdata.get("Gender");
            resident?.name = residentdata.get("Name");
            resident?.surnames = residentdata.get("Surnames");
            resident?.id = residentdata.get("IDPillbox");
        }.await()
    
    
    
    
    return resident;
}

fun createResident(resident: Resident) {
    // Name
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Name")
        .setValue(resident.name)
    // Surnames
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Surnames")
        .setValue(resident.surnames)
    // Gender
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Gender")
        .setValue(resident.gender)
    // Birthdate
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Birthdate")
        .setValue(resident.birthdate)
    // ID
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/IDPillbox")
        .setValue(resident.id)
}

fun updateResident(resident: Resident) {
    // Name
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Name")
        .setValue(resident.name);

    // Surnames
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Surnames")
        .setValue(resident.surnames);

    // Gender
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Gender")
        .setValue(resident.gender);

    // Birthdate
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/Birthdate")
        .setValue(resident.birthdate);

    // ID
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + resident.id + "/Data/IDPillbox")
        .setValue(resident.id);
}

fun deleteResidentById(idResident: String) {
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + Firebase.auth.currentUser?.uid.toString() + "/Residents/" + idResident)
        .removeValue();
}

suspend fun getRandomString(length: Int): String {
    val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9');

    val date: Date = Date()
    val cal = Calendar.getInstance()
    cal.time = date

    val hours = cal.get(Calendar.HOUR_OF_DAY).toString();
    val minutes = cal.get(Calendar.MINUTE).toString();
    val seconds = cal.get(Calendar.SECOND).toString();
    val milliseconds = cal.get(Calendar.MILLISECOND).toString();

    Thread.sleep(100);
    var randomString = hours + minutes + seconds + milliseconds + List(length) { charset.random() } .joinToString("");

    FirebaseDatabase.getInstance().reference.child("Residences")
        .child(Firebase.auth.currentUser?.uid.toString()).child("Residents").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            try {
                var residenceResident = it.value as HashMap<String, HashMap<String, String>>;
                val itrAllResident = residenceResident?.iterator();
                while (itrAllResident?.hasNext() == true) {
                    var itrStaff = itrAllResident.next();
                    var aux = itrStaff.value as HashMap<String, HashMap<String, String>>;
                    var staffkey = itrStaff.key
                    Log.i("firebase", "Got value ${itrStaff.key}")
                    Log.i("randomString", "$randomString")
                    while(itrStaff.key == randomString){
                        Thread.sleep(100);
                        randomString = hours + minutes + seconds + milliseconds + List(length) { charset.random() }.joinToString("");
                        Log.i("randomString", "Changed value: $randomString")
                    }
                }
            }
            catch(e: Exception){

            }
        }.await()

    return randomString;
}