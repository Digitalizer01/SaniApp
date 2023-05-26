package com.example.saniapp.user

import android.util.Log
import com.example.saniapp.residence.Residence
import com.example.saniapp.resident.Resident
import com.example.saniapp.resident.ResidentAlert
import com.example.saniapp.resident.ResidentMedication
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

sealed interface StaffFunctions

suspend fun findResidenceByIdStaff(idStaff: String): Residence? {
    var residence: Residence? = Residence(
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
    var found: Boolean = false;

    try {
        FirebaseDatabase.getInstance().reference.child("Residences").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var residences = it.value as HashMap<String, HashMap<String, String>>;
            val itr = residences?.iterator();
            while (itr?.hasNext() == true && !found) {
                var itr2 = itr.next();
                var residencekey = itr2.key;
                try {
                    var staffmap = itr2!!.value["Staff"] as HashMap<String, String>;
                    if (staffmap.containsKey(idStaff)) {
                        var residencedata = itr2.value["Data"] as HashMap<String, String>;
                        residence = Residence(
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
                        found = true;
                    }
                } catch (e: Exception) {

                }
            }
        }.await()
    } catch (e: Exception) {

    }
    return residence;
}

fun deleteStaffByIdResidenceAndIdStaff(idResidence: String, idStaff: String) {
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Staff/" + idStaff)
        .removeValue();

    Firebase.auth.currentUser?.delete();
    Firebase.auth.signOut();
}

suspend fun findStaffById(idStaff: String): Staff? {
    var staff: Staff? = Staff(
        null,
        null,
        null,
        null,
        null,
        null,
        null
    );
    var found: Boolean = false;




    try {
        FirebaseDatabase.getInstance().reference.child("Residences").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var residences = it.value as HashMap<String, HashMap<String, String>>;
            val itr = residences?.iterator();
            while (itr?.hasNext() == true && !found) {
                var itr2 = itr.next();
                var residencekey = itr2.key;
                try {
                    var staffmap = itr2!!.value["Staff"] as HashMap<String, String>;
                    if (staffmap.containsKey(idStaff)) {
                        var staffdata = staffmap.get(idStaff) as HashMap<String, String>;
                        var staffdatainfo = staffdata.get("Data") as HashMap<String, String>;
                        staff = Staff(
                            staffdatainfo?.get("Birthdate"),
                            staffdatainfo?.get("Email"),
                            staffdatainfo?.get("Gender"),
                            staffdatainfo?.get("Name"),
                            staffdatainfo?.get("Phone"),
                            staffdatainfo?.get("Surnames"),
                            idStaff
                        )
                        found = true;
                    }
                } catch (e: Exception) {

                }
            }
        }.await()
    } catch (e: Exception) {

    }
    return staff;
}

private fun checkAlert(medication: HashMap<String, HashMap<String, HashMap<String, String>>>): Boolean {
    var alert: Boolean = false;

    val date: Date = Date()
    val cal = Calendar.getInstance()
    cal.time = date
    val weekdayformat = SimpleDateFormat("EEEE")
    val currentweekday: String = weekdayformat.format(Date())

    val hours = cal.get(Calendar.HOUR_OF_DAY);
    val minutes = cal.get(Calendar.MINUTE);

    // Night: 00:00 - 6:59
    // Morning: 7:00 - 11:59
    // Afternoon: 12:00 - 17:59
    // Evening: 18:00 - 23:59

    var currentdaypart = "";
    if (hours >= 0 && hours <= 6) {
        currentdaypart = "Night";
    } else if (hours >= 7 && hours <= 11) {
        currentdaypart = "Morning";
    } else if (hours >= 12 && hours <= 17) {
        currentdaypart = "Afternoon";
    } else if (hours >= 18 && hours <= 23) {
        currentdaypart = "Evening";
    }

    var weekdays =
        arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    var dayparts = arrayOf("Morning", "Afternoon", "Evening", "Night");

    for (weekday in weekdays) {
        for (daypart in dayparts) {
            var medicationHour = "";
            var medicationTaken = "";
            if (!medication.get(weekday)?.get(daypart).isNullOrEmpty()) {
                // Check if it is before current week day
                if (weekdays.indexOf(weekday) < weekdays.indexOf(currentweekday)) {
                    // Only need to find one taken == no
                    if (medication.get(weekday)?.get(daypart)?.get("Medication")
                            .toString() != "---"
                    ) {
                        if (medication.get(weekday)?.get(daypart)?.get("Taken")
                                .toString() == "No"
                        ) {
                            alert = true;
                        }
                    }
                } else {
                    // Check if it is current week day
                    if (weekdays.indexOf(weekday) == weekdays.indexOf(currentweekday)) {
                        // Check if is before current day part
                        if (dayparts.indexOf(daypart) < dayparts.indexOf(currentdaypart)) {
                            // Only need to find one taken == no
                            if (medication.get(weekday)?.get(daypart)?.get("Medication")
                                    .toString() != "---"
                            ) {
                                if (medication.get(weekday)?.get(daypart)?.get("Taken")
                                        .toString() == "No"
                                ) {
                                    alert = true;
                                }
                            }
                        } else {
                            // Check if it is current day part
                            if (dayparts.indexOf(weekday) == dayparts.indexOf(currentdaypart)) {
                                if (medication.get(weekday)?.get(daypart)?.get("Medication")
                                        .toString() != "---"
                                ) {
                                    medicationHour =
                                        medication.get(weekday)?.get(daypart)?.get("Hour")
                                            .toString();
                                    medicationTaken =
                                        medication.get(weekday)?.get(daypart)?.get("Taken")
                                            .toString();

                                    val hoursplit = medicationHour.split(":");

                                    if (medicationTaken == "No") {
                                        // Checking hour
                                        if (Integer.parseInt(hoursplit[0]) < hours) { // Hour
                                            alert = true;
                                        } else {
                                            if (Integer.parseInt(hoursplit[0]) == hours) {
                                                if (Integer.parseInt(hoursplit[1]) < minutes) {
                                                    alert = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    return alert;
}

private fun checkAlertWeekDay(
    residentalert: ResidentAlert,
    medication: HashMap<String, HashMap<String, HashMap<String, String>>>
): ResidentAlert {
    val date: Date = Date()
    val cal = Calendar.getInstance()
    cal.time = date
    val weekdayformat = SimpleDateFormat("EEEE")
    val currentweekday: String = weekdayformat.format(Date())

    val hours = cal.get(Calendar.HOUR_OF_DAY);
    val minutes = cal.get(Calendar.MINUTE);

    // Night: 00:00 - 6:59
    // Morning: 7:00 - 11:59
    // Afternoon: 12:00 - 17:59
    // Evening: 18:00 - 23:59

    var currentdaypart = "";
    if (hours >= 0 && hours <= 6) {
        currentdaypart = "Night";
    } else if (hours >= 7 && hours <= 11) {
        currentdaypart = "Morning";
    } else if (hours >= 12 && hours <= 17) {
        currentdaypart = "Afternoon";
    } else if (hours >= 18 && hours <= 23) {
        currentdaypart = "Evening";
    }

    var weekdays =
        arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    var dayparts = arrayOf("Morning", "Afternoon", "Evening", "Night");

    for (weekday in weekdays) {
        for (daypart in dayparts) {
            var medicationHour = "";
            var medicationTaken = "";
            if (!medication.get(weekday)?.get(daypart).isNullOrEmpty()) {
                // Check if it is before current week day
                if (weekdays.indexOf(weekday) < weekdays.indexOf(currentweekday)) {
                    // Only need to find one taken == no
                    if (medication.get(weekday)?.get(daypart)?.get("Medication")
                            .toString() != "---"
                    ) {
                        if (medication.get(weekday)?.get(daypart)?.get("Taken")
                                .toString() == "No"
                        ) {
                            when (weekday) {
                                "Monday" -> residentalert?.monday = true;
                                "Tuesday" -> residentalert?.tuesday = true;
                                "Wednesday" -> residentalert?.wednesday = true;
                                "Thursday" -> residentalert?.thursday = true;
                                "Friday" -> residentalert?.friday = true;
                                "Saturday" -> residentalert?.saturday = true;
                                "Sunday" -> residentalert?.sunday = true;
                            }
                        }
                    }
                } else {
                    // Check if it is current week day
                    if (weekdays.indexOf(weekday) == weekdays.indexOf(currentweekday)) {
                        // Check if is before current day part
                        if (dayparts.indexOf(daypart) < dayparts.indexOf(currentdaypart)) {
                            // Only need to find one taken == no
                            if (medication.get(weekday)?.get(daypart)?.get("Medication")
                                    .toString() != "---"
                            ) {
                                if (medication.get(weekday)?.get(daypart)?.get("Taken")
                                        .toString() == "No"
                                ) {
                                    when (weekday) {
                                        "Monday" -> residentalert?.monday = true;
                                        "Tuesday" -> residentalert?.tuesday = true;
                                        "Wednesday" -> residentalert?.wednesday = true;
                                        "Thursday" -> residentalert?.thursday = true;
                                        "Friday" -> residentalert?.friday = true;
                                        "Saturday" -> residentalert?.saturday = true;
                                        "Sunday" -> residentalert?.sunday = true;
                                    }
                                }
                            }
                        } else {
                            // Check if it is current day part
                            if (dayparts.indexOf(weekday) == dayparts.indexOf(currentdaypart)) {
                                if (medication.get(weekday)?.get(daypart)?.get("Medication")
                                        .toString() != "---"
                                ) {
                                    medicationHour =
                                        medication.get(weekday)?.get(daypart)?.get("Hour")
                                            .toString();
                                    medicationTaken =
                                        medication.get(weekday)?.get(daypart)?.get("Taken")
                                            .toString();

                                    val hoursplit = medicationHour.split(":");

                                    if (medicationTaken == "No") {
                                        // Checking hour
                                        if (Integer.parseInt(hoursplit[0]) < hours) { // Hour
                                            when (weekday) {
                                                "Monday" -> residentalert?.monday = true;
                                                "Tuesday" -> residentalert?.tuesday = true;
                                                "Wednesday" -> residentalert?.wednesday = true;
                                                "Thursday" -> residentalert?.thursday = true;
                                                "Friday" -> residentalert?.friday = true;
                                                "Saturday" -> residentalert?.saturday = true;
                                                "Sunday" -> residentalert?.sunday = true;
                                            }
                                        } else {
                                            if (Integer.parseInt(hoursplit[0]) == hours) {
                                                if (Integer.parseInt(hoursplit[1]) < minutes) {
                                                    when (weekday) {
                                                        "Monday" -> residentalert?.monday = true;
                                                        "Tuesday" -> residentalert?.tuesday = true;
                                                        "Wednesday" -> residentalert?.wednesday =
                                                            true;
                                                        "Thursday" -> residentalert?.thursday =
                                                            true;
                                                        "Friday" -> residentalert?.friday = true;
                                                        "Saturday" -> residentalert?.saturday =
                                                            true;
                                                        "Sunday" -> residentalert?.sunday = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }



    return residentalert;
}

suspend fun findResidentAllByIdResidence(idResidence: String): ArrayList<Resident>? {
    var residentlist: ArrayList<Resident>? = ArrayList<Resident>();

    try {
        FirebaseDatabase.getInstance().reference.child("Residences")
            .child(idResidence).child("Residents").get()
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
                        var medication: HashMap<String, HashMap<String, HashMap<String, String>>>? =
                            null;
                        try {
                            medication =
                                aux.get("Medication")!! as HashMap<String, HashMap<String, HashMap<String, String>>>;
                        } catch (e: Exception) {


                        }
                        var alert: Boolean = false;
                        if (!medication.isNullOrEmpty()) {
                            alert = checkAlert(medication);
                        }
                        var resident = Resident(
                            data?.get("Birthdate"),
                            data?.get("Gender"),
                            data?.get("Name"),
                            data?.get("Surnames"),
                            residentkey,
                            alert
                        );
                        residentlist?.add(resident);


                    }


                } catch (e: Exception) {
                    residentlist = null;
                }

            }.await()


    } catch (e: Exception) {

    }

    return residentlist;
}

suspend fun findIdResidenceByIdStaff(idStaff: String): String? {
    var idResidence: String? = null;
    var found: Boolean = false;

    try {
        FirebaseDatabase.getInstance().reference.child("Residences").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
            var residences = it.value as HashMap<String, HashMap<String, String>>;
            val itr = residences?.iterator();
            while (itr?.hasNext() == true && !found) {
                var itr2 = itr.next();
                var residencekey = itr2.key;
                var staffmap = itr2.value["Staff"] as HashMap<String, String>;
                if (staffmap.containsKey(idStaff)) {
                    var residencedata = itr2.value["Data"] as HashMap<String, String>;
                    idResidence = residencekey;
                    found = true;
                }
            }

        }.await()


    } catch (e: Exception) {

    }
    return idResidence;
}

suspend fun getResidentAlertByIdResidenceAndIdResident(
    idResidence: String,
    idResident: String
): ResidentAlert {
    var residentalert: ResidentAlert = ResidentAlert(
        null,
        false,
        false,
        false,
        false,
        false,
        false,
        false
    );
    try {
        FirebaseDatabase.getInstance().reference.child("Residences")
            .child(idResidence).child("Residents").child(idResident).get()
            .addOnSuccessListener() {
                Log.i("firebase", "Got value ${it.value}")
                try {
                    var residentInfo = it.value as HashMap<String, HashMap<String, String>>;
                    var residentdata = residentInfo.get("Data") as HashMap<String, String>;
                    var resident: Resident = Resident(
                        residentdata.get("Birthdate").toString(),
                        residentdata.get("Gender").toString(),
                        residentdata.get("Name").toString(),
                        residentdata.get("Surnames").toString(),
                        residentdata.get("IDPillbox").toString(),
                        false
                    );
                    residentalert?.resident = resident;

                    if (!residentInfo.get("Medication").isNullOrEmpty()) {
                        checkAlertWeekDay(
                            residentalert,
                            residentInfo.get("Medication") as HashMap<String, HashMap<String, HashMap<String, String>>>
                        );
                    }


                } catch (e: Exception) {


                }

            }.await()


    } catch (e: Exception) {

    }

    return residentalert;
}

suspend fun findResidentMedicationByDayAndIdResidenceAndIdResident(
    day: String,
    idResidence: String,
    idResident: String
): ResidentMedication {
    var residentmedication: ResidentMedication = ResidentMedication(
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )

    try {
        FirebaseDatabase.getInstance().reference.child("Residences")
            .child(idResidence).child("Residents").child(idResident).child("Medication").child(day)
            .get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                try {
                    var residentMedicationInfo =
                        it.value as HashMap<String, HashMap<String, String>>;

                    residentmedication.day = day;

                    // Morning
                    residentmedication.morning_hour =
                        residentMedicationInfo.get("Morning")?.get("Hour");
                    residentmedication.morning_medication =
                        residentMedicationInfo.get("Morning")?.get("Medication");
                    residentmedication.morning_taken =
                        residentMedicationInfo.get("Morning")?.get("Taken");

                    // Afternoon
                    residentmedication.afternoon_hour =
                        residentMedicationInfo.get("Afternoon")?.get("Hour");
                    residentmedication.afternoon_medication =
                        residentMedicationInfo.get("Afternoon")?.get("Medication");
                    residentmedication.afternoon_taken =
                        residentMedicationInfo.get("Afternoon")?.get("Taken");

                    // Evening
                    residentmedication.evening_hour =
                        residentMedicationInfo.get("Evening")?.get("Hour");
                    residentmedication.evening_medication =
                        residentMedicationInfo.get("Evening")?.get("Medication");
                    residentmedication.evening_taken =
                        residentMedicationInfo.get("Evening")?.get("Taken");

                    // Night
                    residentmedication.night_hour =
                        residentMedicationInfo.get("Night")?.get("Hour");
                    residentmedication.night_medication =
                        residentMedicationInfo.get("Night")?.get("Medication");
                    residentmedication.night_taken =
                        residentMedicationInfo.get("Night")?.get("Taken");


                } catch (e: Exception) {


                }

            }.await()


    } catch (e: Exception) {

    }

    return residentmedication;
}

fun updateResidentMedicationByDayAndIdResidenceAndIdResident(
    day: String,
    idResidence: String,
    idResident: String,
    residentMedication: ResidentMedication
) {

    // Morning
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Morning/Hour")
        .setValue(residentMedication.morning_hour);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Morning/Medication")
        .setValue(residentMedication.morning_medication);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Morning/Taken")
        .setValue(residentMedication.morning_taken);

    // Afternoon
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Afternoon/Hour")
        .setValue(residentMedication.afternoon_hour);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Afternoon/Medication")
        .setValue(residentMedication.afternoon_medication);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Afternoon/Taken")
        .setValue(residentMedication.afternoon_taken);

    // Evening
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Evening/Hour")
        .setValue(residentMedication.evening_hour);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Evening/Medication")
        .setValue(residentMedication.evening_medication);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Evening/Taken")
        .setValue(residentMedication.evening_taken);

    // Night
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Night/Hour")
        .setValue(residentMedication.night_hour);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Night/Medication")
        .setValue(residentMedication.night_medication);
    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Residences/" + idResidence + "/Residents/" + idResident + "/Medication/" + day + "/Night/Taken")
        .setValue(residentMedication.night_taken);

}