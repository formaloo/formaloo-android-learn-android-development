package co.idearun.feature.lesson.adapter.holder

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardDropdownItemBinding

import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel

class DropDownHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardDropdownItemBinding.bind(view)
    fun bindItems(
        item: Fields,
        pos: Int,

        form: Form, viewmodel: UIViewModel, lessonListener: LessonListener
    ) {


        val dropAdapter = DropDownItemsAdapter(form)

        var userSelect = false
        binding.valueSpinner.apply {
            adapter = dropAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    dropAdapter.getItem(position)?.slug?.let { slug ->
                        viewmodel.addKeyValueToReq(item.slug!!, slug)
                        if (userSelect) {
                            Handler(Looper.getMainLooper()).postDelayed({
                                lessonListener.next()
                            }, Constants.AUTO_NEXT_DELAY)

                        } else {
                            userSelect = true
                        }
                    }
                }

            }

        }

        binding.field = item
        binding.form = form
        binding.fieldUiHeader.field = item
        binding.fieldUiHeader.form = form
        binding.viewmodel = viewmodel
        binding.lifecycleOwner = binding.spinnerValueLay.context as LifecycleOwner
        binding.executePendingBindings()

        if (item.required == true) {
            viewmodel.reuiredField(item)

        } else {

        }
    }

}