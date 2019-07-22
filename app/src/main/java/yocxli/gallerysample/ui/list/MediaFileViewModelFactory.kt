package yocxli.gallerysample.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import yocxli.gallerysample.domain.repository.MediaRepository

class MediaFileViewModelFactory(val mediaRepository: MediaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MediaFileListViewModel::class.java -> MediaFileListViewModel(mediaRepository) as T
            else -> throw IllegalArgumentException()
        }
    }
}