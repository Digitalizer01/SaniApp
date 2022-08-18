package com.example.saniapp.residence

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
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
import java.io.File
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResidenceProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceProfile : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_profile, container, false)
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
                var residence: HashMap<String, String> = HashMap<String, String>();
                while (iterator.hasNext()) {
                    var hashmap = iterator.next();
                    if (hashmap.key.toString() == "Data")
                        residence = hashmap.value as HashMap<String, String>;

                    print("Done");
                    print("New")
                }

                var text_residence_title = view?.findViewById(R.id.text_residence_title) as? TextView;
                var text_residence_name = view?.findViewById(R.id.text_residence_name) as? TextView;
                var text_residence_country = view?.findViewById(R.id.text_residence_country) as? TextView;
                var text_residence_city = view?.findViewById(R.id.text_residence_city) as? TextView;
                var text_residence_provience = view?.findViewById(R.id.text_residence_provience) as? TextView;
                var text_residence_street = view?.findViewById(R.id.text_residence_street) as? TextView;
                var text_residence_zc = view?.findViewById(R.id.text_residence_zc) as? TextView;
                var text_residence_phone = view?.findViewById(R.id.text_residence_phone) as? TextView;
                var text_residence_email = view?.findViewById(R.id.text_residence_email) as? TextView;
                var text_residence_timetable = view?.findViewById(R.id.text_residence_timetable) as? TextView;

                text_residence_title?.setText(residence["Name"]);
                text_residence_name?.setText("Nombre: " + residence["Name"]);
                text_residence_country?.setText("País: " + residence["Country"]);
                text_residence_city?.setText("Ciudad: " + residence["City"]);
                text_residence_provience?.setText("Provincia: " + residence["Province"]);
                text_residence_street?.setText("Calle: " + residence["Street"]);
                text_residence_zc?.setText("CP: " + residence["ZC"]);
                text_residence_phone?.setText("Teléfono: " + residence["Phone"]);
                text_residence_email?.setText("Email: " + residence["Email"]);
                text_residence_timetable?.setText("Horario: " + residence["Timetable"]);

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);
        val nal = Navigation.findNavController(view);
        var layout = view?.findViewById(R.id.id_linearlayout_residence) as LinearLayout;

        val user = FirebaseAuth.getInstance().currentUser

        val algorithm = "AES/CBC/PKCS5Padding";
        val key = SecretKeySpec("1234567890123456".toByteArray(), "AES");
        val iv = IvParameterSpec(ByteArray(16));
        var cipherText = "";

        val path = context?.filesDir?.absolutePath
        var file = File("$path/").walk().find { it.name == "residencedata.txt"}

        cipherText = file!!.readText()
        val plainText = decrypt(algorithm, cipherText, key, iv);

        println(plainText)
        val parts = plainText.split("\n");
        var residenceemail = parts[0];
        var residencepassword = parts[1];

        showInfo(user?.uid.toString(), layout, nal);

        var btn_residence_staff = view?.findViewById(R.id.button_residence_staff) as Button;
        btn_residence_staff.setOnClickListener{
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString(), residenceemail, residencepassword);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.residenceStaff, bundle);
        }

        var btn_residence_residents = view?.findViewById(R.id.button_residence_residents) as Button;
        btn_residence_residents.setOnClickListener{
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString(), residenceemail, residencepassword);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.residenceResidents, bundle);
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResidenceProfile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}