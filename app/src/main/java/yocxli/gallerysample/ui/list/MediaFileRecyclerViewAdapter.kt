package yocxli.gallerysample.ui.list


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_mediafile_content.view.*
import kotlinx.android.synthetic.main.fragment_mediafile_section.view.*
import yocxli.gallerysample.GlideApp
import yocxli.gallerysample.R
import yocxli.gallerysample.domain.entity.MediaFile

/**
 *
 */
class MediaFileRecyclerViewAdapter(
    private val fragment: Fragment,
    var values: List<out Any>,
    private val listener: OnListItemInteractionListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val mOnSectionClickListener: View.OnClickListener
    private val mOnContentClickListener: View.OnClickListener

    init {
        mOnSectionClickListener = View.OnClickListener { v ->
            val item = v.tag as String
            listener?.onSectionInteraction(item)

        }
        mOnContentClickListener = View.OnClickListener { v ->
            val item = v.tag as MediaFile
            listener?.onContentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            Type.CONTENT.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_mediafile_content, parent, false)
                return ContentViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.fragment_mediafile_section, parent, false)
                return SectionViewHolder(view)
            }
        }

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = values[position]

        when (item) {
            is MediaFile -> {
                val holder = viewHolder as ContentViewHolder
                GlideApp.with(fragment)
                    .load(item.uri)
                    .placeholder(ColorDrawable(Color.GRAY))
                    .error(ColorDrawable(Color.RED))
                    .fallback(ColorDrawable(Color.BLUE))
                    .into(holder.contentView)
                holder.name.text = item.name

                with(holder.view) {
                    tag = item
                    setOnClickListener(mOnContentClickListener)
                }
            }
            else -> {
                val holder = viewHolder as SectionViewHolder
                holder.title.text = item.toString()
                with(holder.view) {
                    tag = item.toString()
                    setOnClickListener(mOnSectionClickListener)
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    override fun getItemViewType(position: Int): Int {
        val item = values[position]
        when (item) {
            is MediaFile -> return Type.CONTENT.ordinal
            else -> return Type.SECTION_HEADER.ordinal
        }
    }

    inner class SectionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.section_title

        override fun toString(): String {
            return super.toString() + " '$title'"
        }
    }

    inner class ContentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentView: ImageView = view.content
        val name: TextView = view.name

        override fun toString(): String {
            return super.toString() + " '$contentView'"
        }
    }

    enum class Type {
        SECTION_HEADER,
        CONTENT
    }

    interface OnListItemInteractionListener {
        fun onSectionInteraction(item: String)
        fun onContentInteraction(item: MediaFile)
    }
}
