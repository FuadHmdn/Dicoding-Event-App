package com.fuad.dicoding_event.ui.favoritfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fuad.dicoding_event.data.local.entity.EventEntity
import com.fuad.dicoding_event.databinding.FragmentFavoriteBinding
import com.fuad.dicoding_event.ui.DetailActivity
import com.fuad.dicoding_event.ui.ViewModelFactory


class FavoriteEventFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val factory: ViewModelFactory by lazy {
        ViewModelFactory.getInstance(requireActivity())
    }

    private val viewModel: FavoriteViewModel by viewModels{
        factory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFavoriteFragment.layoutManager = LinearLayoutManager(requireContext())


        viewModel.getAllFavoriteEvent().observe(viewLifecycleOwner){ event->
            if (event.isNotEmpty()) {
                setAdapter(event)
            } else {
                setAdapter(emptyList())
            }
        }
    }

    private fun setAdapter(event: List<EventEntity?>?) {
        val adapter = FavoriteAdapter { item ->
            val id = item.id.toInt()
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra( DetailActivity.EXTRA_ID , id)
            startActivity(intent)
        }

        adapter.submitList(event)
        binding.rvFavoriteFragment.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}