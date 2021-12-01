package com.example.weatherapp.di

import com.example.weatherapp.presentation.viewmodel.ViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<ViewModel> {
        ViewModel(interactor = get())
    }
}