package co.idearun.feature.lesson.adapter.holder

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.idearun.common.Constants
import co.idearun.common.extension.invisible
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutUiCsatItemBinding

import co.idearun.feature.lesson.adapter.*
import co.idearun.feature.lesson.listener.CSATListener
import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel
import co.idearun.feature.lesson.adapter.*

class CSATHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val binding = LayoutUiCsatItemBinding.bind(itemView)

    fun bindItems(
        field: Fields,
        position_: Int,
        form: Form,
        viewmodel: UIViewModel,
        lessonListener: LessonListener
    ) {
        binding.field = field
        binding.form = form
        binding.fieldUiHeader.field = field
        binding.fieldUiHeader.form = form

        binding.viewmodel = viewmodel
        val context = binding.starlay.context
        binding.lifecycleOwner = context as LifecycleOwner

        var selectedItem: Int? = null


        setUpCSATIconList(field, viewmodel, selectedItem,lessonListener)

        if (field.required == true) {
            viewmodel.reuiredField(field)

        } else {

        }

    }

    private fun setUpCSATIconList(
        field: Fields,
        viewmodel: UIViewModel,
        selectedItem: Int?,
        lessonListener: LessonListener
    ) {
        val list = when (field.thumbnail_type) {
            CSATThumbnailType.flat_face.name -> {

                arrayListOf(
                    CSATFlat.ANGRY,
                    CSATFlat.SAD,
                    CSATFlat.NEUTRAL,
                    CSATFlat.SMILE,
                    CSATFlat.LOVE
                )
            }
            CSATThumbnailType.funny_face.name -> {
                arrayListOf(
                    CSATFunny.ANGRY,
                    CSATFunny.SAD,
                    CSATFunny.NEUTRAL,
                    CSATFunny.SMILE,
                    CSATFunny.LOVE
                )

            }
            CSATThumbnailType.outlined.name -> {
                arrayListOf(
                    CSATOutline.ANGRY,
                    CSATOutline.SAD,
                    CSATOutline.NEUTRAL,
                    CSATOutline.SMILE,
                    CSATOutline.LOVE
                )
            }
            CSATThumbnailType.heart.name -> {
                arrayListOf(
                    CSATHEART.ANGRY,
                    CSATHEART.SAD,
                    CSATHEART.NEUTRAL,
                    CSATHEART.SMILE,
                    CSATHEART.LOVE
                )

            }
            CSATThumbnailType.monster.name -> {
                arrayListOf(
                    CSATMonster.ANGRY,
                    CSATMonster.SAD,
                    CSATMonster.NEUTRAL,
                    CSATMonster.SMILE,
                    CSATMonster.LOVE
                )
            }
            CSATThumbnailType.star.name -> {
                arrayListOf(
                    CSATSTAR.ANGRY,
                    CSATSTAR.SAD,
                    CSATSTAR.NEUTRAL,
                    CSATSTAR.SMILE,
                    CSATSTAR.LOVE
                )
            }

            else -> {
                binding.csatIconRes.invisible()
                arrayListOf()

            }
        }

        binding.csatIconRes.apply {
            layoutManager = StaggeredGridLayoutManager(5, RecyclerView.VERTICAL)
            adapter = CSATAdapter(list, (selectedItem ?: 0) - 1, object : CSATListener {
                override fun csatSelected(pos: Int) {
                    viewmodel.addKeyValueToReq(field.slug!!, pos)
                    Handler(Looper.getMainLooper()).postDelayed({
                        lessonListener.next()
                    }, Constants.AUTO_NEXT_DELAY)
                }

            })
        }


    }



}