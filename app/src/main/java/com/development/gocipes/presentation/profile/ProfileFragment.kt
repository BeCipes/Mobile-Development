package com.development.gocipes.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.development.gocipes.core.data.remote.response.auth.UserResult
import com.development.gocipes.core.utils.Extensions.showImage
import com.development.gocipes.core.utils.Result
import com.development.gocipes.databinding.FragmentProfileBinding
import com.development.gocipes.presentation.profile.LogoutDialog.Companion.TAG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserObserver()
    }

    private fun getUserObserver() {
        viewModel.getCurrentUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Error -> {
                    Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {}
                is Result.Success -> {
                    setupView(result.data)
                }
            }
        }
    }

    private fun setupView(userResult: UserResult) {
        val urlPhoto =
            "https://t4.ftcdn.net/jpg/00/64/67/63/360_F_64676383_LdbmhiNM6Ypzb3FM4PPuFP9rHe7ri8Ju.jpg"
        val userName = "${userResult.firstName} ${userResult.lastName}"

        binding?.apply {
            ivProfileFotoProfil.showImage(
                requireActivity(), userResult.photo ?: urlPhoto
            )
            edtProfileEmailUser.setText(userResult.email)
            edtProfileUsernameUser.setText(userName)
            edtProfilePasswordUser.setText(userName)

            btnProfileFav.setOnClickListener {
                navigateToFavorite()
            }

            btnProfileInfoAplikasi.setOnClickListener {
                navigateToInfoApp()
            }

            binding?.btnProfileEdit?.setOnClickListener {
                onClickEdit()
            }
            binding?.btnProfileSimpan?.setOnClickListener {
                onClickSave()
            }

            btnLogout.setOnClickListener {
                val dialog = LogoutDialog()
                dialog.show(childFragmentManager, TAG)
            }
        }
    }

    private fun navigateToInfoApp() {
        val action = ProfileFragmentDirections.actionProfileFragmentToInfoAppFragment()
        findNavController().navigate(action)
    }

    private fun navigateToFavorite() {
        val action = ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClickEdit() {
        binding?.apply {
            btnProfileEdit.visibility = View.GONE
            btnProfileSimpan.visibility = View.VISIBLE

            edtProfileEmailUser.isEnabled = true
            edtProfilePasswordUser.isEnabled = true
            edtProfileUsernameUser.isEnabled = true
        }
    }

    private fun onClickSave() {
        binding?.apply {
            btnProfileEdit.visibility = View.VISIBLE
            btnProfileSimpan.visibility = View.GONE

            edtProfileEmailUser.isEnabled = false
            edtProfilePasswordUser.isEnabled = false
            edtProfileUsernameUser.isEnabled = false
        }
    }
}