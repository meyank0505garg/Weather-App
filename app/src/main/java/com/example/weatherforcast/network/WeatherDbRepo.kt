package com.example.weatherforcast.network

import com.example.weatherforcast.data.WeatherDao
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepo @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites() : Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun insertFav(favorite: Favorite) = weatherDao.insertFav(favorite)
    suspend fun updateFav(favorite: Favorite) = weatherDao.updateFav(favorite)
    suspend fun  deleteAllFav() = weatherDao.deteleAll()
    suspend fun deleteFav(favorite : Favorite) = weatherDao.deleteFav(favorite)
    suspend fun getfav(city: String) = weatherDao.getFavById(city)

//    Units

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deteleAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)

}