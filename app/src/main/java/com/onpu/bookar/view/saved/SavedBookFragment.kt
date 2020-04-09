package com.onpu.bookar.view.saved

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.onpu.bookar.R
import kotlinx.android.synthetic.main.fragment_saved_books.*

class SavedBookFragment: Fragment(R.layout.fragment_saved_books) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            setTitle(R.string.saved_books)
            setNavigationIcon(R.drawable.ic_back)
            setOnMenuItemClickListener { findNavController().popBackStack() }
        }
    }
}