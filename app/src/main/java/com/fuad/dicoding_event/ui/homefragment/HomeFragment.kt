package com.fuad.dicoding_event.ui.homefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuad.dicoding_event.data.ListEventsItem
import com.fuad.dicoding_event.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.listEventItemUpcoming.observe(viewLifecycleOwner){ listEventItem ->
            setUpcomingEvent(listEventItem)
        }
    }

    private fun setUpcomingEvent(listEventsItem: List<ListEventsItem?>?) {
        val adapter = EventUpcomingAdapter()
        adapter.submitList(listEventsItem)
        binding.recyclerViewUpcoming.adapter = adapter
    }

}