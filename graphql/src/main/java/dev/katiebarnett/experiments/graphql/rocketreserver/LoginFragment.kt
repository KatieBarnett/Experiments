package dev.katiebarnett.experiments.graphql.rocketreserver

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dev.katiebarnett.experiments.graphql.R
import dev.katiebarnett.experiments.graphql.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitProgressBar.visibility = View.GONE
        binding.submit.setOnClickListener {
            val email = binding.email.text.toString()
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailLayout.error = getString(R.string.invalid_email)
                return@setOnClickListener
            }

            binding.submitProgressBar.visibility = View.VISIBLE
            binding.submit.visibility = View.GONE
            lifecycleScope.launchWhenResumed {
                val response = try {
                    apolloClient(requireContext()).mutation(LoginMutation(email = email)).execute()
                } catch (e: Exception) {
                    null
                }

                val token = response?.data?.login?.token
                if (token == null || response.hasErrors()) {
                    binding.submitProgressBar.visibility = View.GONE
                    binding.submit.visibility = View.VISIBLE
                    return@launchWhenResumed
                }

                User.setToken(requireContext(), token)
                findNavController().popBackStack()
            }
        }
    }
}
