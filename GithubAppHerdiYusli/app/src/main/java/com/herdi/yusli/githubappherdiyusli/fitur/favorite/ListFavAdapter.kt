package com.herdi.yusli.githubappherdiyusli.fitur.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herdi.yusli.githubappherdiyusli.data.database.Favorite
import com.herdi.yusli.githubappherdiyusli.databinding.ShapeListUserBinding
import com.herdi.yusli.githubappherdiyusli.view.ProfileActivity

class ListFavAdapter : RecyclerView.Adapter<ListFavAdapter.FavoriteViewHolder>() {
    private val userFavorites = ArrayList<Favorite>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteViewHolder {
        val itemView =
            ShapeListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(userFavorites[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(userFavorites[position]) }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListFavorite(items: List<Favorite>) {
        userFavorites.clear()
        userFavorites.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = userFavorites.size
    class FavoriteViewHolder(private val binding: ShapeListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                txtUserId.text = favorite.username
                Glide.with(itemView)
                    .load(favorite.img_url)
                    .circleCrop()
                    .into(binding.ivUserImg)
                root.setOnClickListener {
                    val i = Intent(itemView.context, ProfileActivity::class.java)
                    i.putExtra(ProfileActivity.EXTRA_ID_USER_FAV, favorite)
                    itemView.context.startActivity(i)
                }
            }
        }
    }

    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorite: Favorite)
    }
}