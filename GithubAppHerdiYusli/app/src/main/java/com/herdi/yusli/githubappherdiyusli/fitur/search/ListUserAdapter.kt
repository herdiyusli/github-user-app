package com.herdi.yusli.githubappherdiyusli.fitur.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.herdi.yusli.githubappherdiyusli.data.response.User
import com.herdi.yusli.githubappherdiyusli.databinding.ShapeListUserBinding

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private val list = ArrayList<User>()

    private var onItemClick: OnItemClick? = null

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.onItemClick = onItemClick
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListUser(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }


    inner class UserViewHolder(private val binding: ShapeListUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClick?.onItemClicked(user)
            }

            binding.apply {
                Glide.with(itemView)
                    .load(user.img_user)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .circleCrop()
                    .into(ivUserImg)
                txtUserId.text = user.username


            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ShapeListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    interface OnItemClick {
        fun onItemClicked(data: User)
    }
}