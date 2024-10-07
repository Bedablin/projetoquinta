package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityLinkPedidoBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore


class linkPedido : AppCompatActivity() {

    private lateinit var binding: ActivityLinkPedidoBinding
    private lateinit var linkBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_link_pedido)


        binding = ActivityLinkPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        linkBtn = findViewById(R.id.registrue_btn)



        linkBtn.setOnClickListener {

            val email = binding.emailInput.text.toString()
            val db = Firebase.firestore


            if (email.isNotEmpty()) {
                        val pedido = mapOf(
                            "email do cuidador" to MainActivity.GlobalData.ultEmail,
                            "email do idoso" to email)
                        val pedidoRef = db.collection("pedidos")
                        pedidoRef.document().set(pedido)
                        //colocar nome do documento desejado dentro de document(''Email'')

                        val intent = Intent(this, menuCuidador::class.java)
                        startActivity(intent)

            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }


        }
    }
}