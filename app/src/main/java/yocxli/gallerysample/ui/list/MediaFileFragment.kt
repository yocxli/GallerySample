package yocxli.gallerysample.ui.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yocxli.gallerysample.R
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.ui.list.dummy.DummyContent

/**
 *
 */
class MediaFileFragment : Fragment(), MediaFileRecyclerViewAdapter.OnListItemInteractionListener {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mediafile_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, columnCount).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return when (adapter?.getItemViewType(position)) {
                                MediaFileRecyclerViewAdapter.Type.CONTENT.ordinal -> 1
                                else -> columnCount
                            }
                        }
                    }
                }
                adapter = MediaFileRecyclerViewAdapter(
                    this@MediaFileFragment,
                    DummyContent.ITEMS,
                    this@MediaFileFragment
                )
                addItemDecoration(
                    GridDividerDecoration(
                        resources.getDimensionPixelSize(R.dimen.grid_divider),
                        columnCount
                    )
                )
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSectionInteraction(item: String) {
        Toast.makeText(context, "'$item' clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onContentInteraction(item: MediaFile) {
        Toast.makeText(context, "'$item' clicked", Toast.LENGTH_SHORT).show()
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: MediaFile?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MediaFileFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
