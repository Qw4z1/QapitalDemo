package se.devies.qapitaldemo.presentation.goaldetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import se.devies.qapitaldemo.R
import se.devies.qapitaldemo.data.Feed
import se.devies.qapitaldemo.databinding.FragmentGoalDetailBinding
import se.devies.qapitaldemo.presentation.BottomItemDecoration
import se.devies.qapitaldemo.presentation.SideItemDecoration
import se.devies.qapitaldemo.presentation.TopItemDecoration
import se.devies.qapitaldemo.repo

class GoalDetailFragment : Fragment(), GoalDetailView {

    private val args: GoalDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentGoalDetailBinding
    lateinit var presenter: GoalDetailPresenter
    lateinit var adapter: FeedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentGoalDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = GoalDetailPresenter(repo)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = FeedAdapter()
        val itemPadding = resources.getDimensionPixelOffset(R.dimen.item_padding)
        binding.feedList.addItemDecoration(TopItemDecoration(itemPadding))
        binding.feedList.addItemDecoration(BottomItemDecoration(itemPadding))
        binding.feedList.addItemDecoration(SideItemDecoration(itemPadding))
        binding.feedList.addItemDecoration(DividerItemDecoration(context, VERTICAL))

        binding.feedList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.start(this, args.goalid)
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun updateGoal(goal: GoalDetailModel) {
        binding.loading = false
        binding.goal = goal
    }

    override fun updateFeed(feedItems: List<Feed>) {
        adapter.feedItems = feedItems
    }

    override fun showError(t: Throwable) {
        binding.loading = false
    }

    override fun showFeedError(throwable: Throwable) {
        Log.e("Feed", "Error", throwable)
    }

}