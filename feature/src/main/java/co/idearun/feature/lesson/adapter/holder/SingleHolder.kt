package co.idearun.feature.lesson.adapter.holder

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.feature.R
import co.idearun.common.Constants
import co.idearun.data.model.form.ChoiceItem
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardSignleItemBinding
import co.idearun.feature.Binding

import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel


class SingleHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardSignleItemBinding.bind(view)
    fun bindItems(
        field: Fields,
        pos: Int,

        form: Form,
        uiViewModel: UIViewModel,
        lessonListener: LessonListener
    ) {
        binding.field = field
        binding.form = form
        binding.viewmodel = uiViewModel

        binding.fieldUiHeader.field = field
        binding.fieldUiHeader.form = form
        val context = binding.valueRg.context
        binding.lifecycleOwner = context as LifecycleOwner


        val type = field.type
        if (type != null && type.equals(Constants.YESNO)) {
            field.choice_items = arrayListOf(
                ChoiceItem(
                    context.resources.getString(R.string.yes),
                    null,
                    "yes"
                ), ChoiceItem(
                    context.resources.getString(
                        R.string.no
                    ), null, "no"
                )
            )
        }

        field.choice_items?.let {
            addRadioButtons(
                field,
                binding.valueRg,
                ArrayList(it),
                form,
                uiViewModel,lessonListener
            )

        }
    }

    fun addRadioButtons(
        field: Fields,
        value_rg: RadioGroup,
        items: ArrayList<ChoiceItem>,
        form: Form,
        uiViewModel: UIViewModel,
        lessonListener: LessonListener,
    ) {
        val context = value_rg.context
        val type = field.type
        value_rg.orientation = if (type != null && type.equals(Constants.YESNO)) {
            LinearLayout.HORIZONTAL
        } else {
            LinearLayout.VERTICAL
        }

        for (i in 1..items.size) {
            value_rg.addView(createRadioButton(form,uiViewModel,items,field,i,context,lessonListener))
        }
    }

    private fun createRadioButton(
        form: Form,
        uiViewModel: UIViewModel,
        items: java.util.ArrayList<ChoiceItem>,
        field: Fields,
        i: Int,
        context: Context,
        lessonListener: LessonListener
    ): RadioButton {


        val rdbtn = RadioButton(context)

        rdbtn.apply {
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 48

            textAlignment = View.TEXT_ALIGNMENT_CENTER

            layoutParams = lp
            setPadding(48, 48, 48, 48)
            minLines=2
            setButtonDrawable(android.R.color.transparent);

            Binding.fieldBackground(this, form,)
            Binding.setTextColor(this,form.text_color)

            setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                context.resources.getDimension(R.dimen.font_xlarge)
            )

            id = View.generateViewId()
            text = items[i - 1].title?:""

            items[i - 1].image?.let {

            }

            setOnCheckedChangeListener { compoundButton, b ->
                if (b){
                    Binding.selectedFieldBackground(this, form)
                    Binding.setSelectedTextColor(this,form)

                }else{
                    Binding.fieldBackground(this, form,)
                    Binding.setTextColor(this,form.text_color)

                }
            }

            setOnClickListener {
                items[i - 1].slug?.let { slug ->
                    uiViewModel.addKeyValueToReq(field.slug!!, slug)
                    Handler(Looper.getMainLooper()).postDelayed({
                        lessonListener.next()
                    }, Constants.AUTO_NEXT_DELAY)

                }

            }
        }

        return rdbtn
    }
}