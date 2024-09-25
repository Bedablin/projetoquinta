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
                    val nombre = document.getString("Nome")
                    mutableList.add("$nombre")
                }



                val listView: ListView = findViewById(R.id.listView)

                val trueLista = mutableList.toTypedArray()

                // Create an ArrayAdapter using the string array and a default ListView layout
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trueLista)

                // Attach the adapter to the ListView
                listView.adapter = adapter

                // Handle item clicks
                listView.setOnItemClickListener { parent, view, position, id ->
                    // Get the selected item
                    val selectedItem = trueLista[position]

                    // Show a Toast message
                    Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
