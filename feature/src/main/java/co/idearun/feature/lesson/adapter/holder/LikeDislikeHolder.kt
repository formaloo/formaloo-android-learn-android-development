package co.idearun.feature.lesson.adapter.holder

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardLikeDislikeItemBinding

import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel

class LikeDislikeHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardLikeDislikeItemBinding.bind(view)
    fun bindItems(
        field: Fields,
        pos: Int,

        form: Form,
        viewmodel: UIViewModel,
        lessonListener: LessonListener
    ) {
        binding.field = field
        binding.form = form
        binding.fieldUiHeader.field = field
        binding.fieldUiHeader.form = form

        binding.viewmodel = viewmodel
        binding.lifecycleOwner = binding.dislikeBtn.context as LifecycleOwner

        binding.dislikeBtn.setOnClickListener {
            it.isSelected = true
            binding.likeBtn.isSelected = false
            viewmodel.addKeyValueToReq(field.slug?:"",-1)
            Handler(Looper.getMainLooper()).postDelayed({
                lessonListener.next()
            }, Constants.AUTO_NEXT_DELAY)
        }

        binding.likeBtn.setOnClickListener {
            it.isSelected = true
            binding.dislikeBtn.isSelected = false

            viewmodel.addKeyValueToReq(field.slug?:"",1)

            Handler(Looper.getMainLooper()).postDelayed({
                lessonListener.next()
            }, Constants.AUTO_NEXT_DELAY)
        }


        if (field.required == true) {
            viewmodel.reuiredField(field)

        } else {

        }
    }


}