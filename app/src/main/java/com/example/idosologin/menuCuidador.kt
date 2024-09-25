package com.example.idosologin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.idosologin.R.id.regMedbtn2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore



class menuCuidador : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userEmailEditText: EditText
    private lateinit var updateEmailButton: Button
    private lateinit var deleteAccountButton: Button
    private lateinit var logoutButton: Button
    private lateinit var medRegButton: Button
    private lateinit var medButton: Button

    val db = FirebaseFirestore.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_cuidador)

        firebaseAuth = FirebaseAuth.getInstance()

        userEmailEditText = findViewById(R.id.etUserEmail)
        updateEmailButton = findViewById(R.id.btnUpdateEmail)
        deleteAccountButton = findViewById(R.id.btnDeleteAccount)
        logoutButton = findViewById(R.id.btnLogout)
        medRegButton = findViewById(regMedbtn2)
        medButton = findViewById(R.id.Medbtn2)



        loadUserInfo()



        updateEmailButton.setOnClickListener {
            updateUserEmail()
        }


        deleteAccountButton.setOnClickListener {
            // Create an AlertDialog Builder
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmação")
            builder.setMessage("Tem certeza que quer deletar a conta?")

            // Set positive button (Yes)
            builder.setPositiveButton("Sim") { dialog, _ ->
                deleteAccount()
                db.collection("cuidador").document("${MainActivity.GlobalData.ultEmail}")
                    .delete()
                dialog.dismiss()
            }

            // Set negative button (No)
            builder.setNegativeButton("Não") { dialog, _ ->
                // Do nothing, just dismiss the dialog
                dialog.dismiss()
            }

            // Show the dialog
            builder.show()
        }


        logoutButton.setOnClickListener {
            Log.d("Logout", "Usuário deslogado")
            firebaseAuth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


        medButton.setOnClickListener {
            startActivity(Intent(this, Medicamentos::class.java))
        }

        medRegButton.setOnClickListener {
            startActivity(Intent(this, registroMed::class.java))

        }
    }

    private fun loadUserInfo() {
        val user: FirebaseUser? = firebaseAuth.currentUser

        user?.let {
            val email = it.email
            userEmailEditText.setText(email)
            Log.d("UserInfo", "Email carregado: $email")
        } ?: run {
            Toast.makeText(this, "Nenhum usuário autenticado", Toast.LENGTH_SHORT).show()
            Log.e("UserInfo", "Nenhum usuário autenticado")
            finish()
        }
    }

    private fun updateUserEmail() {
        val newEmail = userEmailEditText.text.toString().trim()

        if (newEmail.isNotEmpty()) {
            val user: FirebaseUser? = firebaseAuth.currentUser

            user?.updateEmail(newEmail)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentRef = db.collection("cuidador").document("${MainActivity.GlobalData.ultEmail}")
                    val updatedEmail = hashMapOf<String, Any>(
                        "email" to newEmail
                    )
                    documentRef.update(updatedEmail)
                    Toast.makeText(this, "Email atualizado com sucesso", Toast.LENGTH_SHORT).show()
                    Log.d("UpdateEmail", "Email atualizado para: $newEmail")
                } else {
                    Toast.makeText(this, "Erro ao atualizar o email: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("UpdateEmail", "Erro ao atualizar o email: ${task.exception?.message}")
                }
            }
        } else {
            Toast.makeText(this, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show()
            Log.d("UpdateEmail", "Tentativa de atualizar com email vazio")
        }
    }

    private fun deleteAccount() {
        val user: FirebaseUser? = firebaseAuth.currentUser

        user?.let {
            it.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Conta excluída com sucesso", Toast.LENGTH_SHORT).show()
                    Log.d("DeleteAccount", "Conta excluída")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao excluir a conta: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("DeleteAccount", "Erro ao excluir a conta: ${task.exception?.message}")
                }
            }
        } ?: run {
            Toast.makeText(this, "Nenhum usuário autenticado", Toast.LENGTH_SHORT).show()
            Log.e("DeleteAccount", "Tentativa de excluir conta sem usuário autenticado")
        }
    }
}
