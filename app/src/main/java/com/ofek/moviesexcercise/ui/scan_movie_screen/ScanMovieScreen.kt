package com.ofek.moviesexcercise.ui.scan_movie_screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.ofek.moviesexcercise.R
import com.ofek.moviesexcercise.presentation.scan_movies_screen.ScanMoviesScreenPresenter
import com.ofek.moviesexcercise.presentation.scan_movies_screen.ScanMoviesScreenView
import com.ofek.moviesexcercise.ui.di.GlobalDependencyProvider


class ScanMovieScreen : AppCompatActivity(), ScanMoviesScreenView {

    companion object {
        private const val CAMERA_REQUEST_CODE = 53
        private const val REQUEST_PERMISSION_SETTINGS = 52
    }

    private lateinit var barcodeView: DecoratedBarcodeView
    private lateinit var loadingLay: ViewGroup

    private val scanMoviesScreenPresenter: ScanMoviesScreenPresenter =
        GlobalDependencyProvider.provideScanMoviesScreenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_movie_screen)
        scanMoviesScreenPresenter.attachView(this)
        initViews()
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED) {
            barcodeView.decodeContinuous(callback)
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)) {
                // the user marked "do not ask again" on the permission dialog
                showPermissionRequiredDialog()
            } else {
                // the permission should be requested
                requestPermissions(arrayOf( Manifest.permission.CAMERA),ScanMovieScreen.CAMERA_REQUEST_CODE)
            }
        }
    }

    private fun initViews() {
        barcodeView = findViewById(R.id.barcode_view)
        val formats = mutableListOf<BarcodeFormat>()
        formats.add(BarcodeFormat.QR_CODE)
        barcodeView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView.decodeContinuous(callback)
        loadingLay = findViewById(R.id.loading_lay)
        loadingLay.visibility = View.GONE
    }

    /**
     *callback for the barcode scanner events
     */
    private val callback = BarcodeCallback {
        if (it.text.isNotEmpty()) {
            scanMoviesScreenPresenter.processScanResult(it.text)
        }
    }

    override fun onPause() {
        super.onPause()
        // there's no point in changing the barcodeview state if there's no permission to use the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED) {
            barcodeView.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        // there's no point in changing the barcodeview state if there's no permission to use the camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED) {
            barcodeView.resume()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PERMISSION_SETTINGS) {
            // if the user granted the permission in the settings it's safe to activate the scanner
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PERMISSION_GRANTED) {
                barcodeView.decodeContinuous(callback)
            } else {
                // otherwise the barcode cannot be scanned so the activity should terminate
                finish()
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                // the barcode scanner can safely start
                barcodeView.decodeContinuous(callback)
            } else {
                showPermissionRequiredDialog()
            }
        }
    }

    private fun showPermissionRequiredDialog() {
        AlertDialog.Builder(this)
            .setTitle("Attention")
            .setMessage("The camera permission is required in order to scan barcode. Please grant the permission in the settings ")
            .setCancelable(false)
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivityForResult(intent, REQUEST_PERMISSION_SETTINGS)
            }.setNegativeButton("Cancel") { _, _ ->
                setResult(Activity.RESULT_CANCELED)
                finish()
            }.show()
    }


    override fun onStartDecodingBarcode() {
        loadingLay.visibility = View.VISIBLE
        // stopping the scanner in order to prevent multiple reads
        barcodeView.pauseAndWait()
    }

    override fun onBarcodeInvalid() {
        loadingLay.visibility = View.GONE
        AlertDialog.Builder(this)
            .setTitle("Failed")
            .setMessage("The barcode content isn't movie, please scan other barcode")
            .setCancelable(false)
            .setNeutralButton("OK") { dialog, _ ->
                dialog.dismiss()
                barcodeView.resume()
            }.show()
    }

    override fun onMovieAdded() {
        AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage("The movie added to the database")
            .setCancelable(false)
            .setNeutralButton("OK") { dialog, _ ->
                dialog.dismiss()
                setResult(Activity.RESULT_OK)
                finish()
            }.show()
    }

    override fun onMovieAlreadyExist() {
        loadingLay.visibility = View.GONE
        barcodeView.resume()
        Snackbar
            .make(barcodeView,"Movie already exists",LENGTH_LONG)
            .show()
    }
}
