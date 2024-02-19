package com.example.starterproject.presentation.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.starterproject.databinding.FragmentDashboardBinding
import com.example.starterproject.presentation.MainViewModel
import com.example.starterproject.presentation.ui.home.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private val adapterCourse: CartAdapter by lazy {
        CartAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }

    private fun setupViews() {
        val cartData = viewModel.localData?.filter { it.totalCart >= 1 }
        binding.rvProducts.adapter = adapterCourse
        adapterCourse.submitList(cartData)
        binding.tvCartEmpty.isVisible = cartData == null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}