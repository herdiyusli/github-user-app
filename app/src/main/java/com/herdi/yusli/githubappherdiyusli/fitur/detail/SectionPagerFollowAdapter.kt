package com.herdi.yusli.githubappherdiyusli.fitur.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.herdi.yusli.githubappherdiyusli.view.FragmentFollowers
import com.herdi.yusli.githubappherdiyusli.view.FragmentFollowing


class SectionPagerFollowAdapter(activity: AppCompatActivity, data: Bundle) :
    FragmentStateAdapter(activity) {
    private var DataBundle: Bundle = data


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollowers()
            1 -> fragment = FragmentFollowing()
        }
        fragment?.arguments = this.DataBundle
        return fragment as Fragment
    }


}