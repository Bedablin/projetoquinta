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

class Pedidos : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)



        val linkCollectionRef = firestore.collection("pedidos")

        val lista = arrayOf<String>()
        val mutableList = lista.toMutableList()
        linkCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("email do cuidador")) {
                        val nombreCriador = document.getString("email do cuidador")
                        if (nombreCriador == MainActivity.GlobalData.ultEmail) {
                            val nombre = document.getString("email do idoso")
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

                }
            }
    }
}
