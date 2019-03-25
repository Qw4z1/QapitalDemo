package se.devies.qapitaldemo.presentation.goals

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
                list.map { it.toGoalViewModel() }
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
                { view?.hideLoading() },
                { throwable -> view?.showError(throwable) }
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

private fun SavingsGoal.toGoalViewModel(): GoalViewModel {
    val subtitle = if (targetAmount > 0) "$${currentBalance.toInt()} of ${targetAmount.toInt()}"
    else "$${currentBalance.toInt()}"

    return GoalViewModel(
        id = id,
        imageUrl = goalImageURL,
        title = name,
        subtitle = subtitle
    )
}