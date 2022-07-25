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
 * Use the [UserProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    fun showInfo(id_user: String, layout: LinearLayout, nal: NavController) {
        var mapUser: Map<String, String>? = null;

        var info = Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Residences/").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val snapshotIterator = dataSnapshot.children;
                val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                var user: HashMap<String, String> = HashMap<String, String>();
                var residence_name: String = "";
                var residence: HashMap<String, String> = HashMap<String, String>();
                var residents: HashMap<String, HashMap<HashMap<String, String>, HashMap<String, String>>> = HashMap<String, HashMap<HashMap<String, String>, HashMap<String, String>>>();
                var found: Boolean = false;
                while (iterator.hasNext() && !found) {
                    var hashmap = iterator.next();
                    residence_name = hashmap.key.toString();
                    residence = hashmap.child("Data").value as HashMap<String, String>;
                    residents = hashmap.child("Residents").value as HashMap<String, HashMap<HashMap<String, String>, HashMap<String, String>>>;
                    var staff = hashmap.child("Staff");
                    var userHashMap: HashMap<String, HashMap<String, HashMap<String, String>>> =
                        staff.value as HashMap<String, HashMap<String, HashMap<String, String>>>;
                    user = userHashMap[id_user]?.get("Data")!!;
                    if(user != null){
                        found = true;
                    }
                    print("Done");
                    print("New")
                }

                var text_name = view?.findViewById(R.id.text_user_name) as TextView;
                var text_surnames = view?.findViewById(R.id.text_user_surnames) as TextView;
                var text_gender = view?.findViewById(R.id.text_user_gender) as TextView;
                var text_birthdate = view?.findViewById(R.id.text_user_birthdate) as TextView;
                var text_email = view?.findViewById(R.id.text_user_email) as TextView;
                var text_phone = view?.findViewById(R.id.text_user_phone) as TextView;
                var text_id = view?.findViewById(R.id.text_user_id) as TextView;

                text_name.setText("Nombre: " + user["Name"]);
                text_surnames.setText("Apellidos: " + user["Surnames"]);
                text_gender.setText("Género: " + user["Gender"]);
                text_birthdate.setText("Fecha nacimiento: " + user["BirthDate"]);
                text_email.setText("Email: " + user["Email"]);
                text_phone.setText("Móvil: " + user["Phone"]);
                text_id.setText("ID: " + id_user);

                var text_residence_name = view?.findViewById(R.id.text_user_residence_name) as TextView;
                var text_residence_country = view?.findViewById(R.id.text_user_residence_country) as TextView;
                var text_residence_city = view?.findViewById(R.id.text_user_residence_city) as TextView;
                var text_residence_provience = view?.findViewById(R.id.text_user_residence_provience) as TextView;
                var text_residence_street = view?.findViewById(R.id.text_user_residence_street) as TextView;
                var text_residence_zc = view?.findViewById(R.id.text_user_residence_zc) as TextView;
                var text_residence_phone = view?.findViewById(R.id.text_user_residence_phone) as TextView;
                var text_residence_email = view?.findViewById(R.id.text_user_residence_email) as TextView;
                var text_residence_timetable = view?.findViewById(R.id.text_user_residence_timetable) as TextView;

                text_residence_name.setText("Nombre: " + residence_name);
                text_residence_country.setText("País: " + residence["Country"]);
                text_residence_city.setText("Ciudad: " + residence["City"]);
                text_residence_provience.setText("Provincia: " + residence["Province"]);
                text_residence_street.setText("Calle: " + residence["Street"]);
                text_residence_zc.setText("CP: " + residence["ZC"]);
                text_residence_phone.setText("Teléfono: " + residence["Phone"]);
                text_residence_email.setText("Email: " + residence["Email"]);
                text_residence_timetable.setText("Horario: " + residence["Timetable"]);

                var mapresidents: HashMap<String, String> = HashMap<String, String>();
                val itr = residents.keys.iterator()
                while (itr.hasNext()) {
                    val key = itr.next()
                    val value: HashMap<String, HashMap<String, String>> = residents[key] as HashMap<String, HashMap<String, String>>;
                    val name_surname_resident = value["Data"]?.get("Name") + " " + value["Data"]?.get("Surnames");
                    mapresidents.put(key,  value["Data"]?.get("Name") + " " + value["Data"]?.get("Surnames"));

                    var btn_resident = Button(context);
                    btn_resident.setText(name_surname_resident);
                    btn_resident.setTextColor(Color.WHITE);
                    btn_resident.setBackgroundColor(Color.rgb(98, 0, 238));
                    btn_resident.setOnClickListener{
                        val bundle = Bundle()
                        var argdata = arrayOf(residence_name, value);
                        bundle.putSerializable("argdata", argdata);
                        nal.navigate(R.id.userResident, bundle);
                    }
                    layout.addView(btn_resident);
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
        var layout = view?.findViewById(R.id.id_linearlayout_user) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        showInfo(user?.uid.toString(), layout, nal);

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}