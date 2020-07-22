package vlnny.base.fragment

abstract class BaseFragmentCompanion<F : BaseFragment> {
    val id: String = javaClass.name
    abstract fun newInstance(): F
}