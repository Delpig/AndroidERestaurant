package fr.isen.leclere.androiderestaurant

import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.isen.leclere.androiderestaurant.databinding.FragmentLayoutBinding


private lateinit var binding: FragmentLayoutBinding
private const val ARG_PARAM1 = ""

class myFrament : Fragment(){
    companion object {
        fun newInstance(url: String): myFrament {
           return myFrament().apply {
                arguments = Bundle().apply {
                    putString("URL", url)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString("URL")?.let {
            Picasso
                .get()
                .load(it)
                .placeholder(R.drawable.error)
                .error(R.drawable.error)
                .into(binding.imageView3)
        }
    }
}