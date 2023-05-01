package com.albertojr.dragonball

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.albertojr.dragonball.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG_EMAIL = "MyEmail"
    private val TAG_PASSWROD = "MyPassword"

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUserInterface()

    }

    //SetUserInterface
    private fun setUserInterface(){
        loadDataFromPreferences()
        setActionMethod()
    }

    //Button on Click
    private fun setActionMethod(){

        binding.swSave.setOnClickListener {
            if (!binding.swSave.isChecked){
                saveDataInPreferences("", "")
                Toast.makeText(this,getString(R.string.login_data_removed), Toast.LENGTH_LONG).show()
                loadDataFromPreferences()
            }
        }

        binding.bnLogin.setOnClickListener {
            if (binding.swSave.isChecked ){
                saveDataInPreferences(binding.etEmail.text.toString(), binding.etPass.text.toString())
                Toast.makeText(this,getString(R.string.login_data_saved), Toast.LENGTH_LONG).show()
            }else{
               // saveDataInPreferences("", "")
               // Toast.makeText(this,getString(R.string.login_data_removed), Toast.LENGTH_LONG).show()
                Log.w("Tag","Login data will not be saved")
            }
        }
    }

    //SharedPreferences related Methods
//Save in saveInPreferences
    private fun saveDataInPreferences(mail: String, pass: String){
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putString(TAG_EMAIL, mail).apply()
            putString(TAG_PASSWROD, pass).apply()

        }
    }
//Retrieve
   private fun loadDataFromPreferences(){
       getPreferences(Context.MODE_PRIVATE).apply {
           binding.etEmail.setText(getString(TAG_EMAIL,""))
           binding.etPass.setText(getString(TAG_PASSWROD,""))
           if (binding.etEmail.text.toString() != "" && binding.etPass.text.toString() != ""){
               binding.swSave.isChecked = true
           }
       }
   }

}

