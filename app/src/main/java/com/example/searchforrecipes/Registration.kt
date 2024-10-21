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

data class User(var pass:String) {

}

class Registration : AppCompatActivity() {
    private lateinit var pref: SharedPreferences
    lateinit var login: EditText
    lateinit var password: EditText
    lateinit var checkpass: EditText
    private var my_settings="my_settings"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        login=findViewById(R.id.enter_log)
        password=findViewById(R.id.enter_pass)
        checkpass=findViewById(R.id.check_pass)
        pref=getSharedPreferences(my_settings, MODE_PRIVATE)
    }
    fun Register(view: View){
        if(login.text.toString().isNotEmpty() or  password.text.toString().isNotEmpty()or checkpass.text.toString().isNotEmpty() ){
            if(password.text.toString()==checkpass.text.toString()){
                val ed=pref.edit()
                var user=User(password.text.toString())
                var pass: String= Gson().toJson(user)

                ed.putString("${login.text}", pass)
                ed.commit()
                val intent = Intent(this, Authorization::class.java)
                startActivity(intent)
            }
            else
            {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Пароль и подтверждение не совпадает")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show()
            }
        }
        else
        {
            val alert = AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("У вас есть незаполненные поля")
                .setPositiveButton("Ok", null)
                .create()
                .show()
        }
    }

}