package tw.teng.hw3.model

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import tw.teng.hw3.ui.MainViewModel

val viewModelModule = module {

    // Specific viewModel pattern to tell Koin how to build CountriesViewModel
    viewModel {
        MainViewModel()
    }
}