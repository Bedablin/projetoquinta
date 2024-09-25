package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityRegistroMedBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore


class registroMed : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroMedBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var medRegisBtn: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_med)


        binding = ActivityRegistroMedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        medRegisBtn = findViewById(R.id.medregis_btn)

        firebaseAuth = FirebaseAuth.getInstance()

        medRegisBtn.setOnClickListener {

            val medName = binding.medInput.text.toString()
            val dosage = binding.dosageInput.text.toString()
            val duration = binding.durationInput.text.toString()
            val info = binding.infoInput.text.toString()
            val creator = MainActivity.GlobalData.ultName
            val db = Firebase.firestore


            val medicamento = mapOf(
                "Nome" to medName,
                "Dosagem" to dosage,
                "Duração" to duration,
                "Informação add" to info,
                "Criador do medicamento" to creator)
            val medRef = db.collection("medicamento")
            medRef.document("$medName").set(medicamento)

            val intent = Intent(this, menuCuidador::class.java)
            startActivity(intent)

                }
            }


        }

