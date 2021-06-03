package com.qm.mondia.ui.fragment.songdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.qm.mondia.base.view.BaseFragment
import com.qm.mondia.databinding.FragmentSongDetailsBinding

class SongDetailsFragment : BaseFragment<FragmentSongDetailsBinding, SongDetailsViewModel>() {
    override fun pageTitle(): String = ""
    override val mViewModel: SongDetailsViewModel by viewModels()
    private val args: SongDetailsFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.apply {
            gotData(args)
        }
    }
}