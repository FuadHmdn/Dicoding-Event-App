package com.fuad.dicoding_event.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fuad.dicoding_event.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("select * from event")
    fun getEventFavorite(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM event WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteEvent(event: EventEntity)

    @Delete
    suspend fun deleteFavoriteEvent(event: EventEntity)

}