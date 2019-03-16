package se.devies.qapitaldemo.presentation.goals

import android.util.Log
import se.devies.qapitaldemo.data.DemoRepo
import se.devies.qapitaldemo.data.SavingsGoal

class GoalsPresenter(
    private val repo: DemoRepo
) {

    private var view: GoalDetailView? = null

    fun start(view: GoalDetailView) {
        this.view = view
    }

    fun stop() {
        view = null
    }

    fun onGoalClicked(goalId: Int) {
        Log.d("clicker", "onGoalClicked $goalId")
        view?.navigateToDetails(goalId)
    }

}

interface GoalDetailView {
    fun showLoading()
    fun showError()
    fun showGoals(goals: List<SavingsGoal>)
    fun navigateToDetails(goalId: Int)
}