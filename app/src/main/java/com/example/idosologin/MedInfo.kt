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
import com.example.idosologin.databinding.ActivityMedInfoBinding
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
    private lateinit var binding: ActivityMedInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_med_info)

        binding = ActivityMedInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

            val medDoseX = binding.dosageInput.text.toString()
            val medDurX = binding.durationInput.text.toString()
            val medInfoX = binding.infoInput.text.toString()

            val medicamento = mapOf(
                "Dosagem" to medDoseX,
                "Duração" to medDurX,
                "Informação add" to medInfoX)
            val medRef = firestore.collection("medicamento")
            medRef.document("${MainActivity.GlobalData.ultMedName}").update(medicamento)

            val intent = Intent(this, Medicamentos::class.java)
            startActivity(intent)

        }
    }

    private fun loadMedName() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Nome")) {
                        val mdNome = document.get("Nome")
                        if(mdNome == MainActivity.GlobalData.ultMedName) {
                            medName.text = MainActivity.GlobalData.ultMedName.toString()
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
                    if (document.contains("Nome")) {
                        val mdName = document.get("Nome")
                        if (mdName == MainActivity.GlobalData.ultMedName) {
                            val mdDose = document.getString("Dosagem")
                            medDose.setText(mdDose)
                        }
                    }
                }
            }
    }

    private fun loadMedDur() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Nome")) {
                        val mdName = document.get("Nome")
                        if (mdName == MainActivity.GlobalData.ultMedName) {
                            val mdDur = document.getString("Duração")
                            medDur.setText(mdDur)
                        }
                    }
                }
            }
    }

    private fun loadMedInfo() {

        val medCollectionRef = firestore.collection("medicamento")
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Nome")) {
                        val mdName = document.get("Nome")
                        if (mdName == MainActivity.GlobalData.ultMedName) {
                            val mdInfo = document.getString("Informação add")
                            medInfo.setText(mdInfo)
                        }
                    }
                }
            }
    }
}