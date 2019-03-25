package se.devies.qapitaldemo.presentation.goaldetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import se.devies.qapitaldemo.BR
import se.devies.qapitaldemo.R
import se.devies.qapitaldemo.data.Feed

class FeedAdapter : RecyclerView.Adapter<FeedHolder>() {

    var feedItems: List<Feed> = listOf()
        set(value) {
            field = value
            Log.d("Feed", "items ${value.size}")
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.feed_item, parent, false)
        return FeedHolder(binding)
    }

    override fun getItemCount(): Int = feedItems.size

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.onBind(feedItems[position])
    }
}

class FeedHolder(
    private val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(feed: Feed) {
        binding.setVariable(BR.feedItem, feed)
        binding.executePendingBindings()
    }

}