package com.rexmo.buslocationtracker.Login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.rexmo.buslocationtracker.R
import com.rexmo.buslocationtracker.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var email:String
    private lateinit var fEmail:String
    private lateinit var password:String
    private lateinit var mProgressBar: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.apply {
            email=etEmail.text.toString().trim()
            password=etPassword.text.toString().trim()
        }
        binding.btnLogin.setOnClickListener {
            if(email.isEmpty()||password.isEmpty())
            {
                Toast.makeText(this,"Enter credentials",Toast.LENGTH_SHORT).show()
            }
            else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(fEmail,password)
                    .addOnCompleteListener(
                        OnCompleteListener <AuthResult>{
                                task->
                           // stopProgress()
                            if(task.isSuccessful){
                                //val firebaseUser: FirebaseUser =task.result!!.user!!
                                Toast.makeText(this,"Registered",Toast.LENGTH_SHORT).show()
                               
                            }
                            else
                            {
                                Toast.makeText(this,task.exception!!.message.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    )

            }
        }


    }
}