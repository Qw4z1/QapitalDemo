package se.devies.qapitaldemo.presentation.goals

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import se.devies.qapitaldemo.data.SavingsGoal
import se.devies.qapitaldemo.databinding.FragmentGoalsBinding
import se.devies.qapitaldemo.repo

class GoalsFragment: Fragment(), GoalDetailView {

    lateinit var binding: FragmentGoalsBinding
    lateinit var presenter: GoalsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = GoalsPresenter(repo)
    }

    override fun onStart() {
        super.onStart()
        presenter.start(this)
        binding.presenter = presenter
        binding.loading = false
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    //region GoalDetailView
    override fun showLoading() {

    }

    override fun showError() {

    }

    override fun showGoals(goals: List<SavingsGoal>) {

    }

    override fun navigateToDetails(goalId: Int) {
        Log.d("clicker", "Clicked button goal id $goalId")
        val action = GoalsFragmentDirections.actionGoalsFragmentToGoalDetailFragment(
            goalid = goalId
        )
        findNavController().navigate(action)
    }
    //endregion
}