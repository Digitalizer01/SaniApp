package com.example.saniapp.admin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.google.firebase.auth.FirebaseAuth
import java.io.*
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.xml.parsers.DocumentBuilderFactory

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

    fun decrypt(
        algorithm: String,
        cipherText: String,
        key: SecretKeySpec,
        iv: IvParameterSpec
    ): String {
        val cipher = Cipher.getInstance(algorithm)
        cipher.init(Cipher.DECRYPT_MODE, key, iv)
        val plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText))
        return String(plainText)
    }

    fun readFileAsLinesUsingUseLines(fileName: String): List<String> =
        File(fileName).useLines { it.toList() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);
        val nal = Navigation.findNavController(view);
        var layout = view?.findViewById(R.id.id_linearlayout_admin_menu) as LinearLayout;

        val user = FirebaseAuth.getInstance().currentUser

        val algorithm = "AES/CBC/PKCS5Padding";
        val key = SecretKeySpec("1234567890123456".toByteArray(), "AES");
        val iv = IvParameterSpec(ByteArray(16));
        var cipherText = "";

        val path = context?.filesDir?.absolutePath
        var file = File("$path/").walk().find { it.name == "admindata.txt"}

        cipherText = file!!.readText()
        val plainText = decrypt(algorithm, cipherText, key, iv);

        println(plainText)
        val parts = plainText.split("\n");

        var btn_admin_residences = view?.findViewById(R.id.button_admin_menu_residences) as Button;
        btn_admin_residences.setOnClickListener {
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString(), parts[0], parts[1]);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.adminResidences, bundle);
        }

        var btn_admin_create_residences =
            view?.findViewById(R.id.button_admin_menu_create_residences) as Button;
        btn_admin_create_residences.setOnClickListener {
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString(), parts[0], parts[1]);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.adminCreateResidence, bundle);
        }

        //var rootView: View = inflater.inflate(R.layout.fragment_dialog, container, false);

        val builder = AlertDialog.Builder(context);
        val inflater: LayoutInflater = layoutInflater;
        val dialogLayout: View = inflater.inflate(R.layout.fragment_dialog, null);
        val editText: EditText = dialogLayout.findViewById<EditText>(R.id.et_editText);

        val args = this.arguments


        print("")
        print("")
        print("")

/*
        val myObj = File("userdata.txt")
        myObj.appendText(cipherText);
        if (myObj.createNewFile()) {
            println("File created: " + myObj.name);
        } else {
            println("File already exists.");
        }

        val algorithm = "AES/CBC/PKCS5Padding";
        val key = SecretKeySpec(user?.uid.toString().toByteArray(), "AES");
        val iv = IvParameterSpec(ByteArray(16));

        val plainText = decrypt(algorithm, cipherText, key, iv);
        with(builder) {
            setTitle("Enter some text!")
            setPositiveButton("Ok") { dialog, which ->
                var toast = Toast.makeText(context, editText.text.toString(), Toast.LENGTH_SHORT)
                toast.setMargin(50f, 50f)
                toast.show()
            }

            setNegativeButton("Cancel") { dialog, which ->
                var toast = Toast.makeText(context, "NO ACEPTADO", Toast.LENGTH_SHORT)
                toast.setMargin(50f, 50f)
                toast.show()
            }

            setView(dialogLayout);
            show();
        }
        */

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