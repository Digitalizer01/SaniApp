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
import com.example.saniapp.resident.Resident
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
 * Use the [ResidenceCreateResident.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceCreateResident : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_create_resident, container, false)
    }

    private fun showInfo(
        residenceid: String,
        nal: NavController
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            Thread.sleep(500);
            var randomString = getRandomString(15);
            withContext(Dispatchers.Main) {
                var random = randomString;
                var edittext_residence_residents_create_name =
                    view?.findViewById(R.id.edittext_residence_residents_create_name) as TextView;
                var edittext_residence_residents_create_surnames =
                    view?.findViewById(R.id.edittext_residence_residents_create_surnames) as TextView;
                var spinner_residence_residents_create_gender =
                    view?.findViewById(R.id.spinner_residence_residents_create_gender) as Spinner;
                var genders = arrayOf("Hombre", "Mujer")
                spinner_residence_residents_create_gender.adapter = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_expandable_list_item_1, genders
                )
                var edittext_residence_residents_create_birthdate =
                    view?.findViewById(R.id.edittext_residence_residents_create_birthdate) as TextView;
                var edittext_residence_residents_create_id =
                    view?.findViewById(R.id.edittext_residence_residents_create_id) as TextView;



                edittext_residence_residents_create_id.setText(random);

                var btn_create =
                    view?.findViewById(R.id.button_residence_residents_create_create) as Button;

                btn_create.setOnClickListener {
                    if (edittext_residence_residents_create_name.text.isNotEmpty() &&
                        edittext_residence_residents_create_surnames.text.isNotEmpty() &&
                        spinner_residence_residents_create_gender.selectedItem.toString() != "" &&
                        edittext_residence_residents_create_birthdate.text.isNotEmpty() &&
                        edittext_residence_residents_create_id.text.isNotEmpty()
                    ) {

                        var gender_aux = "";
                        if (spinner_residence_residents_create_gender.selectedItem.toString() == "Hombre") {
                            gender_aux = "Male";
                        } else {
                            gender_aux = "Female";
                        }

                        var resident: Resident = Resident(
                            edittext_residence_residents_create_birthdate.text.toString(),
                            gender_aux,
                            edittext_residence_residents_create_name.text.toString(),
                            edittext_residence_residents_create_surnames.text.toString(),
                            edittext_residence_residents_create_id.text.toString(),
                            false
                        );

                        createResident(resident);

                        val toast =
                            Toast.makeText(context, "Registrado", Toast.LENGTH_SHORT)
                        toast.setMargin(50f, 50f)
                        toast.show()
                        val user = FirebaseAuth.getInstance().currentUser

                        nal.clearBackStack(R.id.residenceProfile);
                    } else {

                        val toast = Toast.makeText(
                            context,
                            "Rellene todos los campos",
                            Toast.LENGTH_SHORT
                        )
                        toast.setMargin(50f, 50f)
                        toast.show()

                    }
                }
            }
        }


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout =
            view?.findViewById(R.id.id_linearlayout_residence_residents_create) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser
        val args = this.arguments

        showInfo(
            user?.uid.toString(),
            nal
        );

        if (user != null) {
            FirebaseAuth.getInstance().updateCurrentUser(user)
        };

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResidenceCreateResident.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceCreateResident().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}