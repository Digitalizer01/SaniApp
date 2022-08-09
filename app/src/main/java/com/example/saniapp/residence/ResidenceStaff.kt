package com.example.saniapp.residence

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Space
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
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResidenceStaff.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceStaff : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_staff, container, false)
    }

    fun showInfo(id_user: String, layout: LinearLayout, nal: NavController) {
        var mapUser: Map<String, String>? = null;

        var info = Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/").getReference(
            "Residences/$id_user/"
        ).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val snapshotIterator = dataSnapshot.children;
                val iterator: Iterator<DataSnapshot> = snapshotIterator.iterator();
                var residence_staff: HashMap<String, HashMap<String, String>> = HashMap<String, HashMap<String, String>>();
                while (iterator.hasNext()) {
                    var hashmap = iterator.next();
                    if (hashmap.key.toString() == "Staff"){
                        residence_staff = hashmap.value as HashMap<String, HashMap<String, String>>;

                        val itr = residence_staff?.keys?.iterator()
                        while (itr?.hasNext() == true) {
                            val key = itr?.next()
                            val value: HashMap<String, HashMap<String, String>> = residence_staff?.get(key) as HashMap<String, HashMap<String, String>>;
                            val name_surname_resident = value["Data"]?.get("Name") + " " + value["Data"]?.get("Surnames");
                            var btn_residence_staff = Button(context);

                            btn_residence_staff.setText(name_surname_resident);
                            btn_residence_staff.setTextColor(Color.WHITE);
                            btn_residence_staff.setBackgroundColor(Color.rgb(98, 0, 238));
                            btn_residence_staff.setOnClickListener{
                                val bundle = Bundle()
                                var argdata = arrayOf(id_user, value, key, name_surname_resident);
                                bundle.putSerializable("argdata", argdata);
                                nal.navigate(R.id.residenceStaffProfile, bundle);
                            }
                            layout.addView(btn_residence_staff);
                        }

                        var btn_residence_staff_create = Button(context);
                        btn_residence_staff_create.setText("Crear");
                        btn_residence_staff_create.setTextColor(Color.WHITE);
                        btn_residence_staff_create.setBackgroundColor(Color.RED);
                        btn_residence_staff_create.setOnClickListener{
                            val bundle = Bundle();
                            nal.navigate(R.id.residenceCreateStaff, bundle);
                        }
                        layout.addView(btn_residence_staff_create);

                        print("Done");
                    }

                    print("New")
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
        var layout = view?.findViewById(R.id.id_linearlayout_residence_staff) as LinearLayout

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
         * @return A new instance of fragment ResidenceStaff.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceStaff().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}