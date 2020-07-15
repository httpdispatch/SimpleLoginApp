package com.example.loginapp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.common.data.succeeded
import com.example.loginapp.common.lifecycle.ViewModelKey
import com.example.loginapp.databinding.FragmentLoginBinding
import dagger.Binds
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginFragment : DaggerFragment() {
    private lateinit var viewDataBinding: FragmentLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container,
            false
        )
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState
            .filter { it.status.succeeded }
            .filter { it.loggedIn }
            .onEach {
                findNavController().navigate(ActionOnlyNavDirections(R.id.action_loginFragment_to_profileFragment))
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.viewModel = viewModel
        viewDataBinding.viewState = viewModel.viewState.asLiveData()
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }

    @dagger.Module
    abstract class Module {
        @ContributesAndroidInjector(modules = [])
        abstract fun contribute(): LoginFragment


        @Binds
        @IntoMap
        @ViewModelKey(LoginViewModel::class)
        internal abstract fun bindViewModel(viewModel: LoginViewModel): ViewModel
    }
}