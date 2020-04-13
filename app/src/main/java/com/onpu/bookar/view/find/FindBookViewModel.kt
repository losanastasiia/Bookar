package com.onpu.bookar.view.find

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onpu.bookar.dagger.ComponentProvider
import com.onpu.bookar.model.data.BookModel
import com.onpu.bookar.model.data.BookWrapper
import com.onpu.bookar.model.database.Book
import com.onpu.bookar.model.repo.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// класс вью модели, который содержит LiveData, на которую подписывается фрагмент,
// также класс отвечает за использование слоя репозитория и получение данных оттуда
class FindBookViewModel : ViewModel(), DataRepository.RequestCallback {

    @Inject
    lateinit var data: DataRepository
    val bookInfo: MutableLiveData<BookWrapper> = MutableLiveData()
    val eventsLd: MutableLiveData<Events> = MutableLiveData()

    init {
        ComponentProvider.appComponent.inject(this)
    }

    override fun onRequestSuccess(book: BookWrapper?) {
        viewModelScope.launch(Dispatchers.IO) {
            val savedBooks = data.getBooks()
            book?.let { booksWrapper ->
                booksWrapper.books?.forEach {
                    if (savedBooks.contains(Book(it.id, it.details.images?.thumbnail, it.details.title))) {
                        it.saved = true
                    }
                }?: kotlin.run { booksWrapper.books = emptyList()}
                bookInfo.postValue(booksWrapper)
            }
            eventsLd.postValue(Events.Finished)
        }
    }

    override fun onRequestFailed() {
        eventsLd.value = Events.Error
    }

    fun findBook(title: String) {
        eventsLd.value = Events.StartLoad
        viewModelScope.launch(Dispatchers.IO) {
            data.findBook(title, this@FindBookViewModel)
        }
    }

    fun onFavouriteChanged(bookModel: BookModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!bookModel.saved) {
                data.deleteBook(bookModel.id)
                bookModel.saved = false
            } else {
                bookModel.saved = true
                data.saveBook(
                    Book(
                        bookModel.id,
                        bookModel.details.images?.thumbnail,
                        bookModel.details.title
                    )
                )
            }
        }
    }

    sealed class Events {
        object StartLoad : Events()
        object Finished : Events()
        object Error : Events()
    }
}