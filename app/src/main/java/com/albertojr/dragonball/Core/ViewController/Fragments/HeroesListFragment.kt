package com.albertojr.dragonball.Core.ViewController.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.albertojr.dragonball.Core.Model.Heroe
import com.albertojr.dragonball.Core.ViewModel.CoreViewModel
import com.albertojr.dragonball.FragmentListAdapter
import com.albertojr.dragonball.R
import com.albertojr.dragonball.databinding.FragmentHeroesListBinding
import com.albertojr.dragonball.onClickGridItem
import kotlinx.coroutines.launch


class HeroesListFragment : Fragment(), onClickGridItem {

    private lateinit var binding: FragmentHeroesListBinding

    val coreViewModel: CoreViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeroesListBinding.inflate(inflater)
        setFloatingHealAllHeroesButton()

        val adapter = FragmentListAdapter(coreViewModel.heroesList, this)
        // binding.rvHeroesList.layoutManager = LinearLayoutManager(binding.rvHeroesList.context)
        binding.rvHeroesList.layoutManager = GridLayoutManager(binding.rvHeroesList.context, 2)
        binding.rvHeroesList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            coreViewModel.uiState.collect {
                //TODO add the listVIewRefreshermethod in here
                adapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }

    override fun onClick(heroe: Heroe) {
        coreViewModel.selectedHeroToFightClicked(heroe)
    }

    private fun setFloatingHealAllHeroesButton() {
        binding.fabHealAllHeroes.setOnClickListener {
            showHealAllHeroesAlertDialog()
        }
    }

    private fun showHealAllHeroesAlertDialog() {
        val builder = AlertDialog.Builder(binding.fabHealAllHeroes.context)
        val view = layoutInflater.inflate(R.layout.custom_alert_dialog, null)

        builder.setView(view)
        builder.setTitle(R.string.alert_dialog_title)

        builder.setPositiveButton(getString(R.string.alert_dialog_yes)) { dialogInterface, which ->
            //Toast.makeText(binding.fabHealAllHeroes.context,"clicked yes", Toast.LENGTH_LONG).show()
            alertHealAllHeroesClicked()
        }
        builder.setNegativeButton(getString(R.string.alert_dialog_cancel)) { dialogInterface, which ->

        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
            ContextCompat.getColor(
                binding.fabHealAllHeroes.context,
                R.color.red_dragon
            )
        )
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
            ContextCompat.getColor(
                binding.fabHealAllHeroes.context,
                R.color.green_dragon
            )
        )
    }

    private fun alertHealAllHeroesClicked() {
        coreViewModel.healAllHeroes()
    }

}