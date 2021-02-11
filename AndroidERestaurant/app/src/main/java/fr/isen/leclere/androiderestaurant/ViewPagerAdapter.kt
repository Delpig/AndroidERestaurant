package fr.isen.leclere.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(activity: AppCompatActivity,val imageUrls: List<String>) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun createFragment(position: Int): Fragment {
        return myFrament.newInstance(imageUrls[position])
    }
}