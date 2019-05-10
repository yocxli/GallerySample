package yocxli.gallerysample.ui.list


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_mediafile_content.view.*
import kotlinx.android.synthetic.main.fragment_mediafile_section.view.*
import yocxli.gallerysample.GlideApp
import yocxli.gallerysample.R
import yocxli.gallerysample.domain.entity.*

/**
 *
 */
class MediaFileRecyclerViewAdapter(
    private val listener: OnListItemInteractionListener?
) : ListAdapter<MediaListItem, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

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
        val item = getItem(position)

        when (item) {
            is MediaFile -> {
                val holder = viewHolder as ContentViewHolder
                GlideApp.with(holder.contentView)
                    .load(item.uri)
                    .centerCrop()
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

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MediaFile -> Type.CONTENT.ordinal
            else -> Type.SECTION_HEADER.ordinal
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

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MediaListItem>() {

    override fun areItemsTheSame(oldItem: MediaListItem, newItem: MediaListItem): Boolean {
        if (oldItem is MediaFile && newItem is MediaFile) {
            return oldItem.uri == newItem.uri
        } else if (oldItem is SectionLabel && newItem is SectionLabel) {
            return oldItem.title == newItem.title
        }
        return false
    }

    override fun areContentsTheSame(oldItem: MediaListItem, newItem: MediaListItem): Boolean {
        if (oldItem is ImageFile && newItem is ImageFile) {
            return oldItem as ImageFile == newItem as ImageFile
        } else if (oldItem is VideoFile && newItem is VideoFile) {
            return oldItem as VideoFile == newItem as VideoFile
        } else if (oldItem is SectionLabel && newItem is SectionLabel) {
            return oldItem as SectionLabel == newItem as SectionLabel
        }
        return false
    }

}
