package co.idearun.feature.lesson.adapter.holder

import android.view.View
import androidx.lifecycle.LifecycleOwner
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutStackSectionItemBinding

class StackHolder(view: View) {
    val binding = LayoutStackSectionItemBinding.bind(view)
    fun bindItems(item: Fields, pos: Int, form: Form) {
        binding.field = item
        binding.form = form
        binding.lifecycleOwner = binding.keyTxv.context as LifecycleOwner


    }


}