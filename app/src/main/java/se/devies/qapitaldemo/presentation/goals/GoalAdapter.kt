package se.devies.qapitaldemo.presentation.goals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import se.devies.qapitaldemo.R

class GoalAdapter : RecyclerView.Adapter<GoalHolder>() {

    var goals: List<GoalViewModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.goal_item, parent, false)
        return GoalHolder(binding)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalHolder, position: Int) {
        holder.bind(goals[position])
    }
}