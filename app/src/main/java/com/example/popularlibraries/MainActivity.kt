package com.example.popularlibraries

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.popularlibraries.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    val counters = mutableListOf (0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.btnCounter1?.setOnClickListener {
            binding?.btnCounter1?.text = (++counters[0]).toString()
        }
        binding?.btnCounter2?.setOnClickListener {
            binding?.btnCounter2?.text = (++counters[1]).toString()
        }
        binding?.btnCounter3?.setOnClickListener {
            binding?.btnCounter3?.text = (++counters[2]).toString()
        }
        initViews()
    }

    fun initViews() {
        binding?.btnCounter1?.text = counters[0].toString()
        binding?.btnCounter2?.text = counters[1].toString()
        binding?.btnCounter3?.text = counters[2].toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putIntArray("counters", counters.toIntArray())
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putIntArray("counters", counters.toIntArray())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val countersArray = savedInstanceState.getIntArray("counters")
        countersArray?.toList()?.let {
            counters.clear()
            counters.addAll(it)
        }
        initViews()
    }
}