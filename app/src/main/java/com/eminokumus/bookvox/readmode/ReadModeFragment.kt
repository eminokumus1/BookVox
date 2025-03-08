package com.eminokumus.bookvox.readmode

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.eminokumus.bookvox.MainActivity
import com.eminokumus.bookvox.databinding.FragmentReadModeBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ReadModeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentReadModeBinding

    private var isFullScreen = false

    private val args: ReadModeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadModeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggleBarsVisibilityWhenScrolled()

        setOnClickListeners()
        makeBottomSheetFullscreen()

    }

    private fun toggleBarsVisibilityWhenScrolled() {
        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                hideBars()
            } else if (scrollY < oldScrollY) {
                showBars()
            }
        }
    }

    private fun setOnClickListeners(){
        setBackButtonOnClickListener()
        setRootOnClickListener()
    }

    private fun setBackButtonOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }

    private fun setRootOnClickListener() {
        binding.root.setOnClickListener {
            toggleFullScreen()
        }
    }

    private fun toggleFullScreen() {
        isFullScreen = !isFullScreen

        if (isFullScreen) {
            hideBars()
        } else {
            showBars()
        }
    }

    private fun hideBars() {
        binding.appBarLayout.animate().translationY(-binding.appBarLayout.height.toFloat()).setDuration(200).start()
        binding.bottombar.animate().translationY(binding.bottombar.height.toFloat()).setDuration(200).start()

    }

    private fun showBars() {
        binding.appBarLayout.animate().translationY(0f).setDuration(200).start()
        binding.bottombar.animate().translationY(0f).setDuration(200).start()
    }

    private fun makeBottomSheetFullscreen() {
        dialog?.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            }
        }
    }
}