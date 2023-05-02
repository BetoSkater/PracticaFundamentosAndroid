package com.albertojr.dragonball

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class CoreViewModel: ViewModel() {

    lateinit var token: String
    private val baseUrl = "https://dragonball.keepcoding.education"

    private val _uiStateCA = MutableStateFlow<UiStateCA>(CoreViewModel.UiStateCA.Started(true))
    val uiState: StateFlow<UiStateCA> = _uiStateCA




    fun retrieveHeroesList(){
        viewModelScope.launch(Dispatchers.IO){
            val client = OkHttpClient()
            val url = "$baseUrl/api/heros/all"
            val body = FormBody.Builder()
                .add("name","")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val heroesDTOList = gson.fromJson(responseBody.string(),Array<HeroeDTO>::class.java)


                    _uiStateCA.value = UiStateCA.OnHeroesRetrieved(heroesDTOList.toList().map { Heroe(it.photo,it.id,it.favorite,it.description,it.name) })
                Log.w("TAG", "DECODING $heroesDTOList")
                }catch (e: Exception){
                    _uiStateCA.value = UiStateCA.Error("Error while decoding the API response")
                }
            } ?: kotlin.run { Log.w("TAG","Theres an error with tha heroes api call") }
        }
    }

    sealed class UiStateCA{
        data class Started(val started: Boolean) : UiStateCA()
        object Ended : UiStateCA()
        data class Error(val error: String): UiStateCA()
        data class OnHeroesRetrieved(val heroesList: List<Heroe>) : UiStateCA() //TODO cambiar token por lista

    }
}