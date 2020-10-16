package com.noice.viewmodel

import androidx.lifecycle.ViewModel
import com.noice.repository.DiscoverRepository

class DiscoverViewModel : ViewModel() {

    private val repository = DiscoverRepository()

    fun getBanners() = repository.getBanners()
}