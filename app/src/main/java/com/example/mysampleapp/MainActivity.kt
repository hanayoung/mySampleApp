package com.example.mysampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysampleapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.mysampleapp.BoardListActivity

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val joinBtnClicked = findViewById<Button>(R.id.joinBtn)
        joinBtnClicked.setOnClickListener {
            // 첫 번째 방법
//            val email=findViewById<EditText>(R.id.emailArea)
//            val pwd=findViewById<EditText>(R.id.pwdArea)

            // 두 번째 방법
            val email = binding.emailArea
            val pwd = binding.pwdArea

            auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show(
                        )
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
            binding.logoutBtn.setOnClickListener {
                auth.signOut()
                Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()
            }
            binding.loginBtn.setOnClickListener {

                val email = binding.emailArea
                val pwd = binding.pwdArea

                auth.signInWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                            Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

                            val intent=Intent(this,BoardListActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
}