package co.idearun.feature.lesson.adapter.holder

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import co.idearun.common.extension.invisible
import co.idearun.feature.R
import co.idearun.feature.databinding.LayoutCsatItemBinding
import co.idearun.feature.lesson.adapter.*
import co.idearun.feature.lesson.adapter.*
import co.idearun.feature.lesson.listener.CSATListener
import java.util.*

class CSATAdapter(
    private val item: ArrayList<out Enum<*>>,
    private val selectedItem: Int? = null,
    private val csatListener: CSATListener
) :
    RecyclerView.Adapter<CSATAdapter.NPSItemViewHolder>() {


    private var selectedPos: Int? = selectedItem

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NPSItemViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_csat_item, parent, false);
        return NPSItemViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: NPSItemViewHolder, position_: Int) {
        holder.bindItems(item[holder.absoluteAdapterPosition])

        holder.itemView.isSelected = selectedPos == position_;

        holder.itemView.setOnClickListener {
            if (selectedPos!=null && selectedPos!!>=0){
                notifyItemChanged(selectedPos!!)
            }
            selectedPos = holder.absoluteAdapterPosition
            notifyItemChanged(holder.absoluteAdapterPosition)

            csatListener.csatSelected(holder.absoluteAdapterPosition + 1)
        }
    }


    override fun getItemCount(): Int {
        return item.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    class NPSItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = LayoutCsatItemBinding.bind(itemView)

        fun bindItems(item: Enum<*>) {
            val context = binding.scatTxv.context
            val colorStateList = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_selected),
                    intArrayOf(android.R.attr.state_selected)
                ), intArrayOf(
                    ContextCompat.getColor(context, R.color.colorGlass) //disabled
                    , ContextCompat.getColor(context, R.color.colorButton) //enabled
                )
            )

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                itemView.backgroundTintList = colorStateList
            }

            when (item) {
                is CSATOutline -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)
                }
                is CSATFunny -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)

                }
                is CSATFlat -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)

                }
                is CSATMonster -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)

                }
                is CSATHEART -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)
                    binding.scatTxv.invisible()
                }
                is CSATSTAR -> {
                    binding.scatTxv.setText(item.str)
                    binding.scatImg.setImageResource(item.drawable)
                    binding.scatTxv.invisible()

                }
                else -> {

                }
            }


        }


    }
}


