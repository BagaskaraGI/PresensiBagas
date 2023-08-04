package com.example.presensibagas.Data

import androidx.lifecycle.LiveData

class PresensiRepository(private val presensiDao: PresensiDao) {
    val readAllData : LiveData<List<Presensi>> = presensiDao.readAllData()

    suspend fun addPresensiMasuk(presensi: Presensi){
        presensiDao.insertPresensiMasuk(presensi)
    }

    suspend fun addPresensiKeluar(presensi: Presensi){
        presensiDao.insertPresensiKeluar(presensi)
    }
}