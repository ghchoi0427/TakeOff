package com.choi.takeoff.ui.newmemo

import androidx.lifecycle.*
import com.choi.takeoff.db.MemoRepository
import com.choi.takeoff.db.entity.Memo
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class NewMemoViewModel(private val repository: MemoRepository) : ViewModel() {

    val allMemos: LiveData<List<Memo>> = repository.allMemos.asLiveData()

    fun insert(memo: Memo) = viewModelScope.launch { repository.insert(memo) }

}

class MemoViewModelFactory(private val repository: MemoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMemoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewMemoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}