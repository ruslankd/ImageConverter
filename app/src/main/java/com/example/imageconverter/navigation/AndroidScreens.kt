package com.example.imageconverter.navigation

import com.example.imageconverter.converter.ConverterFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

class AndroidScreens : IScreens {
    override fun converterScreen() = FragmentScreen { ConverterFragment.newInstance() }
}