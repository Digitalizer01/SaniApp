package com.example.saniapp.residence

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.AdminActivity
import com.example.saniapp.R
import com.example.saniapp.ResidenceActivity
import com.example.saniapp.UserActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResidenceCreateStaff.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceCreateStaff : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_create_staff, container, false)
    }

    fun showInfo(
        userResidence: FirebaseUser?,
        residenceid: String,
        layout: LinearLayout,
        nal: NavController
    ) {
        var edittext_residence_staff_create_name =
            view?.findViewById(R.id.edittext_residence_staff_create_name) as TextView;
        var edittextpassword_residence_staff_create_password =
            view?.findViewById(R.id.edittextpassword_residence_staff_create_password) as TextView;
        var edittext_residence_staff_create_surnames =
            view?.findViewById(R.id.edittext_residence_staff_create_surnames) as TextView;
        var edittext_residence_staff_create_gender =
            view?.findViewById(R.id.edittext_residence_staff_create_gender) as TextView;
        var edittext_residence_staff_create_birthdate =
            view?.findViewById(R.id.edittext_residence_staff_create_birthdate) as TextView;
        var edittext_residence_staff_create_email =
            view?.findViewById(R.id.edittext_residence_staff_create_email) as TextView;
        var edittext_residence_staff_create_phone =
            view?.findViewById(R.id.edittext_residence_staff_create_phone) as TextView;

        var btn_create =
            view?.findViewById(R.id.button_residence_staff_create_create) as Button;

        btn_create.setOnClickListener {

            if (edittext_residence_staff_create_name.text.isNotEmpty() &&
                edittextpassword_residence_staff_create_password.text.isNotEmpty() &&
                edittext_residence_staff_create_surnames.text.isNotEmpty() &&
                edittext_residence_staff_create_gender.text.isNotEmpty() &&
                edittext_residence_staff_create_birthdate.text.isNotEmpty() &&
                edittext_residence_staff_create_email.text.isNotEmpty() &&
                edittext_residence_staff_create_phone.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        edittext_residence_staff_create_email.text.toString(),
                        edittextpassword_residence_staff_create_password.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val toast = Toast.makeText(context, "Registrado", Toast.LENGTH_SHORT)
                            toast.setMargin(50f, 50f)
                            toast.show()
                            val user = FirebaseAuth.getInstance().currentUser

                            // Name
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Name")
                                .setValue(edittext_residence_staff_create_name.text.toString())
                            // Surnames
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Surnames")
                                .setValue(edittext_residence_staff_create_surnames.text.toString())
                            // Gender
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Gender")
                                .setValue(edittext_residence_staff_create_gender.text.toString())
                            // Birthdate
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Birthdate")
                                .setValue(edittext_residence_staff_create_birthdate.text.toString())
                            // Email
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Email")
                                .setValue(edittext_residence_staff_create_email.text.toString())
                            // Phone
                            Firebase.database("https://pastilleroelectronico-f32c6-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Residences/" + residenceid + "/Staff/" + user?.uid.toString() + "/Data/Phone")
                                .setValue(edittext_residence_staff_create_phone.text.toString())

                                FirebaseAuth.getInstance().signInWithEmailAndPassword("residenciaprado@prado.com", "123456").addOnCompleteListener {
                                    nal.navigate(R.id.residenceProfile);
                                }
                        } else {
                            val toast = Toast.makeText(context, "No registrado", Toast.LENGTH_SHORT)
                            toast.setMargin(50f, 50f)
                            toast.show()
                        }

                    }
                print("hola")

                print("hola")

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
            view?.findViewById(R.id.id_linearlayout_residence_staff_create) as LinearLayout

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
         * @return A new instance of fragment ResidenceCreateStaff.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceCreateStaff().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}