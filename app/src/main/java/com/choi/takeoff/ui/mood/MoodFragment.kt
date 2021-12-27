package com.choi.takeoff.ui.mood

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.choi.takeoff.InputMemoActivity
import com.choi.takeoff.databinding.MoodFragmentBinding

class MoodFragment : DialogFragment() {

    lateinit var size: Point
    var currentProgress: Int? = null

    companion object {
        fun newInstance() = MoodFragment()
    }

    private lateinit var viewModel: MoodViewModel
    private var _binding: MoodFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoodViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = MoodFragmentBinding.inflate(inflater, container, false)
        val seekBar = binding.seekBar
        var listener = SeekBarListener()
        seekBar.setOnSeekBarChangeListener(listener)
        val textMood = binding.textMood
        viewModel.progress.observe(this, {
            textMood.text = it.toString()
        })
        val buttonSubmit = binding.buttonSubmit
        buttonSubmit.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MoodViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        val display =
            (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        params?.height = size.y
        params?.width = size.x
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class SeekBarListener : OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            viewModel.setProgress(p1)
            (activity as InputMemoActivity).mood = p1
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
        }

    }

}