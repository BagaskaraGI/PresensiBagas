package com.example.presensibagas.Data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PresensiViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData : LiveData<List<Presensi>>
    private val repository : PresensiRepository

    init {
        val presensiDao = PresensiDatabase.getDatabase(application).PresensiDao()
        repository = PresensiRepository(presensiDao)
        readAllData = repository.readAllData
    }

    fun insertPresensiMasuk(presensi: Presensi){
        viewModelScope.launch(Dispatchers.IO){
            repository.addPresensiMasuk(presensi)
        }
    }

    fun insertPresensiKeluar(presensi: Presensi){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPresensiKeluar(presensi)
        }
    }


}