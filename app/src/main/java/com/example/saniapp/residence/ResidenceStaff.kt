package com.example.saniapp.residence

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.example.saniapp.admin.findResidenceAll
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private fun showStaff(nal: NavController, layout: LinearLayout) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                Thread.sleep(500);
                var stafflist = findStaffAllByIdResidence(Firebase.auth.currentUser?.uid.toString());
                var aux = "hola"
                var aux2 = stafflist
                if(aux2 != null){
                    withContext(Dispatchers.Main) {
                        aux = "adios"
                        var aux3 = aux2
                        var aux6 = aux2?.get(0)
                        var it = aux2?.iterator()
                        while (it?.hasNext() == true) {
                            var staff = it?.next();
                            (staff?.id);
                            ;

                            var btn_staff = Button(context);
                            btn_staff.setText(staff?.name);
                            btn_staff.setTextColor(Color.WHITE);
                            btn_staff.setBackgroundColor(Color.rgb(98, 0, 238));
                            btn_staff.setOnClickListener {
                                val bundle = Bundle()
                                var argdata = arrayOf(staff?.id);
                                bundle.putSerializable("argdata", argdata);
                                nal.navigate(R.id.residenceStaffProfile, bundle);
                            }
                            layout.addView(btn_staff);
                        }
                    }
                    print(aux);
                }
                var aux9 = aux2;
            } catch (e: Exception) {
                showStaff(nal, layout);
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_residence_staff) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments;
        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var residenceemail = inputData[1];
        var residencepass = inputData[2];

        showStaff(nal, layout);

        var btn_residence_staff_create = Button(context);
        btn_residence_staff_create.setText("Crear");
        btn_residence_staff_create.setTextColor(Color.WHITE);
        btn_residence_staff_create.setBackgroundColor(Color.RED);
        btn_residence_staff_create.setOnClickListener{
            val bundle = Bundle();
            var argdata = arrayOf(user?.uid.toString(), residenceemail, residencepass);
            bundle.putSerializable("argdata", argdata);
            nal.navigate(R.id.residenceCreateStaff, bundle);
        }
        layout.addView(btn_residence_staff_create);
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