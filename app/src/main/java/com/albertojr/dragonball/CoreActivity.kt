package com.albertojr.dragonball

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.albertojr.dragonball.databinding.ActivityCoreBinding

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



    }

    private fun retrieveToken(){
        viewModelCA.token = intent.getStringExtra(TAG_TOKEN).toString()
        binding.tvTitle.text = viewModelCA.token
    }
}