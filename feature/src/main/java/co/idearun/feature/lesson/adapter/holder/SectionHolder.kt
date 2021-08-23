package co.idearun.feature.lesson.adapter.holder

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.extension.visible
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutFlashCardSectionItemBinding
import co.idearun.feature.lesson.adapter.SwipeStackAdapter
import co.idearun.feature.lesson.listener.SwipeStackListener

import co.idearun.feature.viewmodel.UIViewModel
import link.fls.swipestack.SwipeStack

class SectionHolder(view: View, private val swipeStackListener: SwipeStackListener) :
    RecyclerView.ViewHolder(view) {
    val binding = LayoutFlashCardSectionItemBinding.bind(view)
    fun bindItems(
        item: Fields,
        pos: Int,

        form: Form,
        uiViewModel: UIViewModel
    ) {
        binding.field = item
        binding.form = form

        binding.lifecycleOwner = binding.swipeStack.context as LifecycleOwner

        // Prepare the View for the animation
        binding.swipeStack.visible()
        binding.swipeStack.alpha = 0.0f

        // Start the animation
        binding.swipeStack.animate()
            .translationY(binding.swipeStack.height.toFloat())
            .alpha(1.0f)
            .setListener(null);

        setSwipeStack(arrayListOf(item), form,  pos)

    }

    private fun setSwipeStack(
        fields: ArrayList<Fields>?,
        form: Form,
        fieldsPos: Int
    ) {
        val swipeStackAdapter = SwipeStackAdapter(fields ?: arrayListOf(), form)
        binding.swipeStack.adapter = swipeStackAdapter
        swipeStackAdapter.notifyDataSetChanged()

        binding.swipeStack.setListener(object : SwipeStack.SwipeStackListener {
            override fun onViewSwipedToLeft(position: Int) {

            }

            override fun onViewSwipedToRight(position: Int) {

            }

            override fun onStackEmpty() {
                swipeStackListener.onSwipeEnd(fieldsPos)

            }


        })
    }

}