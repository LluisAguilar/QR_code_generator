package com.android.luis.code.qrcodegenerator

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.luis.code.qrcodegenerator.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix


class MainActivity : AppCompatActivity() {

    private var mQrString = ""

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.let {
            it.generateQrBtn.setOnClickListener {view ->
                mQrString = it.qrStringTv.text.toString()

                encodeAsBitmap(mQrString)?.let { bitmap ->
                    it.qrImage.setImageBitmap(bitmap)
                }
            }
        }

    }

    private fun encodeAsBitmap(mQrString: String): Bitmap? {

        val result: BitMatrix = try {
            MultiFormatWriter().encode(
                mQrString,
                BarcodeFormat.QR_CODE, 250, 250, null
            )
        } catch (iae: IllegalArgumentException) {
            // Unsupported format
            return null
        }
        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, 250, 0, 0, w, h)
        return bitmap
    }
}