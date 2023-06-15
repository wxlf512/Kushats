package dev.wxlf.kushats.feature_catalog.presentation.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.android.support.AndroidSupportInjection
import dev.wxlf.kushats.feature_catalog.R
import dev.wxlf.kushats.feature_catalog.databinding.FragmentProductDialogBinding
import dev.wxlf.kushats.feature_catalog.domain.usecases.AddProductToBagUseCase
import dev.wxlf.kushats.feature_catalog.domain.usecases.FetchDishUseCase
import dev.wxlf.kushats.feature_catalog.presentation.viewmodels.ProductDialogViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ID = "id"

class ProductDialogFragment : DialogFragment() {

    private var id: Int? = null

    private var _binding: FragmentProductDialogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: ProductDialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProvider(this, factory)[ProductDialogViewModel::class.java]
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(ID)
        }
    }

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentProductDialogBinding.inflate(LayoutInflater.from(context))
        return AlertDialog.Builder(requireContext(), R.style.ProductDialog)
            .setView(binding.root)
            .create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dismissButton.setOnClickListener {
            dismiss()
        }

        viewModel.fetchDish(id ?: -1)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchDishState.collect { result ->
                when (result) {
                    is FetchDishUseCase.Result.Failure -> {
                        Toast.makeText(requireContext(), result.msg, Toast.LENGTH_LONG).show()
                        dismiss()
                    }

                    FetchDishUseCase.Result.Loading -> {
                        binding.circleLoader.visibility = View.VISIBLE
                        binding.content.visibility = View.GONE
                    }

                    FetchDishUseCase.Result.NotFound -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.dish_not_found),
                            Toast.LENGTH_LONG
                        ).show()
                        dismiss()
                    }

                    is FetchDishUseCase.Result.Success -> {
                        val dish = result.dish

                        binding.circleLoader.visibility = View.GONE
                        binding.content.visibility = View.VISIBLE

                        binding.image.load(dish.imageUrl)
                        binding.name.text = dish.name
                        binding.price.text = "${dish.price}${getString(R.string.currency)}"
                        binding.weight.text = " ${dish.weight}${getString(R.string.mass_unit)}"
                        binding.description.text = dish.description

                        binding.addToBagButton.setOnClickListener {
                            viewModel.addDishToBag(dish)
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addProductToBagState.collect { result ->
                when (result) {
                    is AddProductToBagUseCase.Result.Failure -> {
                        Toast.makeText(requireContext(), result.msg, Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                    AddProductToBagUseCase.Result.Loading -> {
                        binding.circleLoader.visibility = View.VISIBLE
                        binding.content.visibility = View.GONE
                    }
                    AddProductToBagUseCase.Result.Success -> {
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            ProductDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
                }
            }
    }
}