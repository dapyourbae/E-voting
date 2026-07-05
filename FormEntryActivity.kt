package com.kpu.pendataan

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.kpu.pendataan.data.AppDatabase
import com.kpu.pendataan.data.Pemilih
import com.kpu.pendataan.databinding.ActivityFormEntryBinding
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class FormEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormEntryBinding
    private var fotoUri: Uri? = null
    private var fotoFile: File? = null

    private val db by lazy { AppDatabase.getDatabase(this) }

    private val requestCameraPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) bukaKamera() else toast("Izin kamera ditolak")
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && fotoUri != null) {
            binding.ivFoto.setImageURI(fotoUri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val today = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        binding.etTanggal.setText(today)

        binding.btnCekNik.setOnClickListener { cekNik() }
        binding.btnAmbilFoto.setOnClickListener { mintaIzinKamera() }
        binding.btnSubmit.setOnClickListener { simpanData() }
    }

    private fun cekNik() {
        val nik = binding.etNik.text.toString().trim()
        if (nik.length != 16) {
            toast("NIK harus 16 digit")
            return
        }
        lifecycleScope.launch {
            val existing = db.pemilihDao().getByNik(nik)
            if (existing != null) {
                binding.etNama.setText(existing.nama)
                binding.etNoHp.setText(existing.noHp)
                binding.etAlamat.setText(existing.alamat)
                if (existing.jenisKelamin == "Laki-Laki") binding.rbLaki.isChecked = true
                else binding.rbPerempuan.isChecked = true
                toast("Data pemilih sudah ada, silakan perbarui jika perlu")
            } else {
                toast("NIK belum terdaftar, silakan lengkapi form")
            }
        }
    }

    private fun mintaIzinKamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            bukaKamera()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun bukaKamera() {
        val fileName = "FOTO_${System.currentTimeMillis()}.jpg"
        fotoFile = File(getExternalFilesDir("Pictures"), fileName)
        fotoUri = FileProvider.getUriForFile(
            this, "${packageName}.fileprovider", fotoFile!!
        )
        takePictureLauncher.launch(fotoUri)
    }

    private fun simpanData() {
        val nik = binding.etNik.text.toString().trim()
        val nama = binding.etNama.text.toString().trim()
        val noHp = binding.etNoHp.text.toString().trim()
        val tanggal = binding.etTanggal.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val jk = when (binding.rgJenisKelamin.checkedRadioButtonId) {
            R.id.rbLaki -> "Laki-Laki"
            R.id.rbPerempuan -> "Perempuan"
            else -> ""
        }

        if (nik.length != 16 || nama.isEmpty() || noHp.isEmpty() || jk.isEmpty() || alamat.isEmpty()) {
            toast("Mohon lengkapi semua data")
            return
        }

        val pemilih = Pemilih(
            nik = nik,
            nama = nama,
            noHp = noHp,
            jenisKelamin = jk,
            tanggalPendataan = tanggal,
            alamat = alamat,
            latitude = 0.0,
            longitude = 0.0,
            fotoPath = fotoFile?.absolutePath ?: ""
        )

        lifecycleScope.launch {
            db.pemilihDao().insert(pemilih)
            toast("Data pemilih berhasil disimpan")
            finish()
        }
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
