package com.example.popularlibraries.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.popularlibraries.databinding.FragmentDetailsBinding
import moxy.MvpAppCompatFragment

class DetailFragment : MvpAppCompatFragment(), UsersView {
    
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun init() {
    }

    override fun updateList() {
        TODO("Not yet implemented")
    }
}