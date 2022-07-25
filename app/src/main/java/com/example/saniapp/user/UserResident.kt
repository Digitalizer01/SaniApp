package com.example.saniapp.user

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
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

    fun showInfo(residencename: String, residentmap: HashMap<String, HashMap<String, String>>, layout: LinearLayout, nal: NavController) {
        var resident_birthdate: String = "";
        var resident_gender: String = "";
        var resident_idpillbox: String = "";
        var resident_name: String = "";
        var resident_surnames: String = "";

        resident_birthdate = residentmap["Data"]?.get("BirthDate").toString();
        resident_gender = residentmap["Data"]?.get("Gender").toString();
        resident_idpillbox = residentmap["Data"]?.get("IDPillbox").toString();
        resident_name = residentmap["Data"]?.get("Name").toString();
        resident_surnames = residentmap["Data"]?.get("Surnames").toString();

        var text_birthdate = view?.findViewById(R.id.text_resident_birthdate) as TextView;
        var text_gender = view?.findViewById(R.id.text_resident_gender) as TextView;
        var text_idpillbox = view?.findViewById(R.id.text_resident_id) as TextView;
        var text_name = view?.findViewById(R.id.text_resident_name) as TextView;
        var text_surnames = view?.findViewById(R.id.text_resident_surnames) as TextView;

        text_birthdate.setText("Fecha nacimiento: " + resident_birthdate);
        text_gender.setText("Género: " + resident_gender);
        text_idpillbox.setText("ID: " + resident_idpillbox);
        text_name.setText("Nombre: " + resident_name);
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

        val bundle = Bundle()

        btn_monday.setOnClickListener{
            var argdata = arrayOf(residencename, "Monday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_tuesday.setOnClickListener{
            var argdata = arrayOf(residencename, "Tuesday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_wednesday.setOnClickListener{
            var argdata = arrayOf(residencename, "Wednesday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_thursday.setOnClickListener{
            var argdata = arrayOf(residencename, "Thursday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_friday.setOnClickListener{
            var argdata = arrayOf(residencename, "Friday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_saturday.setOnClickListener{
            var argdata = arrayOf(residencename, "Saturday", residentmap);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.userResidentMedication, bundle);
        }
        btn_sunday.setOnClickListener{
            var argdata = arrayOf(residencename, "Sunday", residentmap);
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_resident) as LinearLayout
        val user = FirebaseAuth.getInstance().currentUser
        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residencename = inputData[0];
        var residentmap = inputData[1];
        showInfo(residencename as String,
            residentmap as HashMap<String, HashMap<String, String>>, layout, nal);
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