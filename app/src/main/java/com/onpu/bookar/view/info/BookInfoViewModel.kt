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

// класс вью модели, который содержит LiveData, на которую подписывается фрагмент,
// также класс отвечает за использование слоя репозитория и получение данных оттуда
class BookInfoViewModel: ViewModel(), DataRepository.RequestCallback {

    val bookInfo: MutableLiveData<LoadingProcess> = MutableLiveData()

    @Inject
    lateinit var repository: DataRepository

    init {
        ComponentProvider.appComponent.inject(this)
    }

    // метод, который callback-ом вызовет на репозиторий класс, когда получит данные о книге из сети
    override fun onRequestSuccess(book: BookWrapper?) {
        book?.let {
            bookInfo.postValue(LoadingProcess.Loaded(it.books?.first()))
        }?:bookInfo.postValue(LoadingProcess.Loaded(null))
    }

    // метод, который callback-ом вызовет на репозиторий класс, когда произойдёт ошибка зарпоса в сеть
    override fun onRequestFailed() {
        bookInfo.postValue(LoadingProcess.Loaded(null))
    }

    // метод, который вызывается фрагментом для получения информации о книге по её id
    fun getBookInfo(id: String) {
        bookInfo.postValue(LoadingProcess.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            repository.findBookById(id, this@BookInfoViewModel)
        }
    }

    // класс представляющий процесс загрузки данных из сети/бд
    // Создан, чтобы можно было использовать конструкцию when()
    sealed class LoadingProcess {
        object Loading : LoadingProcess()
        class Loaded(val books: BookModel?) : LoadingProcess()
    }
}