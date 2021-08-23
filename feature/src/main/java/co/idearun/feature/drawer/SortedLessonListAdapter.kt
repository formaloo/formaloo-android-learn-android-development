package co.idearun.feature.drawer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import co.idearun.feature.R
import co.idearun.data.model.form.Form
import co.idearun.feature.databinding.LayoutCategoryItemBinding
import co.idearun.feature.databinding.LayoutFormItemBinding
import co.idearun.feature.lesson.LessonActivity
import kotlin.properties.Delegates

class SortedLessonListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    internal var collection: ArrayList<HashMap<Int, Form>> by Delegates.observable(arrayListOf()) { _, _, _ ->
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View
        return when (viewType) {
            TYPE_HEADER -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_category_item, parent, false);
                CategoryViewHolder(itemView)
            }

            TYPE_ITEM -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_form_item, parent, false);
                FormViewHolder(itemView)
            }

            else -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_form_item, parent, false);
                FormViewHolder(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = collection[position]
        val i = ArrayList(item.keys)[0]
        val form = item[i]
        when (getItemViewType(position)) {
            TYPE_HEADER -> {
                (holder as CategoryViewHolder).bindItems(form)

            }

            TYPE_ITEM -> {
                (holder as FormViewHolder).bindItems(form)

            }
        }

    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = collection[position]
        return ArrayList(item.keys)[0]
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = LayoutCategoryItemBinding.bind(itemView)
        fun bindItems(item: Form?) {
            binding.form = item
            binding.formItemLay.form = item
            binding.lifecycleOwner = binding.titleTv.context as LifecycleOwner
            binding.formItemLay.formLay.setOnClickListener {
                openForm(item, it.context)
            }
        }


    }

    inner class FormViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = LayoutFormItemBinding.bind(itemView)
        fun bindItems(item: Form?) {
            binding.form = item
            binding.lifecycleOwner = binding.titleTv.context as LifecycleOwner
            itemView.setOnClickListener {
                openForm(item, it.context)
            }
        }
    }

    private fun openForm(item: Form?, context: Context) {
        val intent = Intent(context, LessonActivity::class.java)
        intent.putExtra("form", item)
        context.startActivity(intent)

    }
}

