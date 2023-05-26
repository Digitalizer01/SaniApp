package com.example.saniapp.admin

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.residence.Residence
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminResidenceProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminResidenceProfile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_admin_residence_profile, container, false)
    }

    private fun showResidence(
        residencekey: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                var residence = findResidenceById(residencekey);
                var residencedata = residence;

                withContext(Dispatchers.Main) {
                    var residencedata2 = residencedata;
                    if (residencedata2?.name != null) {
                        var text_admin_residence_profile_title =
                            view?.findViewById(R.id.text_admin_residence_profile_title) as TextView;
                        var text_admin_residence_profile_id =
                            view?.findViewById(R.id.text_admin_residence_profile_id) as TextView;
                        var edittext_admin_residence_profile_name =
                            view?.findViewById(R.id.edittext_admin_residence_profile_name) as TextView;
                        var edittext_admin_residence_profile_city =
                            view?.findViewById(R.id.edittext_admin_residence_profile_city) as TextView;
                        var edittext_admin_residence_profile_province =
                            view?.findViewById(R.id.edittext_admin_residence_profile_province) as TextView;
                        var edittext_admin_residence_profile_country =
                            view?.findViewById(R.id.edittext_admin_residence_profile_country) as TextView;
                        var edittext_admin_residence_profile_street =
                            view?.findViewById(R.id.edittext_admin_residence_profile_street) as TextView;
                        var edittext_admin_residence_profile_zc =
                            view?.findViewById(R.id.edittext_admin_residence_profile_zc) as TextView;
                        var edittext_admin_residence_profile_email =
                            view?.findViewById(R.id.edittext_admin_residence_profile_email) as TextView;
                        var emailresidence = "";
                        var edittextphone_admin_residence_profile_phone =
                            view?.findViewById(R.id.edittextphone_admin_residence_profile_phone) as TextView;
                        var edittext_admin_residence_profile_timetable =
                            view?.findViewById(R.id.edittext_admin_residence_profile_timetable) as TextView;

                        text_admin_residence_profile_title.setText(residencedata2.name);
                        text_admin_residence_profile_id.setText("ID: " + residencedata2.id);
                        edittext_admin_residence_profile_name.setText(residencedata2.name);
                        edittext_admin_residence_profile_city.setText(residencedata2.city);
                        edittext_admin_residence_profile_province.setText(residencedata2.province);
                        edittext_admin_residence_profile_country.setText(residencedata2.country);
                        edittext_admin_residence_profile_street.setText(residencedata2.street);
                        edittext_admin_residence_profile_zc.setText(residencedata2.zc);
                        edittext_admin_residence_profile_email.setText(residencedata2.email);
                        edittextphone_admin_residence_profile_phone.setText(residencedata2.phone);
                        edittext_admin_residence_profile_timetable.setText(residencedata2.timetable);
                    }
                    (residencedata2);
                    (residencedata2?.id);
                }
            }
            catch(e: Exception){
                withContext(Dispatchers.Main) {
                    var toast = Toast.makeText(context, "No se cargado la residencia", Toast.LENGTH_SHORT)
                    toast.setMargin(50f, 50f)
                    toast.show()
                    showResidence(residencekey)
                }
            }


        }
    }

    private fun updateResidence() {
        var text_admin_residence_profile_title =
            view?.findViewById(R.id.text_admin_residence_profile_title) as TextView;
        var text_admin_residence_profile_id =
            view?.findViewById(R.id.text_admin_residence_profile_id) as TextView;
        var edittext_admin_residence_profile_name =
            view?.findViewById(R.id.edittext_admin_residence_profile_name) as TextView;
        var edittext_admin_residence_profile_city =
            view?.findViewById(R.id.edittext_admin_residence_profile_city) as TextView;
        var edittext_admin_residence_profile_province =
            view?.findViewById(R.id.edittext_admin_residence_profile_province) as TextView;
        var edittext_admin_residence_profile_country =
            view?.findViewById(R.id.edittext_admin_residence_profile_country) as TextView;
        var edittext_admin_residence_profile_street =
            view?.findViewById(R.id.edittext_admin_residence_profile_street) as TextView;
        var edittext_admin_residence_profile_zc =
            view?.findViewById(R.id.edittext_admin_residence_profile_zc) as TextView;
        var edittextphone_admin_residence_profile_phone =
            view?.findViewById(R.id.edittextphone_admin_residence_profile_phone) as TextView;
        var edittext_admin_residence_profile_timetable =
            view?.findViewById(R.id.edittext_admin_residence_profile_timetable) as TextView;
        var edittext_admin_residence_profile_email =
            view?.findViewById(R.id.edittext_admin_residence_profile_email) as TextView;

        var btn_update =
            view?.findViewById(R.id.button_admin_residence_profile_update) as Button;

        btn_update.setOnClickListener {
            if (edittext_admin_residence_profile_name.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_city.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_province.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_country.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_street.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_zc.text.toString().isNotEmpty() &&
                edittextphone_admin_residence_profile_phone.text.toString().isNotEmpty() &&
                edittext_admin_residence_profile_timetable.text.toString().isNotEmpty()
            ) {

                var residence: Residence = Residence(
                    edittext_admin_residence_profile_city.text.toString(),
                    edittext_admin_residence_profile_country.text.toString(),
                    edittext_admin_residence_profile_email.text.toString(),
                    edittext_admin_residence_profile_name.text.toString(),
                    edittextphone_admin_residence_profile_phone.text.toString(),
                    edittext_admin_residence_profile_province.text.toString(),
                    edittext_admin_residence_profile_street.text.toString(),
                    edittext_admin_residence_profile_timetable.text.toString(),
                    edittext_admin_residence_profile_zc.text.toString(),
                    text_admin_residence_profile_id.text.toString()
                )

                try{
                    updateResidenceByResidence(residence);
                    val toast =
                        Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                    toast.show();
                }
                catch (e: Exception){
                    val toast =
                        Toast.makeText(context, "No se ha podido actualizar", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } else {
                val toast =
                    Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout =
            view?.findViewById(R.id.id_linearlayout_admin_residence_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: String = args?.getString("argdata") as String;
        var residencekey = inputData;

        showResidence(
            residencekey as String
        );

        updateResidence();

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminResidenceProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminResidenceProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}