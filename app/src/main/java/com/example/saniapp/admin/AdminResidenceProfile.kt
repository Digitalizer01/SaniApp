package com.example.saniapp.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

    fun showInfo(
        residencekey: String,
        layout: LinearLayout,
        nal: NavController
    ) {
        var mapUser: Map<String, String>? = null;

        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Residences/")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var residencemap: HashMap<String, HashMap<String, String>> =
                            HashMap<String, HashMap<String, String>>();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            residencemap =
                                hashmap.value as HashMap<String, HashMap<String, String>>;
                            if (hashmap.key == residencekey) {
                                var text_admin_residence_profile_title =
                                    view?.findViewById(R.id.text_admin_residence_profile_title) as TextView;
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
                                var edittextemail_admin_residence_profile_email =
                                    view?.findViewById(R.id.edittextemail_admin_residence_profile_email) as TextView;
                                var edittextphone_admin_residence_profile_phone =
                                    view?.findViewById(R.id.edittextphone_admin_residence_profile_phone) as TextView;
                                var edittext_admin_residence_profile_timetable =
                                    view?.findViewById(R.id.edittext_admin_residence_profile_timetable) as TextView;

                                text_admin_residence_profile_title.setText(
                                    residencemap["Data"]?.get(
                                        "Name"
                                    )
                                );
                                edittext_admin_residence_profile_name.setText(
                                    residencemap["Data"]?.get(
                                        "Name"
                                    )
                                );
                                edittext_admin_residence_profile_city.setText(
                                    residencemap["Data"]?.get(
                                        "City"
                                    )
                                );
                                edittext_admin_residence_profile_province.setText(
                                    residencemap["Data"]?.get(
                                        "Province"
                                    )
                                );
                                edittext_admin_residence_profile_country.setText(
                                    residencemap["Data"]?.get(
                                        "Country"
                                    )
                                );
                                edittext_admin_residence_profile_street.setText(
                                    residencemap["Data"]?.get(
                                        "Street"
                                    )
                                );
                                edittext_admin_residence_profile_zc.setText(
                                    residencemap["Data"]?.get(
                                        "ZC"
                                    )
                                );
                                edittextemail_admin_residence_profile_email.setText(
                                    residencemap["Data"]?.get(
                                        "Email"
                                    )
                                );
                                edittextphone_admin_residence_profile_phone.setText(
                                    residencemap["Data"]?.get(
                                        "Phone"
                                    )
                                );
                                edittext_admin_residence_profile_timetable.setText(
                                    residencemap["Data"]?.get(
                                        "Timetable"
                                    )
                                );

                                var btn_update =
                                    view?.findViewById(R.id.button_admin_residence_profile_update) as Button;

                                btn_update.setOnClickListener {

                                    // Name
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Name")
                                        .setValue(edittext_admin_residence_profile_name.text.toString());

                                    // City
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/City")
                                        .setValue(edittext_admin_residence_profile_city.text.toString());

                                    // Province
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Province")
                                        .setValue(edittext_admin_residence_profile_province.text.toString());

                                    // Country
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Country")
                                        .setValue(edittext_admin_residence_profile_country.text.toString());

                                    // Street
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Street")
                                        .setValue(edittext_admin_residence_profile_street.text.toString());

                                    // ZC
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/ZC")
                                        .setValue(edittext_admin_residence_profile_zc.text.toString());

                                    // Email
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Email")
                                        .setValue(edittextemail_admin_residence_profile_email.text.toString());

                                    // Phone
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Phone")
                                        .setValue(edittextphone_admin_residence_profile_phone.text.toString());

                                    // Timetable
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey + "/Data/Timetable")
                                        .setValue(edittext_admin_residence_profile_timetable.text.toString());

                                    val toast =
                                        Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                                    toast.show();

                                }

                                var btn_delete =
                                    view?.findViewById(R.id.button_admin_residence_profile_delete) as Button;

                                btn_delete.setOnClickListener {

                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residencekey)
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
            view?.findViewById(R.id.id_linearlayout_admin_residence_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residencemap = inputData[1];
        var residencekey = inputData[2];
        var residencefullname = inputData[3];

        showInfo(
            residencekey as String,
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