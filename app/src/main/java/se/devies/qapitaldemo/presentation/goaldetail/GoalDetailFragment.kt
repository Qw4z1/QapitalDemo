package se.devies.qapitaldemo.presentation.goaldetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import se.devies.qapitaldemo.databinding.FragmentGoalDetailBinding
import se.devies.qapitaldemo.repo

class GoalDetailFragment : Fragment() {

    private val args: GoalDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentGoalDetailBinding
    lateinit var presenter: GoalDetailPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = GoalDetailPresenter(repo)
    }
}