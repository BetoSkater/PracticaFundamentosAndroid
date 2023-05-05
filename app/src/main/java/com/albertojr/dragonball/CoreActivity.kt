package com.albertojr.dragonball

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.albertojr.dragonball.databinding.ActivityCoreBinding
import kotlinx.coroutines.launch

class CoreActivity : AppCompatActivity() {

    companion object{
        const val TAG_TOKEN = "TOKEN_KEY"

        fun launch(context: Context,token:String){
            val intent = Intent(context, CoreActivity::class.java)
            intent.putExtra(TAG_TOKEN, token)
            context.startActivity(intent)
        }

    }


    private lateinit var binding : ActivityCoreBinding
    private val viewModelCA : CoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrieveToken()

        binding.tvTitle.setOnClickListener {
            //addFragment() //TODO El error que salta es que un lateinitvar no se ha inicializado
        }

        lifecycleScope.launch{
            viewModelCA.uiState.collect{
                when (it){
                    is CoreViewModel.UiStateCA.Started ->  viewModelCA.retrieveHeroesList()
                    is CoreViewModel.UiStateCA.Ended -> Log.w("TAG", "Ended")
                    is CoreViewModel.UiStateCA.OnHeroesRetrieved -> {
                        addHeroesListFragment()
                        binding.tvTitle.text = getString(R.string.heroes_list_title)
                    }//TODO se obtiene el listado de heroes bien
                    is CoreViewModel.UiStateCA.Error -> Log.w("TAG", "Error en UiState")
                    is CoreViewModel.UiStateCA.OnHeroeSelectedToFight -> {
                        addFragmentTwo()
                        binding.tvTitle.text = getString(R.string.fight_title)

                    }
                    is  CoreViewModel.UiStateCA.OnHeroIsDead -> {
                        addHeroesListFragment()
                        binding.tvTitle.text = getString(R.string.heroes_list_title)
                    }
                    else -> Unit
                }
            }
        }

    }

    private fun retrieveToken(){
        viewModelCA.token = intent.getStringExtra(TAG_TOKEN).toString()
        binding.tvTitle.text = viewModelCA.token
    }

    private fun addHeroesListFragment(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragment.id,HeroesListFragment()) //TODO pass context in here if needed
            .commitNow()
    }
    private fun addFragmentTwo(){
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragment.id,FightFragment()) //TODO pass context in here if needed
            .commitNow()
    }

    fun changeFragment(nextFragment: FragmentOptions){
        when(nextFragment){
            FragmentOptions.HeroeListFragment -> addHeroesListFragment()
            FragmentOptions.FightFragment -> addFragmentTwo()
        }
    }
}
enum class FragmentOptions{
    HeroeListFragment, FightFragment
}