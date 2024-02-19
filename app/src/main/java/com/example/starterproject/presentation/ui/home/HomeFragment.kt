package com.example.starterproject.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.starterproject.databinding.FragmentHomeBinding
import com.example.starterproject.presentation.MainViewModel
import com.example.starterproject.presentation.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by activityViewModels()

    private var hasLoadedData = false

    private val adapterCourse: ProductAdapter by lazy {
        ProductAdapter(
            onMinusClick = {
                viewModel.minusCart(it)
            },
            onPlusClick = {
                viewModel.plusCart(it)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasLoadedData) {
            viewModel.getProducts()
            hasLoadedData = true
        }
        setupObserver()
        setupViews()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.productResult.collectLatest {
                        it.onSuccess { result ->
                            adapterCourse.submitList(result)
                        }
                    }
                }
                launch {
                    viewModel.loading.collectLatest {
                        binding.progressBar.isVisible = it
                    }
                }
            }
        }
    }

    private fun setupViews() {
        binding.rvProducts.adapter = adapterCourse
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}