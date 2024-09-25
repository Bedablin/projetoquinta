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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.time.Duration

class Medicamentos : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()
    lateinit var medName: EditText
    lateinit var medDose: EditText
    lateinit var medDur:EditText
    lateinit var medInfo: EditText
    lateinit var medSave: Button
    lateinit var medDel: Button

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
                        val nombreCriador = document.getString(("Criador do medicamento"))
                        if (nombreCriador == MainActivity.GlobalData.ultName) {
                            val nombre = document.getString("Nome")
                            mutableList.add("$nombre")
                        }
                    }
                }


                val listView: ListView = findViewById(R.id.listView)

                val trueLista = mutableList.toTypedArray()

                // Create an ArrayAdapter using the string array and a default ListView layout
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trueLista)

                // Attach the adapter to the ListView
                listView.adapter = adapter

                // Handle item clicks
                listView.setOnItemClickListener { parent, view, position, id ->
                    val selectedItem = trueLista[position]
                    setContentView(R.layout.activity_medinfo)

                    medName = findViewById(R.id.mednameshow)



                    Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
