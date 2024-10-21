package com.example.searchforrecipes

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson

class Authorization : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    lateinit var login: EditText
    lateinit var password: EditText
    private val my_settings="my_settings"
    data class User(var pass:String){}
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        login=findViewById(R.id.enter_log2)
        password=findViewById(R.id.enter_pass2)
        pref=getSharedPreferences(my_settings, MODE_PRIVATE)
        if (pref.getBoolean("IsAuthorized",false)==true){
            val intent= Intent(this,Recipes::class.java)
            startActivity(intent)
        }
    }
    fun SignIn(view: View){
        if(login.text.toString().isNotEmpty()and password.text.toString().isNotEmpty()){
            var pass=pref.getString("${login.text}",null)
            var user:com.example.searchforrecipes.User
            try{
                user= Gson().fromJson(pass, com.example.searchforrecipes.User::class.java)
            }catch (e:Exception){
                user=com.example.searchforrecipes.User("")
            }
            if(password.text.toString()==user.pass){
                var ed=pref.edit()
                ed.putBoolean("IsAuthorised",true)
                ed.commit()
                val intent= Intent(this,Recipes::class.java)
                startActivity(intent)
            }
            else{
                val alert= AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Неверный логин или пароль")
                    .setPositiveButton("Ok",null)
                    .create()
                    .show()
            }

        }
        else{
            val alert= AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("У вас есть незаполненные поля")
                .setPositiveButton("Ok",null)
                .create()
                .show()
        }
    }
    fun Back(view: View){
        val intent= Intent(this, Registration::class.java)
        startActivity(intent)
    }
}