package com.albertojr.dragonball

import android.content.Context

class SharedPreferencesManager {

    companion object{
        lateinit var shared: SharedPreferencesManager
            private set //Para que no se le pueda asignar un valor desde otra clase
    }
/*
    private fun saveDataInPreferences(mail: String, pass: String){
        getPreferences(Context.MODE_PRIVATE).edit().apply {
            putString(TAG_EMAIL, mail).apply()
            putString(TAG_PASSWROD, pass).apply()

        }
    }
    */
}