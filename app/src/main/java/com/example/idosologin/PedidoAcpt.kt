package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityLinkPedidoBinding
import com.example.idosologin.databinding.ActivityPedidoAcptBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore


class PedidoAcpt : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoAcptBinding
    private lateinit var AcceptBtn: Button
    private lateinit var DeclineBtn: Button
    private lateinit var userEmail: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pedido_acpt)


        binding = ActivityPedidoAcptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AcceptBtn = findViewById(R.id.acceptBtn)
        DeclineBtn = findViewById(R.id.declineBtn)
        userEmail = findViewById(R.id.emailview)


        loadUserInfo()



        AcceptBtn.setOnClickListener {

            val email = MainActivity.GlobalData.ultEmail.toString()
            val cuidemail = MainActivity.GlobalData.ultCuidName.toString()
            val db = Firebase.firestore


            val idoso = mapOf(
                "cuidador responsável" to MainActivity.GlobalData.ultCuidName
                )
            val pedidoRef = db.collection("idoso")
            pedidoRef.document(email).update(idoso)
            //colocar nome do documento desejado dentro de document(''Email'')

            val cuidador = mapOf(
                "Pacientes" to MainActivity.GlobalData.ultEmail
            )
            val pedidoCRef = db.collection("cuidador")
            pedidoCRef.document(cuidemail).update(cuidador)



            val pedCollectionRef = db.collection("pedidos")
            pedCollectionRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.contains("email do idoso")) {
                            val mdNome = document.getString("email do cuidador")
                            if(mdNome == MainActivity.GlobalData.ultCuidName.toString()) {
                                pedCollectionRef.document(document.id).delete()

                            }
                        }
                    }
                }

            val intent = Intent(this, menuIdoso::class.java)
            startActivity(intent)

        }

        DeclineBtn.setOnClickListener {
            val db = Firebase.firestore


            val pedCollectionRef = db.collection("pedidos")
            pedCollectionRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.contains("email do idoso")) {
                            val mdNome = document.getString("email do cuidador")
                            if(mdNome == MainActivity.GlobalData.ultCuidName.toString()) {
                                pedCollectionRef.document(document.id).delete()

                            }
                        }
                    }
                }


            val intent = Intent(this, menuIdoso::class.java)
            startActivity(intent)
        }
    }

    private fun loadUserInfo() {
        userEmail.text = MainActivity.GlobalData.ultCuidName.toString()

    }


}