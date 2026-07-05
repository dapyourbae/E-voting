package com.kpu.pendataan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kpu.pendataan.databinding.ActivityInformasiBinding

class InformasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInformasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
