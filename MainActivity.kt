package com.kpu.pendataan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kpu.pendataan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnInformasi.setOnClickListener {
            startActivity(Intent(this, InformasiActivity::class.java))
        }
        binding.btnFormEntry.setOnClickListener {
            startActivity(Intent(this, FormEntryActivity::class.java))
        }
        binding.btnLihatData.setOnClickListener {
            startActivity(Intent(this, DataPemilihActivity::class.java))
        }
        binding.btnKeluar.setOnClickListener {
            finishAffinity()
        }
    }
}
