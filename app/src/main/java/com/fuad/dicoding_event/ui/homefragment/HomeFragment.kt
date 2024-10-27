package com.fuad.dicoding_event.ui.homefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuad.dicoding_event.data.remote.ListEventsItem
import com.fuad.dicoding_event.databinding.FragmentHomeBinding
import com.fuad.dicoding_event.ui.DetailActivity

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

        binding.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewFinished.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.listEventItemUpcoming.observe(viewLifecycleOwner){ listEventItem ->
            setUpcomingEvent(listEventItem)
        }

        viewModel.listEventItemFinished.observe(viewLifecycleOwner) { listEventItem ->
            setFinished(listEventItem)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        viewModel.failure.observe(viewLifecycleOwner){ failure->
            if (failure) {
                viewModel.failureMessage.observe(viewLifecycleOwner){ message ->
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setFinished(listEventItem: List<ListEventsItem?>?) {
        val adapter = EventFinishedAdapter { item ->
            val id = item.id
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra( DetailActivity.EXTRA_ID , id)
            startActivity(intent)
        }
        adapter.submitList(listEventItem)
        binding.recyclerViewFinished.adapter = adapter
    }

    private fun setUpcomingEvent(listEventsItem: List<ListEventsItem?>?) {
        val adapter = EventUpcomingAdapter { item ->
            val id = item.id
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra( DetailActivity.EXTRA_ID , id)
            startActivity(intent)
        }
        adapter.submitList(listEventsItem)
        binding.recyclerViewUpcoming.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}