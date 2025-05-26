package com.uits.baseproject.baseV2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    // LiveData for showing a loading indicator
    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // LiveData for handling errors
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // Function to set loading state
    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    // Function to handle errors
    protected fun setError(message: String) {
        _error.value = message
    }

    // Clear resources if necessary
    override fun onCleared() {
        super.onCleared()
        // Add cleanup logic if needed
    }
}