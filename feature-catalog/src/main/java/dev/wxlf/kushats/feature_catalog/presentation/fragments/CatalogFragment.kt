package dev.wxlf.kushats.feature_catalog.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_catalog.databinding.FragmentCatalogBinding
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_catalog.presentation.viewmodels.CatalogViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: CatalogFragmentArgs by navArgs<CatalogFragmentArgs>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: CatalogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[CatalogViewModel::class.java]
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchCategory(safeArgs.categoryId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchCategoryState.collect { result ->
                when (result) {
                    is FetchCategoryUseCase.Result.Failure -> {
                        // TODO
                    }
                    FetchCategoryUseCase.Result.Loading -> {
                        // TODO
                    }
                    FetchCategoryUseCase.Result.NotFound -> {
                        // TODO
                    }
                    is FetchCategoryUseCase.Result.Success -> {
                        binding.textView.text = result.category.toString()
                    }
                }
            }
        }
    }
}