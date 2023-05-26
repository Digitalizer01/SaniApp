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
import com.example.saniapp.resident.Resident
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserResident.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserResident : Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_resident, container, false)
    }

    private fun showResidentInfo(residenceid: String, residentData: Resident, layout: LinearLayout, nal: NavController) {
        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(250);
            var residentalert = getResidentAlertByIdResidenceAndIdResident(
                residenceid,
                residentData?.id.toString()
            );

            var aux2 = residentalert;
            withContext(Dispatchers.Main) {
                var aux3 = aux2;
                var resident_birthdate: String = "";
                var resident_gender: String = "";
                var resident_idpillbox: String = "";
                var resident_name: String = "";
                var resident_surnames: String = "";

                resident_birthdate = aux3?.resident?.birthdate.toString();
                if (aux3?.resident?.gender.toString() == "Male") {
                    resident_gender = "Hombre";
                } else {
                    resident_gender = "Mujer";
                }
                resident_idpillbox = aux3?.resident?.id.toString();
                resident_name = aux3?.resident?.name.toString();
                resident_surnames = aux3?.resident?.surnames.toString();

                var text_title = view?.findViewById(R.id.text_resident_title) as TextView;
                var text_birthdate = view?.findViewById(R.id.text_resident_birthdate) as TextView;
                var text_gender = view?.findViewById(R.id.text_resident_gender) as TextView;
                var text_idpillbox = view?.findViewById(R.id.text_resident_id) as TextView;
                var text_name = view?.findViewById(R.id.text_resident_name) as TextView;
                var text_surnames = view?.findViewById(R.id.text_resident_surnames) as TextView;

                text_birthdate.setText("Fecha nacimiento: " + resident_birthdate);
                text_gender.setText("Género: " + resident_gender);
                text_idpillbox.setText("ID: " + resident_idpillbox);
                text_name.setText("Nombre: " + resident_name);
                text_title.setText(resident_name);
                text_surnames.setText("Apellidos: " + resident_surnames);

                var btn_monday = Button(context);
                var btn_tuesday = Button(context);
                var btn_wednesday = Button(context);
                var btn_thursday = Button(context);
                var btn_friday = Button(context);
                var btn_saturday = Button(context);
                var btn_sunday = Button(context);

                btn_monday.setText("Lunes");
                btn_tuesday.setText("Martes");
                btn_wednesday.setText("Miércoles");
                btn_thursday.setText("Jueves");
                btn_friday.setText("Viernes");
                btn_saturday.setText("Sábado");
                btn_sunday.setText("Domingo");

                btn_monday.setTextColor(Color.WHITE);
                btn_tuesday.setTextColor(Color.WHITE);
                btn_wednesday.setTextColor(Color.WHITE);
                btn_thursday.setTextColor(Color.WHITE);
                btn_friday.setTextColor(Color.WHITE);
                btn_saturday.setTextColor(Color.WHITE);
                btn_sunday.setTextColor(Color.WHITE);

                btn_monday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_tuesday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_wednesday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_thursday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_friday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_saturday.setBackgroundColor(Color.rgb(98, 0, 238));
                btn_sunday.setBackgroundColor(Color.rgb(98, 0, 238));

                if (residentalert?.monday == true) {
                    btn_monday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.tuesday == true) {
                    btn_tuesday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.wednesday == true) {
                    btn_wednesday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.thursday == true) {
                    btn_thursday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.friday == true) {
                    btn_friday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.saturday == true) {
                    btn_saturday.setBackgroundColor(Color.RED);
                }
                if (residentalert?.sunday == true) {
                    btn_sunday.setBackgroundColor(Color.RED);
                }

                val bundle = Bundle()

                btn_monday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Monday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_tuesday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Tuesday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_wednesday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Wednesday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_thursday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Thursday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_friday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Friday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_saturday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Saturday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }
                btn_sunday.setOnClickListener {
                    var argdata = arrayOf(residenceid, "Sunday", residentalert);
                    bundle.putSerializable("argdata", argdata);
                    nal.navigate(R.id.userResidentMedication, bundle);
                }

                layout.addView(btn_monday);
                layout.addView(btn_tuesday);
                layout.addView(btn_wednesday);
                layout.addView(btn_thursday);
                layout.addView(btn_friday);
                layout.addView(btn_saturday);
                layout.addView(btn_sunday);
                
                
                
            }



            
            
            
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_resident) as LinearLayout
        val user = FirebaseAuth.getInstance().currentUser
        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residenceid = inputData[0] as String;
        var resident = inputData[1] as Resident;
        showResidentInfo(
            residenceid as String,
            resident as Resident,
            layout as LinearLayout,
            nal as NavController
        );
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserResident.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserResident().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}