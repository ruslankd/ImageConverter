package com.example.imageconverter.converter

import android.app.AlertDialog
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.imageconverter.databinding.FragmentConverterBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.File
import java.io.FilenameFilter


class ConverterFragment : MvpAppCompatFragment(), ConverterView {

    companion object {
        fun newInstance(): Fragment = ConverterFragment()

        private const val FILE_TYPE = ".jpg"
        private const val TAG = "myLog"
    }

    private lateinit var fileList: Array<String>
    private lateinit var path: File
    private var chosenFile: String? = null

    val presenter: ConverterPresenter by moxyPresenter { ConverterPresenter() }

    private var vb: FragmentConverterBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        FragmentConverterBinding.inflate(inflater, container, false).also {
            vb = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb?.btnSelect?.setOnClickListener {
            presenter.clickBtnSelect()
        }

        vb?.btnConvert?.setOnClickListener {
            presenter.convert()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        vb = null
    }

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun openFileDialog() {
        path = File("${Environment.getExternalStorageDirectory()}")
        loadFileList()

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Choose your file")
        builder.setItems(fileList) { _, which ->
            chosenFile = fileList[which]
            presenter.setSelectFilePath(path.absolutePath + File.separator + chosenFile)
        }
        builder.show()
    }

    override fun setFilePath(path: String) {
        vb?.tvFilePath?.text = path
    }

    private fun loadFileList() {
        try {
            path.mkdirs()
        } catch (e: SecurityException) {
            Log.e(TAG, "unable to write on the sd card $e")
        }
        fileList = if (path.exists()) {
            val filter = FilenameFilter { _, filename ->
                filename.contains(FILE_TYPE)
            }
            path.list(filter)
        } else {
            arrayOf("")
        }
    }

}