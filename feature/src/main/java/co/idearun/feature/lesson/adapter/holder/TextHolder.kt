package co.idearun.feature.lesson.adapter.holder

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.feature.R
import co.idearun.common.Constants
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardEdtItemBinding

import co.idearun.feature.viewmodel.UIViewModel
import com.google.android.material.textfield.TextInputEditText

class TextHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardEdtItemBinding.bind(view)
    fun bindItems(
        item: Fields,
        position_: Int,

        form: Form,
        uiViewModel: UIViewModel
    ) {
        binding.field = item
        binding.form = form
        binding.viewmodel = uiViewModel

        binding.fieldUiHeader.field = item
        binding.fieldUiHeader.form = form
        binding.lifecycleOwner = itemView.context as LifecycleOwner

        checkDataValueType(binding.valueEdt, item)

        val context = itemView.context

        if (item.required == true) {
            uiViewModel.reuiredField(item)

        } else {

        }
        val myTextWatcher = object : TextWatcher {
            private var position: Int = position_

            fun updatePosition(position: Int) {
                this.position = position
            }

            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i2: Int,
                i3: Int
            ) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString()
                if (input.isNotEmpty()) {
                    if (checkNumberInput(input)) {
                        uiViewModel.addKeyValueToReq(item.slug!!, input)

                    } else {
                        uiViewModel.setErrorToField(
                            item,
                            "${context.getString(R.string.min_value)} : ${item.min_value ?: "-"}" +
                                    "  " +
                                    "${context.getString(R.string.max_value)} : ${item.max_value ?: ""}"
                        )
                    }
                }
            }

            private fun checkNumberInput(input: String): Boolean {
                val maxValue = item.max_value
                val minValue = item.min_value
                val isBigger = maxValue != null && input.toInt() > maxValue.toInt()
                val isSmaller = minValue != null && input.toInt() < minValue.toInt()

                return !(item.type == Constants.NUMBER && (isBigger || isSmaller))

            }
        }

        myTextWatcher.updatePosition(position_)
        binding.valueEdt.addTextChangedListener(myTextWatcher)


    }


    private fun checkDataValueType(edt: TextInputEditText, item: Fields) {

        item.type?.let { fieldType ->
            when (fieldType) {
                Constants.EMAIL -> {
                    setInputType(edt, InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

                }
                Constants.PHONE -> {
                    setInputType(edt, InputType.TYPE_CLASS_PHONE)

                }
                Constants.LONG_TEXT -> {
                    setInputType(edt, InputType.TYPE_TEXT_FLAG_MULTI_LINE)

                }
                Constants.SHORT_TEXT -> {
                    setInputType(edt, InputType.TYPE_CLASS_TEXT)

                }
                Constants.WEBSITE -> {
                    setInputType(edt, InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT)

                }
                Constants.NUMBER -> {
                    setInputType(edt, InputType.TYPE_CLASS_NUMBER)

                }

            }

        }

        if (item.json_key == "national_number") {
            setInputType(edt, InputType.TYPE_CLASS_NUMBER)

        }
    }

    private fun setInputType(edt: TextInputEditText, type: Int) {
        edt.inputType = type
        edt.text?.length?.let {
            edt.setSelection(it)
        }
    }

}