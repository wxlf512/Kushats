package dev.wxlf.kushats.feature_bag.presentation.adapterdelegates

import android.annotation.SuppressLint
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.wxlf.kushats.feature_bag.R
import dev.wxlf.kushats.feature_bag.databinding.BagProductItemBinding

@SuppressLint("SetTextI18n")
fun productAdapterDelegate(
    decrementClickListener: (ProductDisplayableItem) -> Unit,
    incrementClickListener: (ProductDisplayableItem) -> Unit
) =
    adapterDelegateViewBinding<ProductDisplayableItem, DisplayableItem, BagProductItemBinding>(
        { layoutInflater, parent -> BagProductItemBinding.inflate(layoutInflater, parent, false) }
    ) {
        binding.decrement.setOnClickListener {
            decrementClickListener(item)
        }
        binding.increment.setOnClickListener {
            incrementClickListener(item)
        }
        bind {
            with(item) {
                binding.image.load(imageUrl) {
                    placeholder(R.drawable.placeholder)
                }
                binding.name.text = name
                binding.price.text = "${price}${getString(R.string.currency)}"
                binding.weight.text = " ${weight}${getString(R.string.mass_unit)}"
                binding.count.text = count.toString()
            }
        }
    }