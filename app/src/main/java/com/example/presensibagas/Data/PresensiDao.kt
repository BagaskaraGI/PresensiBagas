package com.example.presensibagas.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PresensiDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPresensiMasuk(presensi: Presensi)

    @Query("SELECT * FROM presensi_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Presensi>>

    @Update
    fun insertPresensiKeluar(presensi: Presensi)
}