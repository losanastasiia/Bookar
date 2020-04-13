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

    // получаем зависимость репозитория
    @Inject
    lateinit var data: DataRepository
    val bookInfo: MutableLiveData<BookWrapper> = MutableLiveData()
    val eventsLd: MutableLiveData<Events> = MutableLiveData()

    init {
        // метод, вызов которго скажет даггеру, что сюда (в объект этого класса) нужно предоставить зависимости
        ComponentProvider.appComponent.inject(this)
    }

    // метод, который callback-ом вызовет на репозиторий класс, когда получит данные о книге из сети
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

    // метод, который "дёргает" репозиторий, чтобы тот сделал запрос в сеть для получения данных о книге
    fun findBook(title: String) {
        eventsLd.value = Events.StartLoad
        viewModelScope.launch(Dispatchers.IO) {
            data.findBook(title, this@FindBookViewModel)
        }
    }

    // метод, который вызывается из нашего фрагмента, когда пользователь нажимет сохранить книгу
    fun onFavouriteChanged(bookModel: BookModel) {
        viewModelScope.launch(Dispatchers.IO) {
            // если книга уже сохранена и была нажата кнопка сохранения опять,
            // то мы удаляем нашу книгу из локальной базы данных
            if (!bookModel.saved) {
                data.deleteBook(bookModel.id)
                bookModel.saved = false
            } else {
                // если книга не сохранена и была нажата кнопка сохранения,
                // то мы добавляем нашу книгу из локальной базы данных
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