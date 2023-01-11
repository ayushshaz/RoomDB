package com.example.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user:User)

    @Insert
    fun insertAll(list:List<User>)

    @Delete
    fun delete(user:User)

    @Query("select * from user")
    fun getAllUser():LiveData<List<User>>

    @Query("select * from user where age = :age")
    fun getUserWithAge(age:Int):List<User>
}