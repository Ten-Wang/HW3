package tw.teng.hw3.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import tw.teng.hw3.R
import tw.teng.hw3.databinding.LoginFragmentBinding
import tw.teng.hw3.ui.MainViewModel
import tw.teng.hw3.util.AppUtils


class LoginFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var binding: LoginFragmentBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.btnLogin.setOnClickListener {
            if (AppUtils.checkAccount(binding.editAccount.text.toString()))
                viewModel.login()
            else {
                Toast.makeText(context, "Only can use User1 ~ User8!", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginEvent.observe(viewLifecycleOwner, {
            if(it){
                AppUtils.hideKeyboard(activity as Activity)
                val bundle = bundleOf("userName" to binding.editAccount.text.toString())
                navController.navigate(R.id.action_LoginFragment_to_ChatFragment, bundle)
            }
        })
    }
}

