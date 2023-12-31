package com.development.gocipes.presentation.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.development.gocipes.core.R
import com.development.gocipes.databinding.ContentLogoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoutDialog : DialogFragment() {

    private var _binding: ContentLogoutBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_MyDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = ContentLogoutBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnCancel.setOnClickListener { dismiss() }
            btnLogout.setOnClickListener {
                viewModel.logout()
            }
        }

        viewModel.isLoggedOut.observe(this) { isLoggedOut ->
            if (isLoggedOut) {
                moveToAuth()
                dismiss()
            }
        }
    }

    private fun moveToAuth() {
        activity?.apply {
            startActivity(
                Intent(
                    requireActivity(),
                    Class.forName("com.development.gocipes.auth.presentation.AuthActivity")
                )
            )
            finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "LogoutDialog"
    }
}