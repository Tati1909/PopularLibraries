package com.example.popularlibraries

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.popularlibraries.databinding.ActivityMainBinding
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

class MainActivity : MvpAppCompatActivity(), MainView {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //объявляем Presenter и делегируем его создание и хранение через делегата moxyPresenter,
    //которому отдаём функцию, создающую Presenter.
    private val presenter by moxyPresenter { Presenter(Model()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCounter1.setOnClickListener { presenter.counter1Click() }
        binding.btnCounter2.setOnClickListener { presenter.counter2Click() }
        binding.btnCounter3.setOnClickListener { presenter.counter3Click() }
    }

    override fun setButton1Text( text: String) {
        binding.btnCounter1.text = text
    }

    override fun setButton2Text(text: String) {
        binding.btnCounter2.text = text
    }

    override fun setButton3Text(text: String) {
        binding.btnCounter3.text = text
    }
}