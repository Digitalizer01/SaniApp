package com.example.saniapp.residence

import android.content.Intent.getIntent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.ResidenceActivity
import com.example.saniapp.resident.Resident
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
 * Use the [ResidenceResidentsProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceResidentsProfile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_residents_profile, container, false)
    }

    private fun showInfo(
        idResident: String,
        layout: LinearLayout,
        nal: NavController
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Thread.sleep(100);
                var resident = findResidentById(idResident);
                ;
                ;
                ;
                var residentdata = resident;
                ;
                ;
                ;

                withContext(Dispatchers.Main) {

                    Thread.sleep(100);

                    var residentdata2 = residentdata;
                    if (residentdata2?.name != null) {
                        var edittext_residence_residents_profile_title =
                            view?.findViewById(R.id.text_residence_residents_profile_title) as TextView;
                        var edittext_residence_residents_profile_name =
                            view?.findViewById(R.id.edittext_residence_residents_profile_name) as TextView;
                        var edittext_residence_residents_profile_surnames =
                            view?.findViewById(R.id.edittext_residence_residents_profile_surnames) as TextView;
                        var spinner_residence_residents_profile_gender =
                            view?.findViewById(R.id.spinner_residence_residents_profile_gender) as Spinner;
                        var genders = arrayOf("Hombre", "Mujer")
                        spinner_residence_residents_profile_gender.adapter =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_expandable_list_item_1, genders
                            )
                        var edittextdate_residence_residents_profile_birthdate =
                            view?.findViewById(R.id.edittextdate_residence_residents_profile_birthdate) as TextView;
                        var edittext_residence_residents_profile_id =
                            view?.findViewById(R.id.edittext_residence_residents_profile_id) as TextView;

                        edittext_residence_residents_profile_title.setText(residentdata2?.name + " " + residentdata2?.surnames);
                        edittext_residence_residents_profile_name.setText(residentdata2?.name);
                        edittext_residence_residents_profile_surnames.setText(residentdata2?.surnames);
                        if (residentdata2?.gender == "Male") {
                            spinner_residence_residents_profile_gender.setSelection(0);
                        } else {
                            spinner_residence_residents_profile_gender.setSelection(1);
                        }

                        edittextdate_residence_residents_profile_birthdate.setText(residentdata2?.birthdate);
                        edittext_residence_residents_profile_id.setText(residentdata2?.id);
                    }
                    
                    
                    
                    
                    
                }

                ("");
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    var toast =
                        Toast.makeText(context, "No se cargado el trabajador", Toast.LENGTH_SHORT);
                    toast.setMargin(50f, 50f);
                    toast.show();
                    showInfo(idResident, layout, nal);
                }
            }


        }
    }

    private fun deleteResident(idResident: String, nal: NavController) {
        var btn_delete =
            view?.findViewById(R.id.button_residence_residents_profile_delete) as Button;
        btn_delete.setOnClickListener {

            deleteResidentById(idResident);

            val toast =
                Toast.makeText(context, "Borrado", Toast.LENGTH_SHORT);
            toast.show();
            nal.popBackStack();
        }
    }

    private fun updateResidentNal(nal: NavController) {

        var edittext_residence_residents_profile_title =
            view?.findViewById(R.id.text_residence_residents_profile_title) as TextView;
        var edittext_residence_residents_profile_name =
            view?.findViewById(R.id.edittext_residence_residents_profile_name) as TextView;
        var edittext_residence_residents_profile_surnames =
            view?.findViewById(R.id.edittext_residence_residents_profile_surnames) as TextView;
        var spinner_residence_residents_profile_gender =
            view?.findViewById(R.id.spinner_residence_residents_profile_gender) as Spinner;
        var genders = arrayOf("Hombre", "Mujer")
        spinner_residence_residents_profile_gender.adapter =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_expandable_list_item_1, genders
            )
        var edittextdate_residence_residents_profile_birthdate =
            view?.findViewById(R.id.edittextdate_residence_residents_profile_birthdate) as TextView;
        var edittext_residence_residents_profile_id =
            view?.findViewById(R.id.edittext_residence_residents_profile_id) as TextView;

        var btn_update =
            view?.findViewById(R.id.button_residence_residents_profile_update) as Button;

        btn_update.setOnClickListener {
            if (edittext_residence_residents_profile_title.text.isNotEmpty() &&
                edittext_residence_residents_profile_name.text.isNotEmpty() &&
                edittext_residence_residents_profile_surnames.text.isNotEmpty() &&
                edittextdate_residence_residents_profile_birthdate.text.isNotEmpty() &&
                edittext_residence_residents_profile_id.text.isNotEmpty()
            ) {
                var gender_aux = "";
                if (spinner_residence_residents_profile_gender.selectedItem.toString() == "Hombre") {
                    gender_aux = "Male";
                } else {
                    gender_aux = "Female";
                }

                var resident: Resident = Resident(
                    edittextdate_residence_residents_profile_birthdate.text.toString(),
                    gender_aux,
                    edittext_residence_residents_profile_name.text.toString(),
                    edittext_residence_residents_profile_surnames.text.toString(),
                    edittext_residence_residents_profile_id.text.toString(),
                    null
                );

                updateResident(resident);

                val toast =
                    Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                toast.show();
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
            view?.findViewById(R.id.id_linearlayout_residence_residents_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val idResident: String = args?.getSerializable("argdata") as String;

        showInfo(
            idResident as String,
            layout,
            nal
        );

        updateResidentNal(nal as NavController);
        deleteResident(idResident as String, nal as NavController);
        print("Ok");
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResidenceResidentsProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceResidentsProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}