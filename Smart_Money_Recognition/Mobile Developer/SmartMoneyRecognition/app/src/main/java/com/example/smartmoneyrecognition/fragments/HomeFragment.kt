package com.example.smartmoneyrecognition.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.smartmoneyrecognition.R
import com.example.smartmoneyrecognition.adapter.rvHorizontalAdapter
import com.example.smartmoneyrecognition.databinding.FragmentHomeBinding
import com.example.smartmoneyrecognition.model.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding?= null
    private val  binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        val getList = viewModel.getRvLists()

        val rvAdapter = rvHorizontalAdapter(getList)
        binding.rvHorizontal.adapter = rvAdapter

        val cameraFragment = CameraFragment()

        binding.cekUang.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.flFragment,cameraFragment)
                commit()
            }
        }
    }
}