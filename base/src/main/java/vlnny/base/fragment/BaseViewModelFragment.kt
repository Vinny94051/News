package vlnny.base.fragment

import androidx.lifecycle.ViewModelProvider
import vlnny.base.viewModel.BaseViewModel

abstract class BaseViewModelFragment : BaseFragment() {

    protected lateinit var viewModel : BaseViewModel

    private inline fun<reified VM : BaseViewModel> initVM(){
        viewModel = ViewModelProvider(this)[VM::class.java]
    }
}