package com.uits.baseproject.baselifecycle

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LifecycleRegistryOwner
import androidx.lifecycle.ViewModelProvider


/**
 * BaseLifecycleActivity
 * Copyright © 2019 UITS CO.,LTD
 * Created PHUQUY on 7/9/20.
 **/
@Suppress("LeakingThis")
abstract class BaseLifecycleActivity<T : AndroidViewModel> : AppCompatActivity(), LifecycleRegistryOwner {

    abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy { ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(viewModelClass) }

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry
}