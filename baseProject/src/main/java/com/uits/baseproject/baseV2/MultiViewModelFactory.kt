package com.uits.baseproject.baseV2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MultiViewModelFactory(
    private val creators: Map<Class<out ViewModel>, () -> ViewModel>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator =
            creators[modelClass] ?: throw IllegalArgumentException("Unknown ViewModel class")
        return creator() as T
    }
}