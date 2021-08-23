package co.idearun.feature.lesson.adapter.holder

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardStarItemBinding

import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel

class StarHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardStarItemBinding.bind(view)
    fun bindItems(
        item: Fields,
        pos: Int,

        form: Form,
        uiViewModel: UIViewModel,
        lessonListener: LessonListener
    ) {
        binding.field = item
        binding.form = form
        binding.viewmodel = uiViewModel

        binding.fieldUiHeader.field = item
        binding.fieldUiHeader.form = form


        binding.starRating.setOnRatingBarChangeListener { ratingBar, fl, b ->
            uiViewModel.addKeyValueToReq(item.slug!!, fl)
            Handler(Looper.getMainLooper()).postDelayed({
                lessonListener.next()
            }, Constants.AUTO_NEXT_DELAY)
        }

        if (item.required == true) {
            uiViewModel.reuiredField(item)

        } else {

        }
    }



}