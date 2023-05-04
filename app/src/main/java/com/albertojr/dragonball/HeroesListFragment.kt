package com.albertojr.dragonball

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.albertojr.dragonball.databinding.FragmentFightBinding
import com.albertojr.dragonball.databinding.FragmentHeroesListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HeroesListFragment : Fragment(),onClickGridItem {

    private lateinit var binding: FragmentHeroesListBinding
    val heroesListViewModel: HeroesListViewModel by viewModels()
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

        viewLifecycleOwner.lifecycleScope.launch{
            coreViewModel.uiState.collect{
                //TODO add the listVIewRefreshermethod in here
            }
        }


        val adapter = FragmentListAdapter(coreViewModel.heroesList,this)
       // binding.rvHeroesList.layoutManager = LinearLayoutManager(binding.rvHeroesList.context)
        binding.rvHeroesList.layoutManager = GridLayoutManager(binding.rvHeroesList.context,2)
        binding.rvHeroesList.adapter = adapter

        return binding.root
    }

    override fun onClick(heroe: Heroe) {
        coreViewModel.selectedHeroToFightClicked(heroe)
    }


}