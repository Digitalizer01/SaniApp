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

    fun showInfo(
        residenceid: String,
        residenceresidentkey: String,
        residenceresidentfullname: String,
        layout: LinearLayout,
        nal: NavController
    ) {
        var mapUser: Map<String, String>? = null;

        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Residences/$residenceid/Residents/")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var residenceresidentmap: HashMap<String, HashMap<String, String>> =
                            HashMap<String, HashMap<String, String>>();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            residenceresidentmap =
                                hashmap.value as HashMap<String, HashMap<String, String>>;
                            if (hashmap.key == residenceresidentkey) {
                                var edittext_residence_residents_profile_title =
                                    view?.findViewById(R.id.text_residence_residents_profile_title) as TextView;
                                var edittext_residence_residents_profile_name =
                                    view?.findViewById(R.id.edittext_residence_residents_profile_name) as TextView;
                                var edittext_residence_residents_profile_surnames =
                                    view?.findViewById(R.id.edittext_residence_residents_profile_surnames) as TextView;
                                var spinner_residence_residents_profile_gender =
                                    view?.findViewById(R.id.spinner_residence_residents_profile_gender) as Spinner;
                                var opciones = arrayOf("Hombre", "Mujer")
                                spinner_residence_residents_profile_gender.adapter = ArrayAdapter<String>(
                                    requireContext(),
                                    android.R.layout.simple_expandable_list_item_1,
                                    opciones
                                )
                                var edittext_residence_residents_profile_gender =
                                    view?.findViewById(R.id.edittext_residence_residents_profile_gender) as TextView;
                                var edittextdate_residence_residents_profile_birthdate =
                                    view?.findViewById(R.id.edittextdate_residence_residents_profile_birthdate) as TextView;
                                var edittext_residence_residents_profile_id =
                                    view?.findViewById(R.id.edittext_residence_residents_profile_id) as TextView;

                                edittext_residence_residents_profile_title.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "Name"
                                    ) + " " + residenceresidentmap["Data"]?.get(
                                        "Surnames"
                                    )
                                );
                                edittext_residence_residents_profile_name.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "Name"
                                    )
                                );
                                edittext_residence_residents_profile_surnames.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "Surnames"
                                    )
                                );
                                edittext_residence_residents_profile_gender.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "Gender"
                                    )
                                );
                                edittextdate_residence_residents_profile_birthdate.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "BirthDate"
                                    )
                                );
                                edittext_residence_residents_profile_id.setText(
                                    residenceresidentmap["Data"]?.get(
                                        "IDPillbox"
                                    )
                                );

                                var btn_update =
                                    view?.findViewById(R.id.button_residence_residents_profile_update) as Button;

                                btn_update.setOnClickListener {

                                    // Name
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey + "/Data/Name")
                                        .setValue(edittext_residence_residents_profile_name.text.toString());

                                    // Surnames
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey + "/Data/Surnames")
                                        .setValue(edittext_residence_residents_profile_surnames.text.toString());

                                    // Gender
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey + "/Data/Gender")
                                        .setValue(edittext_residence_residents_profile_gender.text.toString());

                                    // Birthdate
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey + "/Data/BirthDate")
                                        .setValue(edittextdate_residence_residents_profile_birthdate.text.toString());

                                    // ID
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey + "/Data/IDPillbox")
                                        .setValue(edittext_residence_residents_profile_id.text.toString());

                                    val toast =
                                        Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                                    toast.show();

                                }

                                var btn_delete =
                                    view?.findViewById(R.id.button_residence_residents_profile_delete) as Button;

                                btn_delete.setOnClickListener {

                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Residents/" + residenceresidentkey)
                                        .removeValue()

                                    val toast =
                                        Toast.makeText(context, "Borrado", Toast.LENGTH_SHORT);
                                    toast.show();

                                }
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
        var layout =
            view?.findViewById(R.id.id_linearlayout_residence_residents_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residenceid = inputData[0];
        var residenceresidentmap = inputData[1];
        var residenceresidentkey = inputData[2];
        var residenceresidentfullname = inputData[3];

        showInfo(
            residenceid as String,
            residenceresidentkey as String,
            residenceresidentfullname as String,
            layout,
            nal
        );

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