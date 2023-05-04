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
import kotlin.random.Random

class CoreViewModel: ViewModel() {

    lateinit var token: String
    private val baseUrl = "https://dragonball.keepcoding.education"

    var heroesList : List<Heroe> = listOf()
    lateinit var selectedHeroe: Heroe  //TODO check


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
                    heroesList = heroesDTOList.toList().map { Heroe(it.photo,it.id,it.favorite,it.description,it.name) }

                    _uiStateCA.value = UiStateCA.OnHeroesRetrieved(heroesList)
                Log.w("TAG", "DECODING $heroesDTOList")
                }catch (e: Exception){
                    _uiStateCA.value = UiStateCA.Error("Error while decoding the API response")
                }
            } ?: kotlin.run { Log.w("TAG","Theres an error with tha heroes api call") }
        }
    }

    fun selectedHeroToFightClicked(heroe: Heroe){
        selectedHeroe = heroe

        _uiStateCA.value = UiStateCA.OnHeroeSelectedToFight(heroe)
    }

     fun healHeroe(){
        var healValue = 20
        selectedHeroe.let {
            when(selectedHeroe.currentHitPoints){
               in  80..99 -> healValue = selectedHeroe.totalHitPoints - selectedHeroe.currentHitPoints
                0,100 -> healValue = 0
                else -> healValue= 20
            }
        }
        selectedHeroe.currentHitPoints += healValue
    }

     fun damageHeroe(){
        val damage = Random.nextInt(10,60)

        selectedHeroe.let {
            selectedHeroe.currentHitPoints = selectedHeroe.currentHitPoints - damage
            if (selectedHeroe.currentHitPoints <= 0) selectedHeroe.isDead = true
        }
    }

    sealed class UiStateCA{
        data class Started(val started: Boolean) : UiStateCA()
        object Ended : UiStateCA()
        data class Error(val error: String): UiStateCA()
        data class OnHeroesRetrieved(val heroesList: List<Heroe>) : UiStateCA() //TODO cambiar token por lista
        data class OnHeroeSelectedToFight(var heroe: Heroe) : UiStateCA()

    }
}