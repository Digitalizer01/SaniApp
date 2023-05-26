package com.example.saniapp.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.resident.ResidentAlert
import com.example.saniapp.resident.ResidentMedication
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private fun showResidentMedication(residenceid: String, weekday: String, residentalert: ResidentAlert) {
        GlobalScope.launch(Dispatchers.IO) {
            var residentalertinfo = findResidentMedicationByDayAndIdResidenceAndIdResident(weekday, residenceid, residentalert.resident?.id.toString());
            var aux2 = residentalert;
            withContext(Dispatchers.Main) {
                var aux3 = aux2;
                (aux3);
                var text_medication_resident_weekday = view?.findViewById(R.id.text_medication_resident_weekday) as TextView;

                var edittext_morning_medication = view?.findViewById(R.id.edittext_medication_resident_morning_medication) as TextView;
                var edittext_morning_hour = view?.findViewById(R.id.edittext_medication_resident_morning_hour) as TextView;
                var switch_morning_taken = view?.findViewById(R.id.switch_medication_resident_morning_taken) as Switch;
                var switch_morning_activate = view?.findViewById(R.id.switch_medication_resident_morning_activate) as Switch;

                var edittext_afternoon_medication = view?.findViewById(R.id.edittext_medication_resident_afternoon_medication) as TextView;
                var edittext_afternoon_hour = view?.findViewById(R.id.edittext_medication_resident_afternoon_hour) as TextView;
                var switch_afternoon_taken = view?.findViewById(R.id.switch_medication_resident_afternoon_taken) as Switch;
                var switch_afternoon_activate = view?.findViewById(R.id.switch_medication_resident_afternoon_activate) as Switch;

                var edittext_evening_medication = view?.findViewById(R.id.edittext_medication_resident_evening_medication) as TextView;
                var edittext_evening_hour = view?.findViewById(R.id.edittext_medication_resident_evening_hour) as TextView;
                var switch_evening_taken = view?.findViewById(R.id.switch_medication_resident_evening_taken) as Switch;
                var switch_evening_activate = view?.findViewById(R.id.switch_medication_resident_evening_activate) as Switch;

                var edittext_night_medication = view?.findViewById(R.id.edittext_medication_resident_night_medication) as TextView;
                var edittext_night_hour = view?.findViewById(R.id.edittext_medication_resident_night_hour) as TextView;
                var switch_night_taken = view?.findViewById(R.id.switch_medication_resident_night_taken) as Switch;
                var switch_night_activate = view?.findViewById(R.id.switch_medication_resident_night_activate) as Switch;

                when (weekday) {
                    "Monday" -> text_medication_resident_weekday.setText("Lunes");
                    "Tuesday" -> text_medication_resident_weekday.setText("Martes");
                    "Wednesday" -> text_medication_resident_weekday.setText("Miércoles");
                    "Thursday" -> text_medication_resident_weekday.setText("Jueves");
                    "Friday" -> text_medication_resident_weekday.setText("Viernes");
                    "Saturday" -> text_medication_resident_weekday.setText("Sábado");
                    "Sunday" -> text_medication_resident_weekday.setText("Domingo");
                }


                edittext_morning_medication.setText(residentalertinfo.morning_medication.toString());
                edittext_morning_hour.setText(residentalertinfo.morning_hour.toString());

                edittext_afternoon_medication.setText(residentalertinfo.afternoon_medication.toString());
                edittext_afternoon_hour.setText(residentalertinfo.afternoon_hour.toString());

                edittext_evening_medication.setText(residentalertinfo.evening_medication.toString());
                edittext_evening_hour.setText(residentalertinfo.evening_hour.toString());

                edittext_night_medication.setText(residentalertinfo.night_medication.toString());
                edittext_night_hour.setText(residentalertinfo.night_hour.toString());

                if(residentalertinfo.morning_taken.toString() == "Yes") switch_morning_taken.isChecked = true else switch_morning_taken.isChecked = false;
                if(residentalertinfo.afternoon_taken.toString() == "Yes") switch_afternoon_taken.isChecked = true else switch_afternoon_taken.isChecked = false;
                if(residentalertinfo.evening_taken.toString() == "Yes") switch_evening_taken.isChecked = true else switch_evening_taken.isChecked = false;
                if(residentalertinfo.night_taken.toString() == "Yes") switch_night_taken.isChecked = true else switch_night_taken.isChecked = false;

                if(residentalertinfo.morning_medication.toString() != "---") switch_morning_activate.isChecked = true else switch_morning_activate.isChecked = false;
                if(residentalertinfo.afternoon_medication.toString() != "---") switch_afternoon_activate.isChecked = true else switch_afternoon_activate.isChecked = false;
                if(residentalertinfo.evening_medication.toString() != "---") switch_evening_activate.isChecked = true else switch_evening_activate.isChecked = false;
                if(residentalertinfo.night_medication.toString() != "---") switch_night_activate.isChecked = true else switch_night_activate.isChecked = false;

                if(switch_morning_activate.isChecked == false){
                    edittext_morning_medication.visibility = View.INVISIBLE;
                    edittext_morning_hour.visibility = View.INVISIBLE;
                    switch_morning_taken.visibility = View.INVISIBLE;
                };
                if(switch_afternoon_activate.isChecked == false){
                    edittext_afternoon_medication.visibility = View.INVISIBLE;
                    edittext_afternoon_hour.visibility = View.INVISIBLE;
                    switch_afternoon_taken.visibility = View.INVISIBLE;
                };
                if(switch_evening_activate.isChecked == false){
                    edittext_evening_medication.visibility = View.INVISIBLE;
                    edittext_evening_hour.visibility = View.INVISIBLE;
                    switch_evening_taken.visibility = View.INVISIBLE;
                };
                if(switch_night_activate.isChecked == false){
                    edittext_night_medication.visibility = View.INVISIBLE;
                    edittext_night_hour.visibility = View.INVISIBLE;
                    switch_night_taken.visibility = View.INVISIBLE;
                };

                switch_morning_activate?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        edittext_morning_medication.visibility = View.VISIBLE;
                        edittext_morning_hour.visibility = View.VISIBLE;
                        switch_morning_taken.visibility = View.VISIBLE;
                    } else {
                        edittext_morning_medication.visibility = View.INVISIBLE;
                        edittext_morning_hour.visibility = View.INVISIBLE;
                        switch_morning_taken.visibility = View.INVISIBLE;
                    }
                };

                switch_afternoon_activate?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        edittext_afternoon_medication.visibility = View.VISIBLE;
                        edittext_afternoon_hour.visibility = View.VISIBLE;
                        switch_afternoon_taken.visibility = View.VISIBLE;
                    } else {
                        edittext_afternoon_medication.visibility = View.INVISIBLE;
                        edittext_afternoon_hour.visibility = View.INVISIBLE;
                        switch_afternoon_taken.visibility = View.INVISIBLE;
                    }
                };

                switch_evening_activate?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        edittext_evening_medication.visibility = View.VISIBLE;
                        edittext_evening_hour.visibility = View.VISIBLE;
                        switch_evening_taken.visibility = View.VISIBLE;
                    } else {
                        edittext_evening_medication.visibility = View.INVISIBLE;
                        edittext_evening_hour.visibility = View.INVISIBLE;
                        switch_evening_taken.visibility = View.INVISIBLE;
                    }
                };

                switch_night_activate?.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        edittext_night_medication.visibility = View.VISIBLE;
                        edittext_night_hour.visibility = View.VISIBLE;
                        switch_night_taken.visibility = View.VISIBLE;
                    } else {
                        edittext_night_medication.visibility = View.INVISIBLE;
                        edittext_night_hour.visibility = View.INVISIBLE;
                        switch_night_taken.visibility = View.INVISIBLE;
                    }
                };
            }
        }
    }

    private fun updateResidentMedication(residenceid: String, weekday: String, residentalert: ResidentAlert, nal: NavController){
        var btn_update = view?.findViewById(R.id.button_medication_resident_update) as Button;


        btn_update.setOnClickListener{
            var edittext_morning_medication = view?.findViewById(R.id.edittext_medication_resident_morning_medication) as TextView;
            var edittext_morning_hour = view?.findViewById(R.id.edittext_medication_resident_morning_hour) as TextView;
            var switch_morning_taken = view?.findViewById(R.id.switch_medication_resident_morning_taken) as Switch;
            var switch_morning_activate = view?.findViewById(R.id.switch_medication_resident_morning_activate) as Switch;

            var edittext_afternoon_medication = view?.findViewById(R.id.edittext_medication_resident_afternoon_medication) as TextView;
            var edittext_afternoon_hour = view?.findViewById(R.id.edittext_medication_resident_afternoon_hour) as TextView;
            var switch_afternoon_taken = view?.findViewById(R.id.switch_medication_resident_afternoon_taken) as Switch;
            var switch_afternoon_activate = view?.findViewById(R.id.switch_medication_resident_afternoon_activate) as Switch;

            var edittext_evening_medication = view?.findViewById(R.id.edittext_medication_resident_evening_medication) as TextView;
            var edittext_evening_hour = view?.findViewById(R.id.edittext_medication_resident_evening_hour) as TextView;
            var switch_evening_taken = view?.findViewById(R.id.switch_medication_resident_evening_taken) as Switch;
            var switch_evening_activate = view?.findViewById(R.id.switch_medication_resident_evening_activate) as Switch;

            var edittext_night_medication = view?.findViewById(R.id.edittext_medication_resident_night_medication) as TextView;
            var edittext_night_hour = view?.findViewById(R.id.edittext_medication_resident_night_hour) as TextView;
            var switch_night_taken = view?.findViewById(R.id.switch_medication_resident_afternoon_taken) as Switch;
            var switch_night_activate = view?.findViewById(R.id.switch_medication_resident_night_activate) as Switch;

            var switch_morning_taken_value = "";
            var switch_afternoon_taken_value = "";
            var switch_evening_taken_value = "";
            var switch_night_taken_value = "";

            if(switch_morning_taken.isChecked) switch_morning_taken_value = "Yes" else switch_morning_taken_value = "No";
            if(switch_afternoon_taken.isChecked) switch_afternoon_taken_value = "Yes" else switch_afternoon_taken_value = "No";
            if(switch_evening_taken.isChecked) switch_evening_taken_value = "Yes" else switch_evening_taken_value = "No";
            if(switch_night_taken.isChecked) switch_night_taken_value = "Yes" else switch_night_taken_value = "No";

            var edittext_morning_medication_value = "";
            var edittext_afternoon_medication_value = "";
            var edittext_evening_medication_value = "";
            var edittext_night_medication_value = "";

            // Night: 00:00 - 6:59
            // Morning: 7:00 - 11:59
            // Afternoon: 12:00 - 17:59
            // Evening: 18:00 - 23:59

            if(!switch_morning_activate.isChecked){
                edittext_morning_medication_value = "---";
                edittext_morning_medication.setText(edittext_morning_medication_value);
                edittext_morning_hour.setText("07:00");
            } else {
                edittext_morning_medication_value = edittext_morning_medication.text.toString();
            };

            if(!switch_afternoon_activate.isChecked){
                edittext_afternoon_medication_value = "---";
                edittext_afternoon_medication.setText(edittext_afternoon_medication_value);
                edittext_afternoon_hour.setText("12:00");
            } else {
                edittext_afternoon_medication_value = edittext_afternoon_medication.text.toString();
            };

            if(!switch_evening_activate.isChecked){
                edittext_evening_medication_value = "---";
                edittext_evening_medication.setText(edittext_evening_medication_value);
                edittext_evening_hour.setText("18:00");
            } else {
                edittext_evening_medication_value = edittext_evening_medication.text.toString();
            };

            if(!switch_night_activate.isChecked){
                edittext_night_medication_value = "---";
                edittext_night_medication.setText(edittext_night_medication_value);
                edittext_night_hour.setText("00:00");
            } else {
                edittext_night_medication_value = edittext_night_medication.text.toString();
            };

            try {
                if(edittext_morning_hour.text.isNotEmpty() &&
                    edittext_afternoon_hour.text.isNotEmpty() &&
                    edittext_evening_hour.text.isNotEmpty() &&
                    edittext_night_hour.text.isNotEmpty() &&

                    edittext_morning_medication.text.isNotEmpty() &&
                    edittext_afternoon_medication.text.isNotEmpty() &&
                    edittext_evening_medication.text.isNotEmpty() &&
                    edittext_night_medication.text.isNotEmpty() &&

                    edittext_morning_medication.text.toString().isNotEmpty() &&
                    edittext_afternoon_medication.text.toString().isNotEmpty() &&
                    edittext_evening_medication.text.toString().isNotEmpty() &&
                    edittext_night_medication.text.toString().isNotEmpty()){

                    var hour_morning = edittext_morning_hour.text.toString().split(":");
                    var hour_afternoon = edittext_afternoon_hour.text.toString().split(":");
                    var hour_evening = edittext_evening_hour.text.toString().split(":");
                    var hour_night = edittext_night_hour.text.toString().split(":");

                    if((Integer.parseInt(hour_morning[0]) >= 7 && Integer.parseInt(hour_morning[0]) <= 11) &&
                        (Integer.parseInt(hour_afternoon[0]) >= 12 && Integer.parseInt(hour_afternoon[0]) <= 17) &&
                        (Integer.parseInt(hour_evening[0]) >= 18 && Integer.parseInt(hour_evening[0]) <= 23) &&
                        (Integer.parseInt(hour_night[0]) >= 0 && Integer.parseInt(hour_night[0]) <= 6) &&

                        (Integer.parseInt(hour_morning[1]) >= 0 && Integer.parseInt(hour_morning[1]) <= 59) &&
                        (Integer.parseInt(hour_afternoon[1]) >= 0 && Integer.parseInt(hour_afternoon[1]) <= 59) &&
                        (Integer.parseInt(hour_evening[1]) >= 0 && Integer.parseInt(hour_evening[1]) <= 59) &&
                        (Integer.parseInt(hour_night[1]) >= 0 && Integer.parseInt(hour_night[1]) <= 59) ){

                        var residentMedication: ResidentMedication = ResidentMedication(
                            weekday,
                            edittext_morning_hour.text.toString(),
                            edittext_morning_medication_value,
                            switch_morning_taken_value,
                            edittext_afternoon_hour.text.toString(),
                            edittext_afternoon_medication_value,
                            switch_afternoon_taken_value,
                            edittext_evening_hour.text.toString(),
                            edittext_evening_medication_value,
                            switch_evening_taken_value,
                            edittext_night_hour.text.toString(),
                            edittext_night_medication_value,
                            switch_night_taken_value
                        );

                        GlobalScope.launch(Dispatchers.IO) {
                            Thread.sleep(250);
                            updateResidentMedicationByDayAndIdResidenceAndIdResident(weekday, residenceid, residentalert.resident?.id.toString(), residentMedication);
                        }
                        var toast = Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                        toast.setMargin(50f, 50f);
                        toast.show();
                        nal.popBackStack();
                    }

                }
                else{
                    var toast = Toast.makeText(context, "Rellene todos los datos con datos válidos", Toast.LENGTH_SHORT);
                    toast.setMargin(50f, 50f);
                    toast.show();
                }
            }
            catch (e: Exception){
                var toast = Toast.makeText(context, "Introduzca datos válidos", Toast.LENGTH_SHORT);
                toast.setMargin(50f, 50f);
                toast.show();
            }
        }
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
        var residentalert: ResidentAlert = inputData[2] as ResidentAlert;

        var idresident: String = residentalert?.resident?.id.toString();

        showResidentMedication(residenceid, weekday, residentalert);
        updateResidentMedication(residenceid, weekday, residentalert, nal);
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