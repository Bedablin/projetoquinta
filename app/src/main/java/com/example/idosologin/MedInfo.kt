package com.example.idosologin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore


    class MedInfo : AppCompatActivity() {


        val firestore = FirebaseFirestore.getInstance()
        private lateinit var medName: EditText
        private lateinit var medDose: EditText
        private lateinit var medDur: EditText
        private lateinit var medInfo: EditText
        private lateinit var medSave: Button
        private lateinit var medDel: Button


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_medinfo)



            medName = findViewById(R.id.mednameshow)
            medDose = findViewById(R.id.dosage_input)
            medDur = findViewById(R.id.duration_input)
            medInfo = findViewById(R.id.info_input)
            medSave = findViewById(R.id.medsave_btn)
            medDel = findViewById(R.id.meddel_btn)

            //loadMedName()
        }

        private fun loadMedName() {


            val medCollectionRef = firestore.collection("medicamento")
            medCollectionRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        if (document.contains("Nome")) {
                            val mdNome = document.getString("Nome")
                            medName.setText(mdNome)
                        }
                    }
                }
        }

        private fun loadMedDose() {


            val idosoRef = firestore.collection("idoso").document("${MainActivity.GlobalData.ultEmail}")
            idosoRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val anam = documentSnapshot.getString("anamnese")
                    medDose.setText(anam)
                }
        }

        private fun loadMedDur() {


            val idosoRef = firestore.collection("idoso").document("${MainActivity.GlobalData.ultEmail}")
            idosoRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val anam = documentSnapshot.getString("anamnese")
                    medDose.setText(anam)
                }
        }

        private fun loadMedInfo() {


            val idosoRef = firestore.collection("idoso").document("${MainActivity.GlobalData.ultEmail}")
            idosoRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    val anam = documentSnapshot.getString("anamnese")
                    medDose.setText(anam)
                }
        }
    }