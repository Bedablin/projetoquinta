package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    object GlobalData {
        var ultEmail: String? = null
        var ultName: String? = null
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var regisBtn: Button
    private lateinit var forgotPassword: TextView
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)
        regisBtn = findViewById(R.id.regis_btn)
        forgotPassword = findViewById(R.id.forgot_password)


        loginBtn.setOnClickListener {
            Log.d("Login", "Botão de login clicado")
            val email = emailInput.text.toString()
            val pass = passwordInput.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                Log.d("Login", "Email e senha preenchidos")
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        GlobalData.ultEmail = email
                        Log.d("Login", "Login bem-sucedido")
                        Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()

                        val flagRef = db.collection("cuidador").document("$email")
                        flagRef.get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    if (document.contains("flag cuidador")) {
                                        val fieldValue = document.getBoolean("flag cuidador")
                                        if (fieldValue == true) {
                                            val nameRef =
                                                db.collection("cuidador").document("$email")
                                            nameRef.get()
                                                .addOnSuccessListener { document ->
                                                    if (document.exists()) {
                                                        if (document.contains("nome cuidador")) {
                                                            GlobalData.ultName =
                                                                document.getString("nome cuidador")


                                                        }
                                                    }
                                                }


                                            val intent = Intent(this, menuCuidador::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                }
                            }
                        val flagRef2 = db.collection("idoso").document("$email")
                        flagRef2.get()
                            .addOnSuccessListener { document ->
                                if (document.exists()) {
                                    if (document.contains("flag cuidador")) {
                                        val fieldValue = document.getBoolean("flag cuidador")
                                        if (fieldValue == false) {
                                            val intent = Intent(this, menuIdoso::class.java)
                                            startActivity(intent)
                                            finish()
                                        }


                                    }
                                }
                        }





                    }else {
                        Log.d("Login", "Campos de email ou senha vazios")
                        Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                    }
        }
            }
        }


        regisBtn.setOnClickListener {
            Log.d("Registro", "Botão de registro clicado")
            val intent = Intent(this, RegisChoice::class.java)
            startActivity(intent)
        }


        forgotPassword.setOnClickListener {
            val email = emailInput.text.toString()

            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email de recuperação enviado para $email", Toast.LENGTH_SHORT).show()
                        Log.d("ForgotPassword", "Email de recuperação enviado para $email")
                    } else {
                        Toast.makeText(this, "Erro ao enviar email de recuperação: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        Log.e("ForgotPassword", "Erro ao enviar email de recuperação: ${task.exception?.message}")
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, insira seu email para recuperar a senha", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
