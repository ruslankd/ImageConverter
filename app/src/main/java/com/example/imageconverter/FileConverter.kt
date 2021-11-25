package com.example.imageconverter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class FileConverter(var filePath: String) {

    fun convertCompletable(): Completable =
        Completable.create { emitter ->
            convert().let {
                if (it) {
                    emitter.onComplete()
                } else {
                    emitter.onError(Exception("Error"))
                }
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun convert(): Boolean {
        try {
            val original = BitmapFactory.decodeStream(FileInputStream(filePath))
            val out = ByteArrayOutputStream()
            original.compress(Bitmap.CompressFormat.PNG, 100, out)
            val bitmapdata: ByteArray = out.toByteArray()

            val pngFile =
                File(filePath.dropLastWhile { it != '.' } + "png").apply { createNewFile() }
            val fos = FileOutputStream(pngFile)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}