package yocxli.gallerysample.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import yocxli.gallerysample.domain.entity.MediaFile
import yocxli.gallerysample.domain.usecase.ListAll
import kotlin.coroutines.CoroutineContext

class MediaFileListViewModel(val listAll: ListAll) : ViewModel(), CoroutineScope {

    val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var list: MutableLiveData<List<MediaFile>> = MutableLiveData()

    fun onStart() {
        launch {
            isLoading.value = true
            list.value = listAll.execute().value
            isLoading.value = false
        }
    }
}