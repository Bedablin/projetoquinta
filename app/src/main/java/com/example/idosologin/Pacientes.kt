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

class Pacientes : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacientes)

        var currentcuid = MainActivity.GlobalData.ultEmail

        val medCollectionRef = firestore.collection("cuidador").document("$currentcuid")

        val lista = arrayOf<String>()
        val mutableList = lista.toMutableList()
        medCollectionRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {

                    if (document.contains("Pacientes")) {
                        val nombre = document.getString("Pacientes")
                        mutableList.add("$nombre")

                    }
                }

                val listView: ListView = findViewById(R.id.listView)

                val trueLista = mutableList.toTypedArray()


                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trueLista)


                listView.adapter = adapter

                // quando clica no medicamento específico
                listView.setOnItemClickListener { parent, view, position, id ->


                    MainActivity.GlobalData.ultEmail = trueLista[position]
                    MainActivity.GlobalData.ultPacName = trueLista[position]

                    startActivity(Intent(this, PacInfo::class.java))
                    finish()



                }
            }
    }
}
