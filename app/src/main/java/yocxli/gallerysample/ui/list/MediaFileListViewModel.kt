package yocxli.gallerysample.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import yocxli.gallerysample.domain.repository.MediaRepository
import kotlin.coroutines.CoroutineContext

class MediaFileListViewModel(private val mediaRepository: MediaRepository) : ViewModel(), CoroutineScope {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val list = mediaRepository.listAll()

    fun onStart() {
        launch {
            isLoading.value = true
            mediaRepository.sync()
            isLoading.value = false
        }
    }
}