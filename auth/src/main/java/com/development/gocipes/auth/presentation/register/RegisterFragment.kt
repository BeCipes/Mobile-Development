package com.development.gocipes.auth.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.development.gocipes.auth.R
import com.development.gocipes.auth.databinding.FragmentRegisterBinding
import com.development.gocipes.core.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        binding?.contentRegister?.apply {

            btnRegister.setOnClickListener {
                val firstName = tilFirstName.editText?.text.toString().trim()
                val lastName = tilLastName.editText?.text.toString().trim()
                val email = tilEmail.editText?.text.toString().trim()
                val password = tilPassword.editText?.text.toString().trim()

                viewModel.apply {
                    saveEmail(email)
                    savePassword(password)
                }
                registerObserver(firstName, lastName, email, password)
            }
        }
    }

    private fun registerObserver(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModel.register(firstName, lastName, email, password)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Error -> {
                        binding?.contentRegister?.apply {
                            btnRegister.text = getString(R.string.register)
                            progressCircular.visibility = View.GONE
                        }
                        Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT).show()
                    }
                    is Result.Loading -> {
                        binding?.contentRegister?.apply {
                            btnRegister.text = ""
                            progressCircular.visibility = View.VISIBLE
                        }
                    }
                    is Result.Success -> {
                        navigateToLogin()
                        Toast.makeText(
                            requireActivity(),
                            "Daftar akun berhasil! Silahkan Login terlebih dahulu",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun navigateToLogin() {
        val action =
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}