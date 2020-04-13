package com.onpu.bookar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment(R.layout.fragment_main) {

    // вешаем слушателей на кнопки, чтобы можно было переходить на определённые фрагменты
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        find.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionToSearch()) }
        saved.setOnClickListener { findNavController().navigate(MainFragmentDirections.actionToSaved()) }
    }
}