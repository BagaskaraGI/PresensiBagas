package com.example.presensibagas.Data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "presensi_table")
data class Presensi(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val tanggalMasuk:String,
    val jamMasuk:String,
    val lokasiMasuk:String,
    val tanggalKeluar:String,
    val jamKeluar:String,
    val lokasiKeluar:String,
    val statusWFH:String,
) : Parcelable
