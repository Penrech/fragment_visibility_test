package com.example.fragmentviewtest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.fragmentviewtest.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    //todo Create a global variable covered to improve optimization
    private val fragmentLifecycleCallbacks = object : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)

            v.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (v.height > 0 && v.width > 0) {
                        Log.d(
                            SecondFragment::class.java.simpleName,
                            "New Fragment height is ${v.height}"
                        )
                        showToast(isFragmentVisible(f))
                        v.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }

            })
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            super.onFragmentDetached(fm, f)
            showToast(isFragmentVisible(fm.fragments.lastOrNull()))
        }
    }

    private fun showToast(fragmentVisible: Boolean) {
        Toast.makeText(
            requireContext(),
            "Fragment visible $fragmentVisible",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun isFragmentVisible(actualFragment: Fragment?): Boolean {
        if (this == actualFragment) return true
        actualFragment?.let {
            val fragmentView = this.view
            val actualFragmentView = it.view

            if (fragmentView != null && actualFragmentView != null) {
                //Variables for debugging
                val actualFragmentViewHeight = actualFragmentView.height
                val actualFragmentViewWidth = actualFragmentView.width
                val actualFragmentViewX = actualFragmentView.x
                val actualFragmentViewY = actualFragmentView.y
                val fragmentViewHeight = fragmentView.height
                val fragmentViewWidth = fragmentView.width
                val fragmentViewX = fragmentView.x
                val fragmentViewY = fragmentView.y
                val isActualFragmentCoveringThisOne =
                    actualFragmentViewHeight >= fragmentViewHeight &&
                            actualFragmentViewWidth >= fragmentViewWidth &&
                            actualFragmentViewX >= fragmentViewX &&
                            actualFragmentViewY >= fragmentViewY
                return !isActualFragmentCoveringThisOne
            }
        }
        return false
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
        this.parentFragmentManager.registerFragmentLifecycleCallbacks(
            fragmentLifecycleCallbacks,
            false
        )
        initListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this.parentFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifecycleCallbacks)
        _binding = null
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

        binding.secondButtonPartial.setOnClickListener {
            this.parentFragmentManager.commit {
                addToBackStack(null)
                add(R.id.main_fragment_container, PartialFragment())
            }
        }
    }
}