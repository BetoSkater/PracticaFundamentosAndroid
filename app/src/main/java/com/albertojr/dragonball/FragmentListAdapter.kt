package com.albertojr.dragonball

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.albertojr.dragonball.databinding.HeroeCellBinding
import com.squareup.picasso.Picasso

interface onClickGridItem{
    fun onClick(heroe: Heroe)
}

class FragmentListAdapter(
        //TODO proably i should add viewmodel.heroesList
        private val heroesList: List<Heroe>,
        private val callback : onClickGridItem
    ): RecyclerView.Adapter<FragmentListAdapter.HeroesListFragmentViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HeroesListFragmentViewHolder {
            val binding = HeroeCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HeroesListFragmentViewHolder(binding, callback)
        }

        override fun onBindViewHolder(
            holder: HeroesListFragmentViewHolder,
            position: Int
        ) {
            holder.showHeroes(heroesList[position])
        }

        override fun getItemCount(): Int {
            return heroesList.size
        }


        class HeroesListFragmentViewHolder(private var item: HeroeCellBinding, private val callback: onClickGridItem): RecyclerView.ViewHolder(item.root){
            fun showHeroes(heroe: Heroe){
             //   item.ivHeroePicture.image //TODO Instalar Picasso para poder mostrar las imagenes bien
                item.tvName.text = heroe.name
                Picasso.get().load(heroe.photo).into(item.ivHeroePicture)
                //TODO addd a method to calculate the hp % and add it to the progressbar
                item.pbHitPoint.max = heroe.totalHitPoints
                item.pbHitPoint.progress = heroe.currentHitPoints
                item.heroeCell.setOnClickListener {
                    Toast.makeText(item.root.context, "El luchador es ${heroe.name}", Toast.LENGTH_LONG).show()

                    callback.onClick(heroe)
                }

                if(heroe.isDead){
                    item.ivHeroePicture.foreground = ContextCompat.getDrawable(item.ivHeroePicture.context, R.drawable.dead_character_filter)
                    item.ivHeroePicture.alpha = 0.6F
                    item.heroeCell.isClickable = false
                }else{
                    item.ivHeroePicture.foreground = ContextCompat.getDrawable(item.ivHeroePicture.context, R.drawable.alive_character_filter)
                    item.ivHeroePicture.alpha = 1.0F
                    item.heroeCell.isClickable = true
                }


            }
        }
    }

