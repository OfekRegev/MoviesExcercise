package com.ofek.moviesexcercise.ui.scan_movie_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.ofek.moviesexcercise.R

class ScanMovieScreen : AppCompatActivity() {

    private lateinit var barcodeView : DecoratedBarcodeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_movie_screen)
        initViews()
    }

    private fun initViews() {
        barcodeView = findViewById(R.id.barcode_view)
        val formats = mutableListOf<BarcodeFormat>()
        formats.add(BarcodeFormat.QR_CODE)
        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView.decodeContinuous(callback)
    }
    private val callback = BarcodeCallback {
        if (it.text.isNotEmpty()) {

        }
    }
    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }
}
