package co.idearun.feature.home.adapter

import androidx.recyclerview.widget.DiffUtil
import co.idearun.data.model.form.Form

class DiffUtilCallBack : DiffUtil.ItemCallback<Form>() {
    override fun areItemsTheSame(oldItem: Form, newItem: Form): Boolean {
        return oldItem.slug == newItem.slug
    }

    override fun areContentsTheSame(oldItem: Form, newItem: Form): Boolean {
        return oldItem.slug == newItem.slug
                && oldItem.title == newItem.title
                && oldItem.description == newItem.description
                && oldItem.logo == newItem.logo
    }
}