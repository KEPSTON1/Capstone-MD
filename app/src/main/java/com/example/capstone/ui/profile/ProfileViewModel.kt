package com.example.capstone.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.api.response.User
import com.example.capstone.pref.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _profile = MutableLiveData<User?>()
    val profile: LiveData<User?> = _profile

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = userRepository.getProfile(token)
                if (response.success) {
                    _profile.value = response.user
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to get profile"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}