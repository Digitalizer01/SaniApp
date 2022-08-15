package com.example.saniapp.admin

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminMenu : Fragment() {
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
        return inflater.inflate(R.layout.fragment_admin_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);
        val nal = Navigation.findNavController(view);
        var layout = view?.findViewById(R.id.id_linearlayout_admin_menu) as LinearLayout;

        val user = FirebaseAuth.getInstance().currentUser

        var btn_admin_residences = view?.findViewById(R.id.button_admin_menu_residences) as Button;
        btn_admin_residences.setOnClickListener{
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString());
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.adminResidences, bundle);
        }

        var btn_admin_create_residences = view?.findViewById(R.id.button_admin_menu_create_residences) as Button;
        btn_admin_create_residences.setOnClickListener{
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString());
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.adminCreateResidence, bundle);
        }
/*
        val useraux = Firebase.auth.currentUser!!

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
                val credential = EmailAuthProvider
                    .getCredential("prueba@gmail.com", "123456")

        // Prompt the user to re-provide their sign-in credentials
                useraux.reauthenticate(credential)
                    .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }

        Firebase.auth.signOut();
        var usernuevo = Firebase.auth.signInWithCredential(credential);
        val usernuevo2 = Firebase.auth.currentUser!!

        usernuevo2.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
                }
            }
        println("Hola");
*/

        //var rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false);

        val builder = AlertDialog.Builder(context);
        val inflater: LayoutInflater = layoutInflater;
        val dialogLayout: View = inflater.inflate(R.layout.fragment_dialog, null);
        val editText: EditText = dialogLayout.findViewById<EditText>(R.id.et_editText);

        val args = this.arguments

        val userinfo: Array<*> = args?.getSerializable("aux") as Array<*>;
        println("hola")

        with(builder){
            setTitle("Enter some text!")
            setPositiveButton("Ok"){dialog, which ->
                var toast = Toast.makeText(context, editText.text.toString(), Toast.LENGTH_SHORT)
                toast.setMargin(50f, 50f)
                toast.show()
            }

            setNegativeButton("Cancel"){dialog, which ->
                var toast = Toast.makeText(context, "NO ACEPTADO", Toast.LENGTH_SHORT)
                toast.setMargin(50f, 50f)
                toast.show()
            }

            setView(dialogLayout);
            show();
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminMenu.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminMenu().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}