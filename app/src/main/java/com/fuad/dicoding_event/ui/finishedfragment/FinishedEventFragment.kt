package com.fuad.dicoding_event.ui.finishedfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.fuad.dicoding_event.data.ListEventsItem
import com.fuad.dicoding_event.databinding.FragmentFinishedEventBinding
import com.fuad.dicoding_event.ui.homefragment.EventFinishedAdapter

class FinishedEventFragment : Fragment() {

    private var _binding: FragmentFinishedEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FinishedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFinishedFragment.layoutManager = LinearLayoutManager(context)

        viewModel.listEventItemFinished.observe(viewLifecycleOwner){ listEvent->
            setAdapter(listEvent)
        }

        viewModel.searchEventItemFinished.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) {
                viewModel.listEventItemFinished.observe(viewLifecycleOwner){ listEvent->
                    setAdapter(listEvent)
                }
            } else {
                setAdapter(list)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText
                .setOnEditorActionListener { textView, i, keyEvent ->
                    searchBar.setText(searchView.text)
                    if (i == EditorInfo.IME_ACTION_SEARCH) {

                        val query = textView.text.toString()
                        viewModel.searchEvent(query)
                        searchView.hide()
                        true

                    } else {
                        false
                    }
                }
        }
    }

    private fun setAdapter(event: List<ListEventsItem?>?) {
        val adapter = EventFinishedAdapter()
        adapter.submitList(event)
        binding.rvFinishedFragment.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFinishedFragment.visibility = View.VISIBLE
        } else {
            binding.progressBarFinishedFragment.visibility = View.GONE
        }
    }
}