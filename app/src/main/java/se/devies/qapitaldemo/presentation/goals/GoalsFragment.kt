package se.devies.qapitaldemo.presentation.goals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import se.devies.qapitaldemo.R
import se.devies.qapitaldemo.databinding.FragmentGoalsBinding
import se.devies.qapitaldemo.presentation.BottomItemDecoration
import se.devies.qapitaldemo.presentation.SideItemDecoration
import se.devies.qapitaldemo.presentation.TopItemDecoration
import se.devies.qapitaldemo.repo

class GoalsFragment : Fragment(), GoalsView {

    private lateinit var binding: FragmentGoalsBinding
    private lateinit var presenter: GoalsPresenter
    private lateinit var adapter: GoalAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = GoalsPresenter(repo)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = GoalAdapter(presenter::onGoalClicked)
        val itemPadding = resources.getDimensionPixelOffset(R.dimen.item_padding)
        binding.goalsList.addItemDecoration(TopItemDecoration(itemPadding))
        binding.goalsList.addItemDecoration(BottomItemDecoration(itemPadding))
        binding.goalsList.addItemDecoration(SideItemDecoration(itemPadding))

        binding.goalsList.adapter = adapter
        binding.goalsList.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()
        binding.presenter = presenter
        presenter.start(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    //region GoalDetailView
    override fun showLoading() {
        binding.loading = true
    }

    override fun hideLoading() {
        binding.loading = false
    }

    override fun showError(throwable: Throwable) {
        binding.loading = false
        AlertDialog.Builder(context!!).setTitle(throwable.message).create().show()
    }

    override fun showGoals(goals: List<GoalViewModel>) {
        adapter.goals = goals
    }

    override fun navigateToDetails(goalId: Int) {
        val action = GoalsFragmentDirections.actionGoalsFragmentToGoalDetailFragment(
            goalid = goalId
        )
        findNavController().navigate(action)
    }
    //endregion
}