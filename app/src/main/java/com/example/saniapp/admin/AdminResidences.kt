package com.example.saniapp.admin

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.saniapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.util.HashMap
import kotlin.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AdminResidences.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminResidences : Fragment() {
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
        return inflater.inflate(R.layout.fragment_admin_residences, container, false)
    }

    private fun findResidences(layout: LinearLayout, nal: NavController) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                var residencelist = findResidenceAll();
                ;
                ;
                var aux = "hola"
                var aux2 = residencelist
                Thread.sleep(500);
                var aux9 = aux2
                ;

                withContext(Dispatchers.Main) {
                    var aux3 = aux2
                    try {
                        var aux6 = aux2?.get(0)
                        var it = aux2?.iterator()
                        while (it?.hasNext() == true) {
                            var residence = it?.next();
                            (residence?.id);
                            ;

                            var btn_residence = Button(context);
                            btn_residence.setText(residence?.name);
                            btn_residence.setTextColor(Color.WHITE);
                            btn_residence.setBackgroundColor(Color.rgb(98, 0, 238));
                            btn_residence.setOnClickListener {
                                val bundle = Bundle();
                                var argdata = residence?.id;
                                bundle.putSerializable("argdata", argdata);
                                nal.navigate(R.id.adminResidenceProfile, bundle);
                            }
                            layout.addView(btn_residence);
                        }
                    }
                    catch (e: Exception){

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {

                    findResidences(layout, nal)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_admin_residences) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser

        val args = this.arguments

        val inputData: Array<*> = args?.getSerializable("argdata") as Array<*>;
        var adminid = inputData[0];
        var adminemail = inputData[1];
        var adminpass = inputData[2];
        findResidences(layout, nal);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminResidences.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminResidences().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}