package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore


class Registro : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var trueRegisBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)


            binding = ActivityRegistroBinding.inflate(layoutInflater)
            setContentView(binding.root)

            trueRegisBtn = findViewById(R.id.registrue_btn)

            firebaseAuth = FirebaseAuth.getInstance()

            trueRegisBtn.setOnClickListener {

                val email = binding.emailInput.text.toString()
                val pass = binding.passwordInput.text.toString()
                val name = binding.nameInput.text.toString()
                val address = binding.addInput.text.toString()
                val phone = binding.addInput.text.toString()
                val db = Firebase.firestore


                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val idoso = mapOf(
                                "email" to email,
                                "senha" to pass,
                                "nome idoso" to name,
                                "endereço" to address,
                                "telefone" to phone,
                                "flag cuidador" to false)
                            val idosoRef = db.collection("idoso")
                            idosoRef.document(email).set(idoso)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }