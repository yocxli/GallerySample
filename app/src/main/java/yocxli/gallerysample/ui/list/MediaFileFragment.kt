package yocxli.gallerysample.ui.list

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yocxli.gallerysample.R
import yocxli.gallerysample.data.MediaRepositoryImpl
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.usecase.ListAll

/**
 *
 */
class MediaFileFragment : Fragment(), MediaFileRecyclerViewAdapter.OnListItemInteractionListener {

    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    lateinit var viewModel: MediaFileListViewModel

    lateinit var mediaFileRecyclerViewAdapter: MediaFileRecyclerViewAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        }
    }

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

        mediaFileRecyclerViewAdapter = MediaFileRecyclerViewAdapter(
            this@MediaFileFragment,
            arrayListOf(),
            this@MediaFileFragment
        )

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
                adapter = mediaFileRecyclerViewAdapter
                addItemDecoration(
                    GridDividerDecoration(
                        resources.getDimensionPixelSize(R.dimen.grid_divider),
                        columnCount
                    )
                )
            }
        }

        if (context != null) {
            val viewModelFactory = MediaFileViewModelFactory(ListAll(MediaRepositoryImpl(context as Context)))
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(MediaFileListViewModel::class.java)
            viewModel.isLoading.observe(this, object : Observer<Boolean> {
                override fun onChanged(t: Boolean) {
                    if (t) showLoading() else hideLoading()
                }
            })
            viewModel.list.observe(this, object : Observer<List<MediaFile>> {
                override fun onChanged(t: List<MediaFile>) {
                    updateListView(t)
                }
            })

        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (PermissionChecker.checkSelfPermission(context!!, "android.permission.WRITE_EXTERNAL_STORAGE") == PermissionChecker.PERMISSION_GRANTED) {
            viewModel.onStart()
        } else {
            requestPermissions(arrayOf("android.permission.WRITE_EXTERNAL_STORAGE"), 1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when {
            requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                viewModel.onStart()
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
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

    private fun showLoading() {

    }

    private fun hideLoading() {

    }

    private fun updateListView(list: List<MediaFile>) {
        mediaFileRecyclerViewAdapter.values = list
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
