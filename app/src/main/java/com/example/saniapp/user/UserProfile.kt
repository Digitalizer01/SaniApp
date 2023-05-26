package com.example.saniapp.user

import android.app.AlertDialog
import android.graphics.Color
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
import com.example.saniapp.R
import com.example.saniapp.residence.Residence
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

    private fun showStaffInfo() {
        GlobalScope.launch(Dispatchers.IO) {
            var staff: Staff = Staff(
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            while (staff.name == null) {
                staff =
                    findStaffById(FirebaseAuth.getInstance().currentUser?.uid.toString())!!
                Thread.sleep(100);
            }

            var text_name = view?.findViewById(R.id.text_user_name) as? TextView;
            var text_surnames = view?.findViewById(R.id.text_user_surnames) as? TextView;
            var text_gender = view?.findViewById(R.id.text_user_gender) as? TextView;
            var text_birthdate = view?.findViewById(R.id.text_user_birthdate) as? TextView;
            var text_email = view?.findViewById(R.id.text_user_email) as? TextView;
            var text_phone = view?.findViewById(R.id.text_user_phone) as? TextView;
            var text_id = view?.findViewById(R.id.text_user_id) as? TextView;

            text_name?.setText("Nombre: " + staff.name);
            text_surnames?.setText("Apellidos: " + staff.surnames);
            if (staff.gender == "Male") {
                text_gender?.setText("Género: Hombre");
            } else {
                text_gender?.setText("Género: Mujer");
            }
            text_birthdate?.setText("Fecha nacimiento: " + staff.birthdate);
            text_email?.setText("Email: " + staff.email);
            text_phone?.setText("Móvil: " + staff.phone);
            text_id?.setText("ID: " + Firebase.auth.currentUser?.uid.toString());
        }
    }

    private fun showResidenceInfo() {
        GlobalScope.launch(Dispatchers.IO) {
            var residence: Residence = Residence(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            );

            while (residence.name == null) {
                residence =
                    findResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString())!!
                Thread.sleep(100);
            }

            var text_residence_name =
                view?.findViewById(R.id.text_user_residence_name) as? TextView;
            var text_residence_country =
                view?.findViewById(R.id.text_user_residence_country) as? TextView;
            var text_residence_city =
                view?.findViewById(R.id.text_user_residence_city) as? TextView;
            var text_residence_provience =
                view?.findViewById(R.id.text_user_residence_provience) as? TextView;
            var text_residence_street =
                view?.findViewById(R.id.text_user_residence_street) as? TextView;
            var text_residence_zc =
                view?.findViewById(R.id.text_user_residence_zc) as? TextView;
            var text_residence_phone =
                view?.findViewById(R.id.text_user_residence_phone) as? TextView;
            var text_residence_email =
                view?.findViewById(R.id.text_user_residence_email) as? TextView;
            var text_residence_timetable =
                view?.findViewById(R.id.text_user_residence_timetable) as? TextView;

            text_residence_name?.setText("Nombre: " + residence.name);
            text_residence_country?.setText("País: " + residence.country);
            text_residence_city?.setText("Ciudad: " + residence.city);
            text_residence_provience?.setText("Provincia: " + residence.province);
            text_residence_street?.setText("Calle: " + residence.street);
            text_residence_zc?.setText("CP: " + residence.zc);
            text_residence_phone?.setText("Teléfono: " + residence.phone);
            text_residence_email?.setText("Email: " + residence.email);
            text_residence_timetable?.setText("Horario: " + residence.timetable);

        }
    }

    private fun showResidents(layout: LinearLayout, nal: NavController) {
        print("");
        print("");
        print("");
        print("");
        print("");
        print("");
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Thread.sleep(250);
                var residence: Residence = Residence(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "aCIqITDyZPTzD5NMdRpA4t9InLt1"
                );

                /*while (residence.name == null) {
                    residence =
                        findResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString())!!
                    Thread.sleep(100);
                }*/
                Thread.sleep(250);
                var residentlist = findResidentAllByIdResidence(residence!!.id.toString());
                var aux2 = residentlist
                if (aux2 != null) {
                    var aux9 = aux2
                    ;

                    withContext(Dispatchers.Main) {
                        var aux3 = aux2
                        var aux6 = aux2?.get(0)
                        var it = aux2?.iterator()
                        while (it?.hasNext() == true) {
                            var resident = it?.next();
                            (resident?.id);
                            Thread.sleep(500);
                            var residence =
                                    findResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString())!!
                                Thread.sleep(100);


                            var btn_resident = Button(context);
                            btn_resident.setText(resident?.name);
                            btn_resident.setTextColor(Color.WHITE);

                            if (resident?.alert == true) {
                                btn_resident.setBackgroundColor(Color.RED);
                            } else {
                                btn_resident.setBackgroundColor(Color.rgb(98, 0, 238));
                            }

                            btn_resident.setOnClickListener {
                                val bundle = Bundle();
                                var argdata = arrayOf(residence?.id, resident);
                                bundle.putSerializable("argdata", argdata);
                                nal.navigate(R.id.userResident, bundle);
                            }
                            layout.addView(btn_resident);
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showResidents(layout, nal);
                }
            }
        }
        print("");
        print("");
        print("");
        print("");
        print("");
        print("");
    }

    private fun deleteStaff(nal: NavController) {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                var idResidence =
                    findIdResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString());
                while (idResidence == null) {
                    idResidence =
                        findIdResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString());
                }
                var residentlist = findResidentAllByIdResidence(idResidence);
                var aux2 = residentlist
                if (aux2 != null) {
                    var aux9 = aux2
                    ;

                    withContext(Dispatchers.Main) {
                        var aux3 = aux2
                        var aux6 = aux2?.get(0)
                        var it = aux2?.iterator()
                        while (it?.hasNext() == true) {
                            var resident = it?.next();
                            (resident?.id);
                            var residence: Residence = Residence(
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null
                            );

                            while (residence.name == null) {
                                residence =
                                    findResidenceByIdStaff(FirebaseAuth.getInstance().currentUser?.uid.toString())!!
                                Thread.sleep(100);
                            }

                            var btn_delete =
                                view?.findViewById(R.id.button_user_delete) as Button;

                            btn_delete.setOnClickListener {

                                val builder = AlertDialog.Builder(context);
                                val inflater: LayoutInflater = layoutInflater;
                                val dialogLayout: View =
                                    inflater.inflate(R.layout.fragment_dialog_confirm, null);

                                with(builder) {
                                    setTitle("¿Está seguro de que desea borrar su cuenta?")
                                    setPositiveButton("Ok") { dialog, which ->
                                        try {
                                            deleteStaffByIdResidenceAndIdStaff(
                                                residence.id.toString(),
                                                Firebase.auth.currentUser?.uid.toString()
                                            );
                                            var toast =
                                                Toast.makeText(
                                                    context,
                                                    "Borrado. Se reinicará la aplicación.",
                                                    Toast.LENGTH_SHORT
                                                );
                                            toast.show();
                                            Thread.sleep(2000);
                                            System.exit(0);
                                        } catch (e: Exception) {
                                            var toast = Toast.makeText(
                                                context,
                                                "Borrado cancelado",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.setMargin(50f, 50f)
                                            toast.show()
                                        }
                                    }

                                    setNegativeButton("Cancelar") { dialog, which ->
                                        var toast = Toast.makeText(
                                            context,
                                            "NO ACEPTADO",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.setMargin(50f, 50f)
                                        toast.show()
                                    }

                                    setView(dialogLayout);
                                    show();
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    deleteStaff(nal);
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val nal = Navigation.findNavController(view)
        var layout = view?.findViewById(R.id.id_linearlayout_user) as LinearLayout

        val user = FirebaseAuth.getInstance().currentUser
        showStaffInfo();
        showResidenceInfo();
        showResidents(layout as LinearLayout, nal as NavController);
        deleteStaff(nal);
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