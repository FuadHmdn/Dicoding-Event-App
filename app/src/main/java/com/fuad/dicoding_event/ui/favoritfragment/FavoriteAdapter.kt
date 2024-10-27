package com.fuad.dicoding_event.ui.favoritfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fuad.dicoding_event.data.local.entity.EventEntity
import com.fuad.dicoding_event.databinding.ListFinishedFragmentBinding

class FavoriteAdapter(private val onItemClick: (EventEntity) -> Unit): ListAdapter<EventEntity, FavoriteAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    class ViewHolder(private val binding: ListFinishedFragmentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventEntity, onItemClick: (EventEntity) -> Unit) {
            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.imageUpcomingHome)

            binding.eventTitle.text = event.name
            binding.summaryFinishedEvent.text = event.name

            binding.root.setOnClickListener {
                onItemClick(event)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EventEntity>(){

            override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListFinishedFragmentBinding.inflate(LayoutInflater.from(parent.context), parent ,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
       holder.bind(item, onItemClick)
    }
}