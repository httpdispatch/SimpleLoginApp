package com.example.loginapp.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
import com.example.loginapp.databinding.FragmentProfileBinding
import dagger.Binds
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@ExperimentalCoroutinesApi
class ProfileFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentProfileBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ProfileViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container,
            false
        )
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState
            .map { it.userName }
            .distinctUntilChanged()
            .onEach {
                (activity as AppCompatActivity?)!!.supportActionBar!!.title = it
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.viewState
            .filter { it.status.succeeded }
            .filter { !it.loggedIn }
            .onEach {
                findNavController().navigate(ActionOnlyNavDirections(R.id.action_profileFragment_to_loginFragment))
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
        abstract fun contribute(): ProfileFragment


        @Binds
        @IntoMap
        @ViewModelKey(ProfileViewModel::class)
        internal abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel
    }
}