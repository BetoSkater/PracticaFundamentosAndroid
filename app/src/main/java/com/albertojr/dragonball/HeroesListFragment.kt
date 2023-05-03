package com.albertojr.dragonball

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.albertojr.dragonball.databinding.FragmentFightBinding
import com.albertojr.dragonball.databinding.FragmentHeroesListBinding


class HeroesListFragment : Fragment() {

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
        return binding.root
    }


}