package com.kpu.pendataan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kpu.pendataan.adapter.PemilihAdapter
import com.kpu.pendataan.data.AppDatabase
import com.kpu.pendataan.databinding.ActivityDataPemilihBinding
import kotlinx.coroutines.launch

class DataPemilihActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataPemilihBinding
    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataPemilihBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPemilih.layoutManager = LinearLayoutManager(this)
        muatData()
    }

    override fun onResume() {
        super.onResume()
        muatData()
    }

    private fun muatData() {
        lifecycleScope.launch {
            val list = db.pemilihDao().getAll()
            binding.rvPemilih.adapter = PemilihAdapter(list) { pemilih ->
                val intent = Intent(this@DataPemilihActivity, DetailPemilihActivity::class.java)
                intent.putExtra("id", pemilih.id)
                intent.putExtra("nik", pemilih.nik)
                intent.putExtra("nama", pemilih.nama)
                intent.putExtra("noHp", pemilih.noHp)
                intent.putExtra("jenisKelamin", pemilih.jenisKelamin)
                intent.putExtra("tanggal", pemilih.tanggalPendataan)
                intent.putExtra("alamat", pemilih.alamat)
                intent.putExtra("fotoPath", pemilih.fotoPath)
                startActivity(intent)
            }
        }
    }
}
