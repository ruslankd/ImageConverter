package com.example.imageconverter.converter

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ConverterView : MvpView {
    fun openFileDialog()
    fun setFilePath(path: String)
    fun showToast(message: String)
}