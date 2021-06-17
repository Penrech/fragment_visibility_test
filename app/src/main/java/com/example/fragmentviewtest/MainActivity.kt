package com.example.fragmentviewtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.fragmentviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setInitialFragment()
    }


    private fun setInitialFragment(){
        supportFragmentManager.commit {
            add(R.id.main_fragment_container, InitialFragment::class.java, null)
        }
    }
}