package com.example.weatherforcast.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weatherforcast.model.Favorite
import com.example.weatherforcast.model.Unit
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("select * from fav_table")
    fun getFavorites() : Flow<List<Favorite>>

    @Query("select * from fav_table where city = :city")
    suspend fun getFavById(city : String) : Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFav(favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFav(favorite: Favorite)

    @Query("delete from fav_table")
    suspend fun deteleAll()

    @Delete
    suspend fun deleteFav(favorite: Favorite)

//    Units
    @Query("select * from settings_table")
    fun getUnits() : Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit : Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)


    @Query("delete from settings_table")
    suspend fun deteleAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)




}