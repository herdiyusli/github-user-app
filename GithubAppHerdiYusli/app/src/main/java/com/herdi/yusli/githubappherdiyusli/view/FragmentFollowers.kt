package com.herdi.yusli.githubappherdiyusli.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.herdi.yusli.githubappherdiyusli.R
import com.herdi.yusli.githubappherdiyusli.data.response.User
import com.herdi.yusli.githubappherdiyusli.databinding.FragmentFollowViewpagerBinding
import com.herdi.yusli.githubappherdiyusli.fitur.follow.FollowersViewModel
import com.herdi.yusli.githubappherdiyusli.fitur.search.ListUserAdapter

class FragmentFollowers : Fragment(R.layout.fragment_follow_viewpager) {
    private var _followersbinding: FragmentFollowViewpagerBinding? = null
    private val binding get() = _followersbinding!!
    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var listUserAdapter: ListUserAdapter
    private lateinit var username: String


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(ProfileActivity.EXTRA_USERNAME).toString()
        _followersbinding = FragmentFollowViewpagerBinding.bind(view)
        listUserAdapter = ListUserAdapter()
        listUserAdapter.notifyDataSetChanged()



        listUserAdapter.setOnItemClick(
            object : ListUserAdapter.OnItemClick {
                override fun onItemClicked(data: User) {
                    val intent = Intent(activity, ProfileActivity::class.java)
                    intent.putExtra(ProfileActivity.EXTRA_USERNAME, data.username)
                    intent.putExtra(ProfileActivity.EXTRA_ID_USER_FAV, data.id)
                    intent.putExtra(ProfileActivity.EXTRA_IMAGE_URL, data.img_user)
                    startActivity(intent)
                }
            })

        binding.apply {
            rvFollowUser.setHasFixedSize(true)
            rvFollowUser.layoutManager = LinearLayoutManager(activity)
            rvFollowUser.adapter = listUserAdapter

        }

        loading(true)
        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]
        followersViewModel.setListUserFollowers(username)
        followersViewModel.getListUserFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                listUserAdapter.setListUser(it)
                loading(false)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _followersbinding = null
    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.apply {
                loadingFollow.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                loadingFollow.visibility = View.GONE
            }
        }
    }
}