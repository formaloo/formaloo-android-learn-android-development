package co.idearun.feature.lesson.adapter.holder

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.feature.R
import co.idearun.data.model.form.ChoiceItem
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardMatrixItemBinding
import co.idearun.feature.Binding

import co.idearun.feature.viewmodel.UIViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class MatrixHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardMatrixItemBinding.bind(view)
    val checkedAnswer = HashMap<String, String>()

    fun bindItems(
        item: Fields,
        pos: Int,

        form: Form,
        uiViewModel: UIViewModel
    ) {
        binding.field = item
        binding.form = form

        binding.fieldUiHeader.field = item
        binding.fieldUiHeader.form = form
        binding.viewmodel = uiViewModel
        binding.lifecycleOwner = binding.matrixLay.context as LifecycleOwner


        var choices = arrayListOf<ChoiceItem>()
        var groups = arrayListOf<ChoiceItem>()
        item.choice_items?.let {
            choices = it
        }
        item.choice_groups?.let {
            groups = it
        }

        addRadioButtons(
            item,
            binding.matrixLay,
            choices,
            groups,
            form,
            uiViewModel
        )
    }

    fun addRadioButtons(
        field: Fields,
        container: LinearLayout,
        choices: ArrayList<ChoiceItem>,
        groups: ArrayList<ChoiceItem>,
        form: Form,
        uiViewModel: UIViewModel
    ) {
        val context = container.context

        for (g in groups) {
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            val value_rg = CreateRadioGroup(context, form, g, uiViewModel, choices, field, lp)

            container.addView(value_rg)

        }

    }

    private fun CreateRadioGroup(
        context: Context,
        form: Form,
        g: ChoiceItem,
        uiViewModel: UIViewModel,
        choices: ArrayList<ChoiceItem>,
        fields: Fields,
        lp: LinearLayout.LayoutParams
    ): RadioGroup {
        val value_rg = RadioGroup(context)
        value_rg.apply {
            lp.setMargins(16, 0, 16, 32)

            layoutParams = lp

            lp.setMargins(0, 0, 0, 28)

            val title = TextView(context).apply {
                layoutParams = lp
                setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    context.resources.getDimension(R.dimen.font_2xlarge)
                )
                setPadding(48, 48, 48, 48)
                textAlignment = View.TEXT_ALIGNMENT_CENTER

                setLineSpacing(0f, 1.33f)

                form.text_color?.let {
                    setTextColor(Color.parseColor(Binding.getHexColor(form.text_color)))

                }
                form.background_color?.let {
                    setBackgroundColor(Binding.darkenColor(Color.parseColor(Binding.getHexColor(form.background_color))))

                }
                setTypeface(typeface, Typeface.BOLD)

                text = g.title
            }

            addView(title)

            orientation = LinearLayout.VERTICAL

            for (i in 1..choices.size) {
                addView(
                    createRadioButton(
                        form,
                        uiViewModel,
                        choices,
                        fields,
                        i,
                        context,
                        g
                    )
                )

            }
        }



        return value_rg
    }

    private fun createRadioButton(
        form: Form,
        uiViewModel: UIViewModel,
        items: java.util.ArrayList<ChoiceItem>,
        field: Fields,
        i: Int,
        context: Context,
        g: ChoiceItem
    ): RadioButton {


        val rdbtn = RadioButton(context)

        rdbtn.apply {
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 48

            layoutParams = lp
            setPadding(48, 48, 48, 48)
            minLines = 2
            setButtonDrawable(android.R.color.transparent);
            textAlignment = View.TEXT_ALIGNMENT_CENTER

            Binding.fieldBackground(this, form)
            Binding.setTextColor(this, form.text_color)


            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.font_xlarge)
            )

            id = View.generateViewId()
            text = items[i - 1].title ?: ""

            items[i - 1].image?.let {
                setRadioImage(it, this)
            }
            setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    Binding.selectedFieldBackground(this, form)
                    Binding.setSelectedTextColor(this, form)

                } else {
                    Binding.fieldBackground(this, form)
                    Binding.setTextColor(this, form.text_color)

                }
            }

            setOnClickListener {
                if (g.slug != null && items[i - 1].slug != null) {
                    checkedAnswer[g.slug!!] = items[i - 1].slug!!
                    uiViewModel.addKeyValueToReq(
                        field.slug!!,
                        getStr(checkedAnswer)
                    )
                }

            }
        }

        return rdbtn
    }

    private fun setRadioImage(it: String, rdbtn: RadioButton) {
        Picasso.get().load(it).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let {
                    val padding = rdbtn.resources.getDimensionPixelSize(R.dimen.padding_2xsmall)
                    val height = rdbtn.resources.getDimensionPixelSize(R.dimen.btn_h)
                    val width = rdbtn.resources.getDimensionPixelSize(R.dimen.btn_h_l)

                    val drawable = BitmapDrawable(
                        rdbtn.resources,
                        Bitmap.createScaledBitmap(bitmap, 150, height, false)
                    )
                    rdbtn.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null)

                    rdbtn.compoundDrawablePadding = padding

                }
            }

            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                Log.e("TAG", "onBitmapFailed: $e")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

        })

    }

    private fun getStr(checkedAnswer: HashMap<String, String>): String {
        var str = "{"
        val lastItem = checkedAnswer.keys.last()
        checkedAnswer.keys.forEach {
            str += "\"$it\":\"${checkedAnswer[it]}\""
            str += if (!it.equals(lastItem)) {
                ","
            } else {
                "}"

            }
        }
        return str
    }
}