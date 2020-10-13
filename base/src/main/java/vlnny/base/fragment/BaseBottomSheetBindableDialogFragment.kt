package vlnny.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetBindableDialogFragment<VDB : ViewDataBinding> :
    BottomSheetDialogFragment() {

    protected lateinit var binding: VDB
    protected var onFragmentCloseListener: (() -> Unit)? = null

    @LayoutRes
    abstract fun layoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }

    fun setOnCloseDialogListener(listener: (() -> Unit)) {
        onFragmentCloseListener = listener
    }

    override fun dismiss() {
        super.dismiss()
        onFragmentCloseListener?.invoke()
    }

}