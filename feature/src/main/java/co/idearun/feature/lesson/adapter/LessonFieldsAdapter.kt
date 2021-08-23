package co.idearun.feature.lesson.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.Constants
import co.idearun.common.Constants.DATE
import co.idearun.common.Constants.FILE
import co.idearun.common.Constants.Like_Dislike
import co.idearun.common.Constants.MATRIX
import co.idearun.common.Constants.MULTI_SELECT
import co.idearun.common.Constants.PHONE_VERIFICATION
import co.idearun.common.Constants.SECTION
import co.idearun.common.Constants.SIGNATURE
import co.idearun.common.Constants.SINGLE_SELECT
import co.idearun.common.Constants.TIME
import co.idearun.common.Constants.YESNO
import co.idearun.common.Constants.nps
import co.idearun.common.Constants.star
import co.idearun.feature.R
import co.idearun.common.Constants.DROPDOWN
import co.idearun.data.model.form.Fields
import co.idearun.data.model.form.Form

import co.idearun.feature.lesson.listener.SwipeStackListener
import co.idearun.feature.lesson.adapter.holder.*
import co.idearun.feature.lesson.listener.LessonListener
import co.idearun.feature.viewmodel.UIViewModel
import co.idearun.feature.lesson.adapter.holder.*
import java.util.*
import kotlin.properties.Delegates


class LessonFieldsAdapter(
    private val lessonListener: LessonListener,
    private val swipeStackListener: SwipeStackListener,
    private val form: Form,
    private val viewmodel: UIViewModel
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private var lastPosition = RecyclerView.NO_POSITION
        private val TYPE_DROP_DOWN = 0
        private val TYPE_MULTI = 1
        private val TYPE_SIGNLE = 2
        private val TYPE_EDT = 3
        private val TYPE_LIKE_DISLIKE = 4
        private val TYPE_STAR = 5
        private val TYPE_NPS = 6
        private val TYPE_FILE = 7
        private val TYPE_SECTION = 8
        private val TYPE_TIME = 9
        private val TYPE_DATE = 10
        private val TYPE_MATRIX = 11
        private val TYPE_PHONE_VERIFICATION = 12
        private val TYPE_SIGNATURE = 13
        private val TYPE_CSAT = 14
    }

    internal var collection: ArrayList<Fields> by Delegates.observable(arrayListOf()) { _, _, _ ->
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getHolder(viewType, parent)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position_: Int) {

        val btnItem = collection[position_]
        val context = holder.itemView.context
        val itemViewType = getItemViewType(position_)

        when (itemViewType) {
            TYPE_DROP_DOWN -> {
                (holder as DropDownHolder).bindItems(
                    btnItem,
                    position_,
                    form, viewmodel, lessonListener
                )

            }

            TYPE_MATRIX -> {
                (holder as MatrixHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )

            }

            TYPE_MULTI -> {
                (holder as MultiHolder).bindItems(
                    btnItem, position_,
                     form, viewmodel
                )

            }

            TYPE_SIGNLE -> {
                (holder as SingleHolder).bindItems(
                    btnItem,
                    position_,
                    form,
                    viewmodel, lessonListener
                )

            }

            TYPE_EDT -> {
                (holder as TextHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )

            }

            TYPE_LIKE_DISLIKE -> {
                (holder as LikeDislikeHolder).bindItems(
                    btnItem,
                    position_,
                    form,
                    viewmodel, lessonListener
                )

            }

            TYPE_STAR -> {
                (holder as StarHolder).bindItems(
                    btnItem,
                    position_,
                    form,
                    viewmodel, lessonListener
                )

            }

            TYPE_CSAT -> {
                (holder as CSATHolder).bindItems(
                    btnItem,
                    position_,
                    form,
                    viewmodel, lessonListener
                )

            }

            TYPE_NPS -> {
                (holder as NPSHolder).bindItems(
                    btnItem,
                    position_,
                    form,
                    viewmodel, lessonListener
                )

            }

            TYPE_SECTION -> {
                (holder as SectionHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )

            }

            TYPE_PHONE_VERIFICATION -> {
                (holder as TextHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )
            }

            TYPE_SIGNATURE -> {
                (holder as TextHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )
            }

            else -> {
                (holder as TextHolder).bindItems(
                    btnItem,
                    position_,
                    form,

                    viewmodel
                )

            }
        }

        holder.setIsRecyclable(false)

        val animation: Animation =
            AnimationUtils.loadAnimation(context, getAnimationId(position_, itemViewType))
        holder.itemView.startAnimation(animation)
//
        lastPosition = position_

    }

    private fun getAnimationId(position_: Int, itemViewType: Int): Int {
        return if (position_ > lastPosition) R.anim.slide_in_bottom else R.anim.slide_in_top


    }

    override fun getItemViewType(position: Int): Int {
        val fields = collection[position]
        val type = if (fields.sub_type != null) {
            fields.sub_type
        } else {
            fields.type
        }
        return when (type) {
            DROPDOWN -> {
                TYPE_DROP_DOWN
            }

            YESNO -> {
                TYPE_SIGNLE
            }

            MULTI_SELECT -> {
                TYPE_MULTI
            }

            SINGLE_SELECT -> {
                TYPE_SIGNLE
            }

            Like_Dislike -> {
                TYPE_LIKE_DISLIKE

            }

            Constants.embeded -> {
                TYPE_CSAT

            }

            star -> {
                TYPE_STAR

            }

            nps -> {
                TYPE_NPS

            }

            FILE -> {
                TYPE_FILE

            }

            SECTION -> {
                TYPE_SECTION

            }

            TIME -> {
                TYPE_TIME

            }

            DATE -> {
                TYPE_DATE

            }

            MATRIX -> {
                TYPE_MATRIX

            }

            PHONE_VERIFICATION -> {
                TYPE_PHONE_VERIFICATION

            }

            SIGNATURE -> {
                TYPE_SIGNATURE

            }

            else -> {
                TYPE_EDT
            }
        }

    }

    override fun getItemCount(): Int {
        return collection.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }


    private fun getHolder(viewType: Int, parent: ViewGroup): RecyclerView.ViewHolder {
        val itemView: View
        return when (viewType) {
            TYPE_DROP_DOWN -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_dropdown_item, parent, false);
                DropDownHolder(itemView)
            }

            TYPE_MULTI -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_multi_item, parent, false);
                MultiHolder(itemView)
            }

            TYPE_SIGNLE -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_signle_item, parent, false);
                SingleHolder(itemView)
            }

            TYPE_EDT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_edt_item, parent, false);
                TextHolder(itemView)
            }

            TYPE_LIKE_DISLIKE -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_like_dislike_item, parent, false);
                LikeDislikeHolder(itemView)
            }

            TYPE_STAR -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_star_item, parent, false);
                StarHolder(itemView)
            }

            TYPE_CSAT -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_ui_csat_item, parent, false);
                return CSATHolder(itemView)
            }

            TYPE_NPS -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_nps_item, parent, false);
                NPSHolder(itemView)
            }

            TYPE_SECTION -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_section_item, parent, false);
                SectionHolder(itemView, swipeStackListener)
            }

            TYPE_MATRIX -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_matrix_item, parent, false);
                MatrixHolder(itemView)
            }
//            TYPE_PHONE_VERIFICATION -> {
//                itemView = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.layout_flash_card_phone_verify_item, parent, false);
//                return PhoneVerifyViewHolder(itemView)
//            }
//            TYPE_SIGNATURE -> {
//                itemView = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.layout_flash_card_signature_item, parent, false);
//                return SignatureViewHolder(itemView)
//            }
            else -> {
                itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_flash_card_edt_item, parent, false);
                TextHolder(itemView)

            }
        }

    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }
}


