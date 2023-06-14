package dev.wxlf.kushats.feature_catalog.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_catalog.R
import dev.wxlf.kushats.feature_catalog.databinding.FragmentCatalogBinding
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.mapToDisplayable
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

    private var alertDialog: AlertDialog? = null

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

        val adapter = ListDelegationAdapter(
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
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.GONE

                        showError(result.msg)
                    }

                    FetchCategoryUseCase.Result.Loading -> {
                        binding.circularLoader.visibility = View.VISIBLE
                        binding.productsList.visibility = View.GONE
                    }

                    FetchCategoryUseCase.Result.NotFound -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.GONE

                        showError(getString(R.string.category_not_found))
                    }

                    is FetchCategoryUseCase.Result.Success -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.VISIBLE
                        binding.title.text = result.category.name
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchDishesState.collect { result ->
                when (result) {
                    is FetchDishesUseCase.Result.Failure -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.GONE
                        showError(result.msg)
                    }

                    FetchDishesUseCase.Result.Loading -> {
                        binding.circularLoader.visibility = View.VISIBLE
                        binding.productsList.visibility = View.GONE
                    }

                    is FetchDishesUseCase.Result.Success -> {
                        binding.circularLoader.visibility = View.GONE
                        binding.productsList.visibility = View.VISIBLE
                        adapter.items = result.dishes.mapToDisplayable()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun showError(msg: String) {
        if (alertDialog == null) {
            alertDialog = AlertDialog.Builder(requireContext())
                .setTitle(R.string.error_dialog_title)
                .setMessage(msg)
                .setPositiveButton(R.string.retry_error_dialog_button) { _, _ ->
                }
                .setNegativeButton(R.string.back_error_dialog_button) { _, _ ->
                    findNavController().navigateUp()
                }
                .setOnDismissListener {
                    viewModel.fetchCategory(safeArgs.categoryId)
                    viewModel.fetchDishes(binding.chips.findViewById<Chip>(binding.chips.checkedChipId).text.toString())
                }
                .create()
        }
        alertDialog?.show()
    }
}