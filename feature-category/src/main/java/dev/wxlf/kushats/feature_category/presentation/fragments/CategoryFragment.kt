package dev.wxlf.kushats.feature_category.presentation.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_category.R
import dev.wxlf.kushats.feature_category.databinding.FragmentCategoryBinding
import dev.wxlf.kushats.feature_category.domain.usecases.FetchCategoryUseCase
import dev.wxlf.kushats.feature_category.domain.usecases.FetchDishesUseCase
import dev.wxlf.kushats.feature_category.domain.usecases.mapToDisplayable
import dev.wxlf.kushats.feature_category.presentation.adapterdelegates.DisplayableItem
import dev.wxlf.kushats.feature_category.presentation.adapterdelegates.dishesAdapterDelegate
import dev.wxlf.kushats.feature_category.presentation.viewmodels.CategoryViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val safeArgs: CategoryFragmentArgs by navArgs<CategoryFragmentArgs>()

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: CategoryViewModel

    private lateinit var adapter: ListDelegationAdapter<List<DisplayableItem>>
    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[CategoryViewModel::class.java]
        super.onCreate(savedInstanceState)

        adapter = ListDelegationAdapter(
            dishesAdapterDelegate {
                val productDialog = ProductDialogFragment.newInstance(it.id)
                productDialog.show(
                    requireActivity().supportFragmentManager,
                    getString(R.string.product)
                )
            }
        )

        viewModel.fetchDishes(getString(R.string.all_menu_chip))
        viewModel.fetchCategory(safeArgs.categoryId)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.productsList.layoutManager =
            GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
        binding.productsList.adapter = adapter

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
            alertDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.error_dialog_title)
                .setMessage(msg)
                .setPositiveButton(R.string.retry_error_dialog_button) { _, _ ->
                }
                .setNegativeButton(R.string.back_error_dialog_button) { _, _ ->
                    findNavController().popBackStack()
                }
                .setOnDismissListener {
                    viewModel.fetchCategory(safeArgs.categoryId)
                    viewModel.fetchDishes(binding.chips.findViewById<Chip>(binding.chips.checkedChipId).text.toString())
                }
                .create()
        }
        alertDialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}