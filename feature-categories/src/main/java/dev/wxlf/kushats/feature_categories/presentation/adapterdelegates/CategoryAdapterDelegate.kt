package dev.wxlf.kushats.feature_categories.presentation.adapterdelegates

import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import dev.wxlf.kushats.feature_categories.R
import dev.wxlf.kushats.feature_categories.databinding.CategoryItemBinding

fun categoryAdapterDelegate(itemClickedListener: (CategoryDisplayableItem) -> Unit) = adapterDelegateViewBinding<CategoryDisplayableItem, DisplayableItem, CategoryItemBinding>(
    { layoutInflater, parent -> CategoryItemBinding.inflate(layoutInflater, parent, false) }
) {
    binding.card.setOnClickListener{
        itemClickedListener(item)
    }
    bind {
        binding.title.text = item.name
        binding.image.load(item.imageUrl) {
            crossfade(true)
            crossfade(250)
            placeholder(R.drawable.placeholder)
        }
    }
}