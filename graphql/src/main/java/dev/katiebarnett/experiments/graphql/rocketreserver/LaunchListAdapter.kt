package dev.katiebarnett.experiments.graphql.rocketreserver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.katiebarnett.experiments.graphql.R
import dev.katiebarnett.experiments.graphql.databinding.LaunchItemBinding

class LaunchListAdapter(
    private val launches: List<LaunchListQuery.Launch>
) :
    RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    var onEndOfListReached: (() -> Unit)? = null
    var onItemClicked: ((LaunchListQuery.Launch) -> Unit)? = null

    class ViewHolder(val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root)
    
    override fun getItemCount(): Int {
        return launches.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launches[position]
        holder.binding.site.text = launch.site ?: ""
        holder.binding.missionName.text = launch.mission?.name
        holder.binding.missionPatch.load(launch.mission?.missionPatch) {
            placeholder(R.drawable.ic_placeholder)
        }

        if (position == launches.size - 1) {
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(launch)
        }
    }
}