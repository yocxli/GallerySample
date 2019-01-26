package yocxli.gallerysample.ui.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridDividerDecoration(val dividerSize: Int, val columnCount: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndexMod = layoutParams.spanIndex % columnCount
        when {
            spanIndexMod == (columnCount - 1) -> { // right edge
                outRect.right = 0
            }
            else -> { // not right edge
                outRect.left = 0
                outRect.right = dividerSize
            }
        }
        outRect.top = 0
        outRect.bottom = dividerSize
    }
}