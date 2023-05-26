package com.example.saniapp.residence

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.admin.findResidenceAll
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResidenceResidents.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResidenceResidents : Fragment() {
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
        return inflater.inflate(R.layout.fragment_residence_residents, container, false)
    }

    private fun showInfo(id_user: String, layout: LinearLayout, nal: NavController) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                var residentlist = findResidentAll();
                ;
                ;
                var aux = "hola"
                Thread.sleep(500);
                var aux2 = residentlist
                if(aux2 != null){
                    var aux9 = aux2
                    ;

                    withContext(Dispatchers.Main) {
                        
                        
                        (aux);
                        aux = "adios"
                        var aux3 = aux2
                        var aux6 = aux2?.get(0)
                        var it = aux2?.iterator()
                        while (it?.hasNext() == true) {
                            var resident = it?.next();
                            (resident?.id);
                            ;

                            var btn_resident = Button(context);
                            btn_resident.setText(resident?.name);
                            btn_resident.setTextColor(Color.WHITE);
                            btn_resident.setBackgroundColor(Color.rgb(98, 0, 238));
                            btn_resident.setOnClickListener {
                                val bundle = Bundle();
                                var argdata = resident?.id;
                                bundle.putSerializable("argdata", argdata);
                                nal.navigate(R.id.residenceResidentsProfile, bundle);
                            }
                            layout.addView(btn_resident);
                        }
                        
                        
                        
                        
                        
                        
                    }
                }

                print(aux);

                ("");
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showInfo(Firebase.auth.currentUser?.uid.toString(), layout, nal);
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_residence_residents) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        var btn_residence_resident_create = Button(context);
        btn_residence_resident_create.setText("Crear");
        btn_residence_resident_create.setTextColor(Color.WHITE);
        btn_residence_resident_create.setBackgroundColor(Color.RED);
        btn_residence_resident_create.setOnClickListener{
            val bundle = Bundle();
            nal.navigate(R.id.residenceCreateResident, bundle);
        }
        layout.addView(btn_residence_resident_create);

        showInfo(user?.uid.toString(), layout, nal);

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResidenceResidents.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResidenceResidents().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}