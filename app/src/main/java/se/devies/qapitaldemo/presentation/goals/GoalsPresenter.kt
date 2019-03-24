package se.devies.qapitaldemo.presentation.goals

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import se.devies.qapitaldemo.data.DemoRepo
import se.devies.qapitaldemo.data.SavingsGoal
import se.devies.qapitaldemo.presentation.plusAssign

class GoalsPresenter(
    private val repo: DemoRepo
) {

    private var view: GoalsView? = null

    private var disposables = CompositeDisposable()

    fun start(view: GoalsView) {
        this.view = view
        if (disposables.isDisposed) disposables = CompositeDisposable()
        view.showLoading()

        disposables += repo.observeGoals()
            .map { list ->
                list.map { mapGoalViewModel(it)}
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                view?.showGoals(list)
            }

        disposables += repo.refreshGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.hideLoading()
                    Log.d("Glitter", "Completed refresh")
                },
                { throwable ->
                    view?.showError(throwable)
                }
            )
    }

    private fun mapGoalViewModel(savingsGoal: SavingsGoal): GoalViewModel {
        val subtitle = if (savingsGoal.targetAmount > 0) "$${savingsGoal.currentBalance.toInt()} of ${savingsGoal.targetAmount.toInt()}"
        else "$${savingsGoal.currentBalance.toInt()}"

        return GoalViewModel(
            id = savingsGoal.id,
            imageUrl = savingsGoal.goalImageURL,
            title = savingsGoal.name,
            subtitle = subtitle
        )
    }

    fun stop() {
        view = null
        disposables.dispose()
    }

    fun onGoalClicked(goalId: Int) {
        view?.navigateToDetails(goalId)
    }

}

interface GoalsView {
    fun showLoading()
    fun hideLoading()
    fun showError(throwable: Throwable)
    fun showGoals(goals: List<GoalViewModel>)
    fun navigateToDetails(goalId: Int)
}

data class GoalViewModel(
    val id: Int,
    val title: String,
    val subtitle: String,
    val imageUrl: String
)