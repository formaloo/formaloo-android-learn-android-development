package co.idearun.feature.home.adapter

import android.view.View
import co.idearun.data.model.form.Form

interface LessonListListener {
    fun openLessonPage(form: Form?, formItemLay: View,progress:Int)}