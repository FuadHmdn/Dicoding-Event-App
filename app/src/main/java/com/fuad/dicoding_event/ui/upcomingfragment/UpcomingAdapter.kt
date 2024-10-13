package com.fuad.dicoding_event.ui.upcomingfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuad.dicoding_event.data.ListEventsItem
import com.fuad.dicoding_event.databinding.ListFinishedEventHomeFragmentBinding
import com.fuad.dicoding_event.databinding.ListFinishedFragmentBinding

class UpcomingAdapter(private val onItemClick: (ListEventsItem) -> Unit) : ListAdapter<ListEventsItem, UpcomingAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    class ViewHolder(private val binding: ListFinishedFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem, onItemClick: (ListEventsItem) -> Unit) {
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.imageUpcomingHome)

            binding.eventTitle.text = event.name
            binding.summaryFinishedEvent.text = event.summary

            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListFinishedFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event, onItemClick)


    }
}