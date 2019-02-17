package yocxli.gallerysample.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import yocxli.gallerysample.domain.usecase.ListAll
import java.lang.IllegalArgumentException

class MediaFileViewModelFactory(val listAll: ListAll) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MediaFileListViewModel::class.java -> MediaFileListViewModel(listAll) as T
            else -> throw IllegalArgumentException()
        }
    }
}