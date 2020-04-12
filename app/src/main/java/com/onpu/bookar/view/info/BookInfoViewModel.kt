package com.onpu.bookar.view.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onpu.bookar.dagger.ComponentProvider
import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.model.data.BookWrapper
import com.onpu.bookar.model.repo.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookInfoViewModel: ViewModel(), DataRepository.RequestCallback {

    val bookInfo: MutableLiveData<LoadingProcess> = MutableLiveData()

    @Inject
    lateinit var repository: DataRepository

    init {
        ComponentProvider.appComponent.inject(this)
    }

    override fun onRequestSuccess(book: BookWrapper?) {
        book?.let {
            bookInfo.postValue(LoadingProcess.Loaded(it.books.first()))
        }?:bookInfo.postValue(LoadingProcess.Loaded(null))
    }

    override fun onRequestFailed() {
        bookInfo.postValue(LoadingProcess.Loaded(null))
    }

    fun getBookInfo(id: String) {
        bookInfo.postValue(LoadingProcess.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.findBookById(id, this@BookInfoViewModel)
        }
    }

    sealed class LoadingProcess {
        object Loading : LoadingProcess()
        class Loaded(val books: BookModel?) : LoadingProcess()
    }
}