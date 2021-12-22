package com.choi.takeoff.ui.newmemo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.choi.takeoff.R

class NewMemoFragment : Fragment() {

    companion object {
        fun newInstance() = NewMemoFragment()
    }

    private lateinit var viewModel: NewMemoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_new_memo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewMemoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}