package com.example.saniapp.residence

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
import com.google.firebase.auth.FirebaseUser
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

    fun showInfo(
        userResidence: FirebaseUser?,
        residenceid: String,
        layout: LinearLayout,
        nal: NavController
    ) {

        var info = Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
            "Residences/$residenceid/"
        ).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val snapshotIterator = dataSnapshot.children;
                val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                var residence_residents: HashMap<String, HashMap<String, String>> = HashMap<String, HashMap<String, String>>();
                while (iterator.hasNext()) {
                    var hashmap = iterator.next();
                    if (hashmap.key.toString() == "Residents"){
                        residence_residents = hashmap.value as HashMap<String, HashMap<String, String>>;
                        var keys = residence_residents.keys;
                        val itr = residence_residents?.keys?.iterator();
                        var randomid = getRandomString(15);
                        while(keys.contains(randomid)){
                            randomid = getRandomString(15);
                        }

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

                        var btn_create =
                            view?.findViewById(R.id.button_residence_residents_create_create) as Button;

                        edittext_residence_residents_create_id.setText(randomid);

                        btn_create.setOnClickListener {

                            if (edittext_residence_residents_create_name.text.isNotEmpty() &&
                                edittext_residence_residents_create_surnames.text.isNotEmpty() &&
                                spinner_residence_residents_create_gender.selectedItem.toString() != "" &&
                                edittext_residence_residents_create_birthdate.text.isNotEmpty() &&
                                edittext_residence_residents_create_id.text.isNotEmpty()) {

                                val toast = Toast.makeText(context, "Registrado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()
                                val user = FirebaseAuth.getInstance().currentUser

                                // Name
                                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Residences/" + residenceid + "/Residents/" + edittext_residence_residents_create_id.text.toString() + "/Data/Name")
                                    .setValue(edittext_residence_residents_create_name.text.toString())
                                // Surnames
                                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Residences/" + residenceid + "/Residents/" + edittext_residence_residents_create_id.text.toString() + "/Data/Surnames")
                                    .setValue(edittext_residence_residents_create_surnames.text.toString())

                                var gender_aux = "";
                                if(spinner_residence_residents_create_gender.selectedItem.toString() == "Hombre"){
                                    gender_aux = "Male";
                                }
                                else{
                                    gender_aux = "Female";
                                }
                                // Gender
                                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Residences/" + residenceid + "/Residents/" + edittext_residence_residents_create_id.text.toString() + "/Data/Gender")
                                    .setValue(gender_aux)
                                // Birthdate
                                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Residences/" + residenceid + "/Residents/" + edittext_residence_residents_create_id.text.toString() + "/Data/Birthdate")
                                    .setValue(edittext_residence_residents_create_birthdate.text.toString())
                                // ID
                                Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("Residences/" + residenceid + "/Residents/" + edittext_residence_residents_create_id.text.toString() + "/Data/IDPillbox")
                                    .setValue(randomid)

                            } else {

                                val toast = Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()

                            }
                        }
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }

    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return List(length) { charset.random() }
            .joinToString("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout =
            view?.findViewById(R.id.id_linearlayout_residence_residents_create) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser
        val args = this.arguments

        showInfo(
            user,
            user?.uid.toString(),
            layout,
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