package dev.wxlf.kushats.feature_catalog.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_catalog.databinding.FragmentCatalogBinding
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.mapToDisplayable
import dev.wxlf.kushats.feature_catalog.presentation.adapterdelegates.DisplayableItem
import dev.wxlf.kushats.feature_catalog.presentation.adapterdelegates.dishesAdapterDelegate
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        val adapter = ListDelegationAdapter<List<DisplayableItem>>(
            dishesAdapterDelegate {
                Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
            }
        )

        binding.productsList.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.productsList.adapter = adapter

        viewModel.fetchCategory(safeArgs.categoryId)
        viewModel.fetchDishes(binding.chips.findViewById<Chip>(binding.chips.checkedChipId).text.toString())

        binding.chips.setOnCheckedStateChangeListener { group, checkedIds ->
            viewModel.fetchDishes(group.findViewById<Chip>(checkedIds[0]).text.toString())
        }

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
                        binding.title.text = result.category.name
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchDishesState.collect { result ->
                when (result) {
                    is FetchDishesUseCase.Result.Failure -> {
                        // TODO
                    }

                    FetchDishesUseCase.Result.Loading -> {
                        // TODO
                    }

                    is FetchDishesUseCase.Result.Success -> {
                        adapter.items = result.dishes.mapToDisplayable()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}