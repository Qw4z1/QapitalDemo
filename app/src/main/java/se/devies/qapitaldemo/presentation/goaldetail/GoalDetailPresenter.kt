package se.devies.qapitaldemo.presentation.goaldetail

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import se.devies.qapitaldemo.data.DemoRepo
import se.devies.qapitaldemo.data.Feed
import se.devies.qapitaldemo.data.SavingsGoal
import se.devies.qapitaldemo.presentation.plusAssign

class GoalDetailPresenter(
    private val repo: DemoRepo
) {
    private var disposables = CompositeDisposable()

    private var view: GoalDetailView? = null

    fun start(view: GoalDetailView, goalId: Int) {
        this.view = view
        if (disposables.isDisposed) disposables = CompositeDisposable()
        disposables += repo.observeGoal(goalId)
            .map { it.toGoalDetailModel() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view?.updateGoal(it) },
                { throwable -> view?.showError(throwable) }
            )

        disposables += repo.observeFeed(goalId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { view?.updateFeed(it) },
                { throwable -> view?.showFeedError(throwable) }
            )
    }

    fun stop() {
        disposables.dispose()
        view = null
    }

}

private fun SavingsGoal.toGoalDetailModel(): GoalDetailModel {
    return GoalDetailModel(
        id = id,
        imageUrl = goalImageURL,
        title = name,
        subtitle = if (targetAmount > 0) "$${currentBalance.toInt()} of ${targetAmount.toInt()}" else "$${currentBalance.toInt()}",
        goalProgress = if (targetAmount > 0) ((currentBalance / targetAmount) * 100).toInt() else -1
    )
}

interface GoalDetailView {
    fun updateGoal(goal: GoalDetailModel)
    fun showError(t: Throwable)
    fun showFeedError(throwable: Throwable)
    fun updateFeed(feedItems: List<Feed>)
}

data class GoalDetailModel(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val subtitle: String,
    val goalProgress: Int
)