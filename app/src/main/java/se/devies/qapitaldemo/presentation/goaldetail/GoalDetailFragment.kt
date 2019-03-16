package se.devies.qapitaldemo.presentation.goaldetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import se.devies.qapitaldemo.databinding.FragmentGoalDetailBinding

class GoalDetailFragment : Fragment() {

    private val args: GoalDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentGoalDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoalDetailBinding.inflate(inflater, container, false)

        return binding.root
    }
}