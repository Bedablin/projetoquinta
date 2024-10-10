package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class PacInfo : AppCompatActivity() {

    private lateinit var userEmailEditText: TextView
    private lateinit var anamButton: Button
    private lateinit var medButton: Button

    val db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pac_info)


        userEmailEditText = findViewById(R.id.etUserEmail2)
        anamButton = findViewById(R.id.btnAnam)
        medButton = findViewById(R.id.Medbtn2)



        loadUserInfo()


        anamButton.setOnClickListener {
            startActivity(Intent(this, Anamnese::class.java))
            finish()
        }

        medButton.setOnClickListener {
            startActivity(Intent(this, Medicamentos::class.java))
        }


    }

    private fun loadUserInfo() {

        val email = MainActivity.GlobalData.ultEmail
        userEmailEditText.setText(email)

    }
}

