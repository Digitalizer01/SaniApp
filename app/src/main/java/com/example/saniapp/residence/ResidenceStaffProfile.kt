package com.example.saniapp.residence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.user.Staff
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
 * Use the [ResidenceStaffProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceStaffProfile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_staff_profile, container, false)
    }

    private fun showStaff(
        staffkey: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                var staff = findStaffById(staffkey);
                ;
                ;
                ;
                var staffdata = staff;
                ;
                ;
                ;

                withContext(Dispatchers.Main) {
                    
                    
                    var staffdata2 = staffdata;
                    if (staffdata2?.name != null) {
                        var edittext_residence_staff_profile_title =
                            view?.findViewById(R.id.text_residence_staff_profile_title) as TextView;
                        var edittext_residence_staff_profile_id =
                            view?.findViewById(R.id.text_residence_staff_profile_id) as TextView;
                        var edittext_residence_staff_profile_name =
                            view?.findViewById(R.id.edittext_residence_staff_profile_name) as TextView;
                        var edittext_residence_staff_profile_surnames =
                            view?.findViewById(R.id.edittext_residence_staff_profile_surnames) as TextView;
                        var spinner_residence_staff_profile_gender =
                            view?.findViewById(R.id.spinner_residence_staff_profile_gender) as Spinner;
                        var edittext_residence_staff_profile_email =
                            view?.findViewById(R.id.edittext_residence_staff_profile_email) as TextView;
                        var genders = arrayOf("Hombre", "Mujer")
                        spinner_residence_staff_profile_gender.adapter =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_expandable_list_item_1, genders
                            )

                        var email = "";

                        var edittextdate_residence_staff_profile_birthdate =
                            view?.findViewById(R.id.edittextdate_residence_staff_profile_birthdate) as TextView;
                        var edittextphone_residence_staff_profile_phone =
                            view?.findViewById(R.id.edittextphone_residence_staff_profile_phone) as TextView;

                        edittext_residence_staff_profile_id.setText("ID: " + staffkey);
                        edittext_residence_staff_profile_title.setText(staffdata2?.name + " " + staffdata2?.surnames.toString());
                        edittext_residence_staff_profile_name.setText(staffdata2?.name);
                        edittext_residence_staff_profile_surnames.setText(staffdata2?.surnames);
                        if (staffdata2?.gender == "Male") {
                            spinner_residence_staff_profile_gender.setSelection(0);
                        } else {
                            spinner_residence_staff_profile_gender.setSelection(1);
                        }

                        edittextdate_residence_staff_profile_birthdate.setText(staffdata2?.birthdate);
                        edittext_residence_staff_profile_email.setText(staffdata2?.email);

                        edittextphone_residence_staff_profile_phone.setText(staffdata2?.phone);
                    }
                    
                    
                    
                    
                    
                }

                ("");
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    var toast =
                        Toast.makeText(context, "No se cargado el trabajador", Toast.LENGTH_SHORT);
                    toast.setMargin(50f, 50f);
                    toast.show();
                    showStaff(staffkey);
                }
            }


        }
    }

    private fun updateStaffParam(staffkey: String, nal: NavController) {

        var edittext_residence_staff_profile_title =
            view?.findViewById(R.id.text_residence_staff_profile_title) as TextView;
        var edittext_residence_staff_profile_id =
            view?.findViewById(R.id.text_residence_staff_profile_id) as TextView;
        var edittext_residence_staff_profile_name =
            view?.findViewById(R.id.edittext_residence_staff_profile_name) as TextView;
        var edittext_residence_staff_profile_surnames =
            view?.findViewById(R.id.edittext_residence_staff_profile_surnames) as TextView;
        var spinner_residence_staff_profile_gender =
            view?.findViewById(R.id.spinner_residence_staff_profile_gender) as Spinner;
        var genders = arrayOf("Hombre", "Mujer")
        spinner_residence_staff_profile_gender.adapter =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_expandable_list_item_1, genders
            )

        var edittext_residence_staff_profile_email =
            view?.findViewById(R.id.edittext_residence_staff_profile_email) as TextView;

        var edittextdate_residence_staff_profile_birthdate =
            view?.findViewById(R.id.edittextdate_residence_staff_profile_birthdate) as TextView;
        var edittextphone_residence_staff_profile_phone =
            view?.findViewById(R.id.edittextphone_residence_staff_profile_phone) as TextView;

        var btn_update =
            view?.findViewById(R.id.button_residence_staff_profile_update) as Button;

        btn_update.setOnClickListener {
            if (edittext_residence_staff_profile_title.text.toString().isNotEmpty() &&
                edittext_residence_staff_profile_id.text.toString().isNotEmpty() &&
                edittext_residence_staff_profile_name.text.toString().isNotEmpty() &&
                edittext_residence_staff_profile_surnames.text.toString().isNotEmpty() &&
                spinner_residence_staff_profile_gender.toString().isNotEmpty() &&
                edittextdate_residence_staff_profile_birthdate.text.toString().isNotEmpty() &&
                edittextphone_residence_staff_profile_phone.text.toString().isNotEmpty()
            ) {

                var gender_aux = "";
                if (spinner_residence_staff_profile_gender.selectedItem.toString() == "Hombre") {
                    gender_aux = "Male";
                } else {
                    gender_aux = "Female";
                }

                var staff: Staff = Staff(
                    edittextdate_residence_staff_profile_birthdate.text.toString(),
                    edittext_residence_staff_profile_email.text.toString(),
                    gender_aux,
                    edittext_residence_staff_profile_name.text.toString(),
                    edittextphone_residence_staff_profile_phone.text.toString(),
                    edittext_residence_staff_profile_surnames.text.toString(),
                    staffkey
                );

                try{
                    updateStaff(staff);
                    val toast =
                        Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                    toast.show();
                    nal.popBackStack();
                }
                catch (e: Exception){
                    val toast =
                        Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else{
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
            view?.findViewById(R.id.id_linearlayout_residence_staff_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var staffkey = inputData[0];

        showStaff(staffkey as String);

        updateStaffParam(staffkey as String, nal as NavController);

        print("Ok");
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResidenceStaffProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceStaffProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}