package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore


class Anamnese : AppCompatActivity() {

    private lateinit var anamSaveButton: Button
    private lateinit var anamInput: EditText

    val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anamnese)

        loadAnam()


        anamSaveButton = findViewById(R.id.btnAnamSave)
        anamInput = findViewById(R.id.anamInput)

        anamSaveButton.setOnClickListener {
            val anamInput2 = anamInput.text.toString()
            val idosoRef = db.collection("idoso").document("${MainActivity.GlobalData.ultEmail}")
            val newAnam = mapOf(
                "anamnese" to anamInput2
            )
            idosoRef.update(newAnam)

            startActivity(Intent(this, menuIdoso::class.java))
            finish()

        }
    }


    private fun loadAnam() {


        val idosoRef = db.collection("idoso").document("${MainActivity.GlobalData.ultEmail}")
        idosoRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val anam = documentSnapshot.getString("anamnese")
                anamInput.setText(anam)
            }
    }
}


