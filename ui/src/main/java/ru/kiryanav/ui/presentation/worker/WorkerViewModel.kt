package ru.kiryanav.ui.presentation.worker

import androidx.lifecycle.MutableLiveData
import ru.kiryanav.ui.model.ArticleItem
import vlnny.base.viewModel.BaseViewModel

object WorkerViewModel : BaseViewModel() {
     val newsLiveData = MutableLiveData<List<ArticleItem>>()
}