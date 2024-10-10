package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class PedidoIdoso : AppCompatActivity() {

    val firestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_idoso)



        val linkCollectionRef = firestore.collection("pedidos")

        val lista = arrayOf<String>()
        val mutableList = lista.toMutableList()
        linkCollectionRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    if (document.contains("email do idoso")) {
                        val nombreCriador = document.getString("email do idoso")
                        if (nombreCriador == MainActivity.GlobalData.ultEmail) {
                            val nombre = document.getString("email do cuidador")
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

                    MainActivity.GlobalData.ultCuidName = trueLista[position]

                    startActivity(Intent(this, PedidoAcpt::class.java))
                    finish()

                }
            }
    }
}
