package se.devies.qapitaldemo.presentation.goals

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
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

        disposables += repo.observeGoals()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list -> view?.showGoals(list) }

        disposables += repo.refreshGoals()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("Glitter", "Completed refresh") },
                { throwable -> Log.e("Glitter", "Failed refreshing",throwable) }
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
    fun showError()
    fun showGoals(goals: List<SavingsGoal>)
    fun navigateToDetails(goalId: Int)
}