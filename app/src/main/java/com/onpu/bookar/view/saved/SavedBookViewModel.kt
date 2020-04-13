package com.onpu.bookar.view.saved

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

class SavedBookViewModel : ViewModel(), DataRepository.RequestCallback {

    @Inject
    lateinit var repo: DataRepository

    val booksLd: MutableLiveData<LoadingProcess> = MutableLiveData()

    init {
        ComponentProvider.appComponent.inject(this)
    }

    fun initLoading() {
        booksLd.value = LoadingProcess.Loading
        viewModelScope.launch(Dispatchers.IO) {

            val booksId = repo.getBooksId()
            if (booksId.isEmpty())
                booksLd.postValue(LoadingProcess.Loaded(mutableListOf()))
            else
                booksId.forEach {
                    repo.findBookById(it, this@SavedBookViewModel)
                }
        }
    }

    override fun onRequestSuccess(book: BookWrapper?) {
        booksLd.value = book?.let {
            booksLd.value?.let { loadingProcess ->
                val books = when(loadingProcess) {
                    LoadingProcess.Loading -> mutableListOf()
                    is LoadingProcess.Loaded -> loadingProcess.books
                }
                it.books?.let {
                books.removeAll(it)
                books.addAll(it)
            }
                LoadingProcess.Loaded(books.apply { it.books?.forEach { it.saved = true } })
            }
        } ?: LoadingProcess.Loaded(mutableListOf())
    }

    override fun onRequestFailed() {
        booksLd.value = LoadingProcess.Loaded(mutableListOf())
    }

    fun onSaveClicked(bookModel: BookModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!bookModel.saved)
                repo.deleteBook(bookModel.id)
            else
                repo.saveBook(
                    Book(
                        bookModel.id,
                        bookModel.details.images?.thumbnail,
                        bookModel.details.title
                    )
                )
        }
    }

    sealed class LoadingProcess() {
        object Loading : LoadingProcess()
        class Loaded(val books: MutableList<BookModel>) : LoadingProcess()
    }
}