package com.example.fragmentviewtest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.fragmentviewtest.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val onBackStackChangedListener = FragmentManager.OnBackStackChangedListener {
        Log.d(
            SecondFragment::class.java.simpleName,
            "lastFragment: ${parentFragmentManager.fragments.lastOrNull()}"
        )
        parentFragmentManager.fragments.lastOrNull()?.let {
            Toast.makeText(
                requireContext(),
                "Fragment visible ${this == it}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isFragmentVisible(actualFragment: Fragment?) =
        this == actualFragment

    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        Log.d(SecondFragment::class.java.simpleName, "Menu visible $menuVisible")
    }

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
        this.parentFragmentManager.addOnBackStackChangedListener(onBackStackChangedListener)
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.parentFragmentManager.removeOnBackStackChangedListener(onBackStackChangedListener)
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