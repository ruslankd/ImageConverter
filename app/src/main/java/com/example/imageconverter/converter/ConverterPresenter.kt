package com.example.imageconverter.converter

import com.example.imageconverter.FileConverter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter

class ConverterPresenter : MvpPresenter<ConverterView>() {

    private var filePath = ""
    var disposable: Disposable? = null

    fun clickBtnSelect() {
        viewState.openFileDialog()
    }

    fun setSelectFilePath(chosenFile: String) {
        filePath = chosenFile
        viewState.setFilePath(chosenFile)
    }

    fun convert() {
        disposable = FileConverter(filePath).convertCompletable().subscribe({
            viewState.showToast("File converted!")
        }, {
            viewState.showToast("Error: ${it.message}")
        })
        viewState.setFilePath("")
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

}