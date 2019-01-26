package yocxli.gallerysample.ui.list

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import yocxli.gallerysample.R


import yocxli.gallerysample.ui.list.MediaFileFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_mediafile.view.*
import yocxli.gallerysample.GlideApp
import yocxli.gallerysample.domain.entity.MediaFile

/**
 *
 */
class MediaFileRecyclerViewAdapter(
    private val fragment: Fragment,
    private val values: List<MediaFile>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MediaFileRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MediaFile
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_mediafile, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        GlideApp.with(fragment)
            .load(item.uri)
            .placeholder(ColorDrawable(Color.BLACK))
            .error(ColorDrawable(Color.RED))
            .fallback(ColorDrawable(Color.GRAY))
            .into(holder.contentView)
        holder.name.text = item.name

        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentView: ImageView = view.content
        val name: TextView = view.name

        override fun toString(): String {
            return super.toString() + " '" + contentView + "'"
        }
    }

}
