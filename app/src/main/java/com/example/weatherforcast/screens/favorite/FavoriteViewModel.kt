package com.example.weatherforcast.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.network.WeatherDbRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor (private val repository : WeatherDbRepo) : ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(
        emptyList()
    )

    val favList = _favList.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged().collect{
                _favList.value = it
            }
        }
    }


    fun insertFav(favorite: Favorite) = viewModelScope.launch { repository.insertFav(favorite) }

    fun updateFav(favorite: Favorite) = viewModelScope.launch {
        repository.updateFav(favorite)
    }

    fun deleteFav(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFav(favorite)
    }




}