package com.example.saniapp.residence

import android.app.AlertDialog
import android.content.ContentValues.TAG
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
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

    fun showInfo(
        residenceid: String,
        residencestaffkey: String,
        residencestafffullname: String,
        layout: LinearLayout,
        nal: NavController
    ) {
        var mapUser: Map<String, String>? = null;

        var info =
            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Residences/$residenceid/Staff/")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val snapshotIterator = dataSnapshot.children;
                        val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                        var residencestaffmap: HashMap<String, HashMap<String, String>> =
                            HashMap<String, HashMap<String, String>>();
                        while (iterator.hasNext()) {
                            var hashmap = iterator.next();
                            residencestaffmap =
                                hashmap.value as HashMap<String, HashMap<String, String>>;
                            if (hashmap.key == residencestaffkey) {
                                var edittext_residence_staff_profile_title =
                                    view?.findViewById(R.id.text_residence_staff_profile_title) as TextView;
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

                                var email = "";

                                var edittextdate_residence_staff_profile_birthdate =
                                    view?.findViewById(R.id.edittextdate_residence_staff_profile_birthdate) as TextView;
                                var edittextphone_residence_staff_profile_phone =
                                    view?.findViewById(R.id.edittextphone_residence_staff_profile_phone) as TextView;

                                edittext_residence_staff_profile_title.setText(
                                    residencestaffmap["Data"]?.get(
                                        "Name"
                                    ) + " " + residencestaffmap["Data"]?.get(
                                        "Surnames"
                                    )
                                );
                                edittext_residence_staff_profile_name.setText(
                                    residencestaffmap["Data"]?.get(
                                        "Name"
                                    )
                                );
                                edittext_residence_staff_profile_surnames.setText(
                                    residencestaffmap["Data"]?.get(
                                        "Surnames"
                                    )
                                );
                                if (residencestaffmap["Data"]?.get("Gender") == "Male") {
                                    spinner_residence_staff_profile_gender.setSelection(0);
                                } else {
                                    spinner_residence_staff_profile_gender.setSelection(1);
                                }

                                edittextdate_residence_staff_profile_birthdate.setText(
                                    residencestaffmap["Data"]?.get(
                                        "Birthdate"
                                    )
                                );
                                email = residencestaffmap["Data"]?.get(
                                    "Email"
                                ).toString();

                                edittextphone_residence_staff_profile_phone.setText(
                                    residencestaffmap["Data"]?.get(
                                        "Phone"
                                    )
                                );

                                var btn_update =
                                    view?.findViewById(R.id.button_residence_staff_profile_update) as Button;

                                btn_update.setOnClickListener {

                                    // Name
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey + "/Data/Name")
                                        .setValue(edittext_residence_staff_profile_name.text.toString());

                                    // Surnames
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey + "/Data/Surnames")
                                        .setValue(edittext_residence_staff_profile_surnames.text.toString());

                                    var gender_aux = "";
                                    if (spinner_residence_staff_profile_gender.selectedItem.toString() == "Hombre") {
                                        gender_aux = "Male";
                                    } else {
                                        gender_aux = "Female";
                                    }

                                    // Gender
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey + "/Data/Gender")
                                        .setValue(gender_aux);

                                    // Birthdate
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey + "/Data/BirthDate")
                                        .setValue(edittextdate_residence_staff_profile_birthdate.text.toString());

                                    // Phone
                                    Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey + "/Data/Phone")
                                        .setValue(edittextphone_residence_staff_profile_phone.text.toString());

                                    val toast =
                                        Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT);
                                    toast.show();

                                }

                                var btn_delete =
                                    view?.findViewById(R.id.button_residence_staff_profile_delete) as Button;

                                btn_delete.setOnClickListener {

                                    /*Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference("Residences/" + residenceid + "/Staff/" + residencestaffkey)
                                        .removeValue()

                                    val toast =
                                        Toast.makeText(context, "Borrado", Toast.LENGTH_SHORT);
                                    toast.show();*/

                                    val builder = AlertDialog.Builder(context);
                                    val inflater: LayoutInflater = layoutInflater;
                                    val dialogLayout: View =
                                        inflater.inflate(R.layout.fragment_dialog, null);
                                    val editText: EditText =
                                        dialogLayout.findViewById<EditText>(R.id.et_editText);

                                    with(builder) {
                                        setTitle("Enter some text!")
                                        setPositiveButton("Ok") { dialog, which ->
                                            val credential = EmailAuthProvider
                                                .getCredential(email, editText.text.toString())
                                            val useraux = Firebase.auth.currentUser!!

                                            useraux.reauthenticate(credential)
                                                .addOnCompleteListener {
                                                    Log.d(
                                                        TAG,
                                                        "User re-authenticated."
                                                    )
                                                }
                                            var toast = Toast.makeText(
                                                context,
                                                editText.text.toString(),
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.setMargin(50f, 50f)
                                            toast.show()
                                        }

                                        setNegativeButton("Cancel") { dialog, which ->
                                            var toast = Toast.makeText(
                                                context,
                                                "NO ACEPTADO",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.setMargin(50f, 50f)
                                            toast.show()
                                        }

                                        setView(dialogLayout);
                                        show();
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout =
            view?.findViewById(R.id.id_linearlayout_residence_staff_profile) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residenceid = inputData[0];
        var residencestaffmap = inputData[1];
        var residencestaffkey = inputData[2];
        var residencestafffullname = inputData[3];

        showInfo(
            residenceid as String,
            residencestaffkey as String,
            residencestafffullname as String,
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