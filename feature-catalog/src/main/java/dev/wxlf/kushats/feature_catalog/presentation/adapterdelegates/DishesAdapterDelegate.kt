package dev.wxlf.kushats.feature_catalog.presentation.adapterdelegates

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.wxlf.kushats.feature_catalog.databinding.ProductItemBinding

fun dishesAdapterDelegate(itemClickListener: (DishDisplayableItem) -> Unit) =
    adapterDelegateViewBinding<DishDisplayableItem, DisplayableItem, ProductItemBinding>(
        { layoutInflater, parent -> ProductItemBinding.inflate(layoutInflater, parent, false) }
    ) {
        binding.product.setOnClickListener {
            itemClickListener(item)
        }
        bind {
            binding.title.text = item.name
            binding.image.load(item.imageUrl)
        }
    }