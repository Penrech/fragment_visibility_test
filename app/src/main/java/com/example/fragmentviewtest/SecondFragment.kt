package com.example.fragmentviewtest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.fragmentviewtest.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        Log.d(SecondFragment::class.java.simpleName, "On pause")
    }

    private fun initListener() {
        binding.secondButtonForward.setOnClickListener {
            this.parentFragmentManager.commit {
                addToBackStack(null)
                add(R.id.main_fragment_container, ThirdFragment())
            }
        }

        binding.secondButtonBack.setOnClickListener {
            this.parentFragmentManager.popBackStack()
        }
    }
}