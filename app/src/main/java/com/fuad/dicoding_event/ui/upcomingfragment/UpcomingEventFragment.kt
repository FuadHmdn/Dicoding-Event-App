package com.fuad.dicoding_event.ui.upcomingfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fuad.dicoding_event.R
import com.fuad.dicoding_event.databinding.FragmentFinishedEventBinding
import com.fuad.dicoding_event.databinding.FragmentUpcomingEventBinding
import com.fuad.dicoding_event.ui.finishedfragment.FinishedViewModel

class UpcomingEventFragment : Fragment() {

    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpcomingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

}