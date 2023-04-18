package com.example.toyexchange.Presentation.ToysViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.toyexchange.db.ToyDatabase

@Suppress("UNCHECKED_CAST")
class DetailsToyViewModelFactory (private val toyDatabase: ToyDatabase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsToyViewModel(toyDatabase) as T
    }
}
