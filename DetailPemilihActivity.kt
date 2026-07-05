package com.kpu.pendataan

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kpu.pendataan.data.AppDatabase
import com.kpu.pendataan.databinding.ActivityDetailPemilihBinding
import kotlinx.coroutines.launch
import java.io.File

class DetailPemilihActivity : AppCompatActivity() {

    private val db by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailPemilihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)

        binding.tvNik.text = "NIK        : ${intent.getStringExtra("nik")}"
        binding.tvNama.text = "Nama       : ${intent.getStringExtra("nama")}"
        binding.tvNoHp.text = "No.HP      : ${intent.getStringExtra("noHp")}"
        binding.tvJk.text = "JK         : ${intent.getStringExtra("jenisKelamin")}"
        binding.tvTanggal.text = "Tanggal    : ${intent.getStringExtra("tanggal")}"
        binding.tvAlamat.text = "Alamat     : ${intent.getStringExtra("alamat")}"

        val fotoPath = intent.getStringExtra("fotoPath")
        if (!fotoPath.isNullOrEmpty()) {
            val file = File(fotoPath)
            if (file.exists()) {
                binding.ivFoto.setImageURI(Uri.fromFile(file))
            }
        }

        binding.btnHapus.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Yakin ingin menghapus data pemilih ini?")
                .setPositiveButton("Hapus") { _, _ ->
                    if (id != -1) {
                        lifecycleScope.launch {
                            db.pemilihDao().deleteById(id)
                            Toast.makeText(this@DetailPemilihActivity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
