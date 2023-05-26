package com.example.saniapp.admin

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
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
import com.example.saniapp.residence.Residence
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminCreateResidence.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminCreateResidence : Fragment() {
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
        return inflater.inflate(R.layout.fragment_admin_create_residence, container, false)
    }

    private fun createResidence(adminemail: String, adminpass: String) {
        var edittext_admin_create_residence_name =
            view?.findViewById(R.id.edittext_admin_create_residence_name) as TextView;
        var edittextpassword_admin_create_residence_password =
            view?.findViewById(R.id.edittextpassword_admin_create_residence_password) as TextView;
        var edittext_admin_create_residence_city =
            view?.findViewById(R.id.edittext_admin_create_residence_city) as TextView;
        var edittext_admin_create_residence_province =
            view?.findViewById(R.id.edittext_admin_create_residence_province) as TextView;
        var edittext_admin_create_residence_country =
            view?.findViewById(R.id.edittext_admin_create_residence_country) as TextView;
        var edittext_admin_create_residence_street =
            view?.findViewById(R.id.edittext_admin_create_residence_street) as TextView;
        var edittext_admin_create_residence_zc =
            view?.findViewById(R.id.edittext_admin_create_residence_zc) as TextView;
        var edittextemail_admin_create_residence_email =
            view?.findViewById(R.id.edittextemail_admin_create_residence_email) as TextView;
        var edittextphone_admin_create_residence_phone =
            view?.findViewById(R.id.edittextphone_admin_create_residence_phone) as TextView;
        var edittext_admin_create_residence_timetable =
            view?.findViewById(R.id.edittext_admin_create_residence_timetable) as TextView;

        var btn_create =
            view?.findViewById(R.id.button_admin_create_residence_create) as Button;

        btn_create.setOnClickListener {

            if (edittext_admin_create_residence_name.text.isNotEmpty() &&
                edittextpassword_admin_create_residence_password.text.isNotEmpty() &&
                edittext_admin_create_residence_city.text.isNotEmpty() &&
                edittext_admin_create_residence_province.text.isNotEmpty() &&
                edittext_admin_create_residence_country.text.isNotEmpty() &&
                edittext_admin_create_residence_street.text.isNotEmpty() &&
                edittext_admin_create_residence_zc.text.isNotEmpty() &&
                edittextemail_admin_create_residence_email.text.isNotEmpty() &&
                edittextphone_admin_create_residence_phone.text.isNotEmpty() &&
                edittext_admin_create_residence_timetable.text.isNotEmpty()
            ) {
                var residence = Residence(
                    edittext_admin_create_residence_city.text.toString(),
                    edittext_admin_create_residence_country.text.toString(),
                    edittextemail_admin_create_residence_email.text.toString(),
                    edittext_admin_create_residence_name.text.toString(),
                    edittextphone_admin_create_residence_phone.text.toString(),
                    edittext_admin_create_residence_province.text.toString(),
                    edittext_admin_create_residence_street.text.toString(),
                    edittext_admin_create_residence_timetable.text.toString(),
                    edittext_admin_create_residence_zc.text.toString(),
                    ""
                );

                GlobalScope.launch(Dispatchers.IO) {
                    var success = false;
                    try {
                        while (!success) {
                            success = createResidence(
                                residence,
                                edittextpassword_admin_create_residence_password.text.toString(),
                                adminemail,
                                adminpass
                            ) == true;
                        }
                        withContext(Dispatchers.Main) {
                            if (success) {
                                var toast =
                                    Toast.makeText(context, "Registrado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()
                            } else {
                                var toast =
                                    Toast.makeText(context, "No registrado", Toast.LENGTH_SHORT)
                                toast.setMargin(50f, 50f)
                                toast.show()
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            var toast =
                                Toast.makeText(context, "No registrado", Toast.LENGTH_SHORT);
                            toast.setMargin(50f, 50f);
                            toast.show();
                        }
                    }
                }
            } else {
                val toast = Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT)
                toast.setMargin(50f, 50f)
                toast.show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout =
            view?.findViewById(R.id.id_linearlayout_admin_create_residence) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments
        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var adminemail = inputData[1];
        var adminpass = inputData[2];

        createResidence(adminemail as String, adminpass as String);

        print("Ok");
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminCreateResidence.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminCreateResidence().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}