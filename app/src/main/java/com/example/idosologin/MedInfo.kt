package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityRegistroMedBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


class MedInfo : AppCompatActivity() {


    val firestore = FirebaseFirestore.getInstance()
    private lateinit var medName: TextView
    private lateinit var medDose: EditText
    private lateinit var medDur: EditText
    private lateinit var medInfo: EditText
    private lateinit var medSave: Button
    private lateinit var medDel: Button
    private lateinit var binding: ActivityRegistroMedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_info)

        val selectedItem = intent.getStringExtra("SELECTED_ITEM")


        binding = ActivityRegistroMedBinding.inflate(layoutInflater)

        medName = findViewById(R.id.mednameshow)
        medDose = findViewById(R.id.dosage_input)
        medDur = findViewById(R.id.duration_input)
        medInfo = findViewById(R.id.info_input)
        medSave = findViewById(R.id.medsave_btn)
        medDel = findViewById(R.id.meddel_btn)

        loadMedName()
        loadMedDose()
        loadMedDur()
        loadMedInfo()

        medSave.setOnClickListener {

            val medicamento = mapOf(
                "Nome" to medName,
                "Dosagem" to medDose,
                "Duração" to medDur,
                "Informação add" to medInfo)
            val medRef = firestore.collection("medicamento")
            medRef.document("$medName").update(medicamento)

            val intent = Intent(this, Medicamentos::class.java)
            startActivity(intent)

        }
    }

    private fun loadMedName() {

        val selectedItem = intent.getStringExtra("SELECTED_ITEM")
        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Nome")) {
                        val mdNome = document.get("Nome")
                        if(mdNome == selectedItem) {
                            medName.setText(selectedItem)
                        }
                    }
                }
            }
    }

    private fun loadMedDose() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Dosagem")) {
                        val mdDose = document.getString("Dosagem")
                        medDose.setText(mdDose)
                    }
                }
            }
    }

    private fun loadMedDur() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Duração")) {
                        val mdDur = document.getString("Duração")
                        medDur.setText(mdDur)
                    }
                }
            }
    }

    private fun loadMedInfo() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Informação add")) {
                        val mdInfo = document.getString("Informação add")
                        medInfo.setText(mdInfo)
                    }
                }
            }
    }
}