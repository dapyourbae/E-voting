package com.kpu.pendataan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pemilih")
data class Pemilih(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nik: String,
    val nama: String,
    val noHp: String,
    val jenisKelamin: String,   // "Laki-Laki" atau "Perempuan"
    val tanggalPendataan: String,
    val alamat: String,
    val latitude: Double,
    val longitude: Double,
    val fotoPath: String
)
