package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.idosologin.databinding.ActivityRegistroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.firestore


class RegisChoice : AppCompatActivity() {

    private lateinit var oldButton: Button
    private lateinit var helpButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regis_choice)

        oldButton = findViewById(R.id.old_btn)
        helpButton = findViewById(R.id.helper_btn)

        oldButton.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }

        helpButton.setOnClickListener {
            startActivity(Intent(this, Registro_help::class.java))
        }
    }
}


