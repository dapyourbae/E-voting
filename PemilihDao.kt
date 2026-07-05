package com.kpu.pendataan.data

import androidx.room.*

@Dao
interface PemilihDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pemilih: Pemilih)

    @Update
    suspend fun update(pemilih: Pemilih)

    @Query("SELECT * FROM pemilih ORDER BY id DESC")
    suspend fun getAll(): List<Pemilih>

    @Delete
    suspend fun delete(pemilih: Pemilih)

    @Query("DELETE FROM pemilih WHERE id = :id")
    suspend fun deleteById(id: Int)

    // Dipakai untuk mengecek apakah NIK sudah pernah didata (sesuai alur di gambar)
    @Query("SELECT * FROM pemilih WHERE nik = :nik LIMIT 1")
    suspend fun getByNik(nik: String): Pemilih?
}
