package com.onpu.bookar.view.find

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onpu.bookar.model.data.BookWrapper
import com.onpu.bookar.model.repo.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FindBookViewModel: ViewModel(), NetworkRepository.RequestCallback {

    private val networking = NetworkRepository()
    val bookInfo: MutableLiveData<BookWrapper> = MutableLiveData()
    val eventsLd: MutableLiveData<Events> = MutableLiveData()

    override fun onRequestSuccess(book: BookWrapper?) {
        eventsLd.value = Events.Finished
        book?.let { bookInfo.value = it }
    }

    override fun onRequestFailed() {
        eventsLd.value = Events.Error
    }

    fun findBook(title: String) {
        eventsLd.value = Events.StartLoad
        viewModelScope.launch(Dispatchers.IO) {
            networking.findBook(title, this@FindBookViewModel)
        }
    }

    sealed class Events {
        object StartLoad: Events()
        object Finished: Events()
        object Error: Events()
    }
}