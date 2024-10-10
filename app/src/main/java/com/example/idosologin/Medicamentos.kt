package com.example.idosologin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.idosologin.databinding.ActivityRegistroMedBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.time.Duration

class Medicamentos : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicamentos)



        val medCollectionRef = firestore.collection("medicamento")

        val lista = arrayOf<String>()
        val mutableList = lista.toMutableList()
        medCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("Criador do medicamento")) {
                        val nombreCriador = document.getString("Criador do medicamento")
                        if (nombreCriador == MainActivity.GlobalData.ultEmail){
                            val nombre = document.getString("Nome")
                            mutableList.add("$nombre")
                        }
                    }
                }


                val listView: ListView = findViewById(R.id.listView)

                val trueLista = mutableList.toTypedArray()


                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trueLista)


                listView.adapter = adapter

                // quando clica no medicamento específico
                listView.setOnItemClickListener { parent, view, position, id ->


                    MainActivity.GlobalData.ultMedName = trueLista[position]

                    startActivity(Intent(this, MedInfo::class.java))
                    finish()



                }
            }
    }
}
