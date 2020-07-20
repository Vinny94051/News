package vlnny.base.fragment

import androidx.databinding.ViewDataBinding


abstract class BaseFragmentCompanion<F : BaseFragment> {
    val id: String = javaClass.name
    abstract fun newInstance(): F
}