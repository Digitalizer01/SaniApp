package com.example.saniapp.user

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserResidentMedication.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserResidentMedication : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_resident_medication, container, false)
    }

    fun showInfo(residenceid: String, weekday: String, idresident: String, layout: LinearLayout, nal: NavController) {
        var mapUser: Map<String, String>? = null;

        var info = Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val snapshotIterator = dataSnapshot.children;
                val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();

                var morning_medication: String = "";
                var morning_hour: String = "";
                var morning_taken: String = "";

                var afternoon_medication: String = "";
                var afternoon_hour: String = "";
                var afternoon_taken: String = "";

                var evening_medication: String = "";
                var evening_hour: String = "";
                var evening_taken: String = "";

                while (iterator.hasNext()) {
                    var hashmap = iterator.next();
                    val info: HashMap<String, String> = hashmap.value as HashMap<String, String>;
                    when(hashmap.key)
                    {
                        "Morning" -> {
                            morning_medication = info["Medication"].toString();
                            morning_hour = info["Hour"].toString();
                            morning_taken = info["Taken"].toString();
                        }
                        "Afternoon" -> {
                            afternoon_medication = info["Medication"].toString();
                            afternoon_hour = info["Hour"].toString();
                            afternoon_taken = info["Taken"].toString();
                        }
                        "Evening" -> {
                            evening_medication = info["Medication"].toString();
                            evening_hour = info["Hour"].toString();
                            evening_taken = info["Taken"].toString();
                        }
                    }
                }

                var edittext_resident_medication_day = view?.findViewById(R.id.text_medication_resident_weekday) as TextView;

                when(weekday){
                    "Monday" -> edittext_resident_medication_day.setText("Lunes");
                    "Tuesday" -> edittext_resident_medication_day.setText("Martes");
                    "Wednesday" -> edittext_resident_medication_day.setText("Miércoles");
                    "Thursday" -> edittext_resident_medication_day.setText("Jueves");
                    "Friday" -> edittext_resident_medication_day.setText("Viernes");
                    "Saturday" -> edittext_resident_medication_day.setText("Sábado");
                    "Sunday" -> edittext_resident_medication_day.setText("Domingo");
                }

                var edittext_morning_medication = view?.findViewById(R.id.edittext_medication_resident_morning_medication) as TextView;
                var edittext_morning_hour = view?.findViewById(R.id.edittext_medication_resident_morning_hour) as TextView;
                var switch_morning_taken = view?.findViewById(R.id.switch_medication_resident_morning_taken) as Switch;

                var edittext_afternoon_medication = view?.findViewById(R.id.edittext_medication_resident_afternoon_medication) as TextView;
                var edittext_afternoon_hour = view?.findViewById(R.id.edittext_medication_resident_afternoon_hour) as TextView;
                var switch_afternoon_taken = view?.findViewById(R.id.switch_medication_resident_afternoon_taken) as Switch;

                var edittext_evening_medication = view?.findViewById(R.id.edittext_medication_resident_evening_medication) as TextView;
                var edittext_evening_hour = view?.findViewById(R.id.edittext_medication_resident_evening_hour) as TextView;
                var switch_evening_taken = view?.findViewById(R.id.switch_medication_resident_evening_taken) as Switch;

                edittext_morning_medication.setText(morning_medication);
                edittext_morning_hour.setText(morning_hour);

                edittext_afternoon_medication.setText(afternoon_medication);
                edittext_afternoon_hour.setText(afternoon_hour);

                edittext_evening_medication.setText(evening_medication);
                edittext_evening_hour.setText(evening_hour);

                if(morning_taken == "Yes") switch_morning_taken.isChecked = true else switch_morning_taken.isChecked = false;
                if(afternoon_taken == "Yes") switch_afternoon_taken.isChecked = true else switch_afternoon_taken.isChecked = false;
                if(evening_taken == "Yes") switch_evening_taken.isChecked = true else switch_evening_taken.isChecked = false;

                var btn_update = view?.findViewById(R.id.button_medication_resident_update) as Button;

                btn_update.setOnClickListener{

                    var switch_morning_taken_value = "";
                    var switch_afternoon_taken_value = "";
                    var switch_evening_taken_value = "";

                    if(switch_morning_taken.isChecked == true) switch_morning_taken_value = "Yes" else switch_morning_taken_value = "No";
                    if(switch_afternoon_taken.isChecked == true) switch_afternoon_taken_value = "Yes" else switch_afternoon_taken_value = "No";
                    if(switch_evening_taken.isChecked == true) switch_evening_taken_value = "Yes" else switch_evening_taken_value = "No";

                    var split_morning_hour = edittext_morning_hour.text.split(":");
                    var split_afternoon_hour = edittext_afternoon_hour.text.split(":");
                    var split_evening_hour = edittext_evening_hour.text.split(":");

                    // Morning: 8:00 - 13:59
                    // Afternoon: 14:00 - 19:59
                    // Evening: 20:00 - 7:59

                    if ((split_morning_hour[0].toInt() >= 8 && split_morning_hour[0].toInt() <= 13) && (split_afternoon_hour[0].toInt() >= 14 && split_afternoon_hour[0].toInt() <= 19) && (split_evening_hour[0].toInt() >= 20 && split_evening_hour[0].toInt() <= 23) || (split_evening_hour[0].toInt() >= 0 && split_evening_hour[0].toInt() <= 7)) {

                        // Morning
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Morning/Hour")
                            .setValue(edittext_morning_hour.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Morning/Medication")
                            .setValue(edittext_morning_medication.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Morning/Taken")
                            .setValue(switch_morning_taken_value);

                        // Afternoon
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Afternoon/Hour")
                            .setValue(edittext_afternoon_hour.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Afternoon/Medication")
                            .setValue(edittext_afternoon_medication.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Afternoon/Taken")
                            .setValue(switch_afternoon_taken_value);

                        // Evening
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Evening/Hour")
                            .setValue(edittext_evening_hour.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Evening/Medication")
                            .setValue(edittext_evening_medication.text.toString());
                        Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday+"/Evening/Taken")
                            .setValue(switch_evening_taken_value);

                        print(edittext_morning_hour.text.toString());
                        print(edittext_morning_medication.text.toString());
                        print(switch_morning_taken_value);

                        print(edittext_afternoon_hour.text.toString());
                        print(edittext_afternoon_medication.text.toString());
                        print(switch_afternoon_taken_value);

                        print(edittext_evening_hour.text.toString());
                        print(edittext_evening_medication.text.toString());
                        print(switch_evening_taken_value);

                        val toast = Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                        toast.show();

                        print("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday + " UPDATED");
                    }
                    else{
                        val toast = Toast.makeText(context, "Las horas no son correctas", Toast.LENGTH_SHORT);
                        toast.show();

                        print("Residences/"+residenceid+"/Residents/"+idresident+"/Medication/"+weekday + " ERROR");
                    }

                }

            }


            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_resident_medication) as LinearLayout
        val user = FirebaseAuth.getInstance().currentUser
        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residenceid: String = inputData[0] as String;
        var weekday: String = inputData[1] as String;
        var residentmap: HashMap<String, HashMap<String, String>> = inputData[2] as HashMap<String, HashMap<String, String>>;

        var idresident: String = residentmap["Data"]?.get("IDPillbox").toString();

        showInfo(residenceid, weekday, idresident, layout, nal);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserResidentMedication.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserResidentMedication().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}