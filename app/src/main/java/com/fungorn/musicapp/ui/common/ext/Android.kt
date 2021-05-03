package com.fungorn.musicapp.ui.common.ext

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.annotation.Keep
import androidx.annotation.MainThread
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@MainThread
inline fun <reified T : ViewBinding> Fragment.viewBinding(
    noinline binder: (view: View) -> T
) = ViewBindingDelegate(binder) { viewLifecycleOwner }

class ViewBindingDelegate<T : ViewBinding>(
    private val binder: (view: View) -> T,
    private val viewLifecycleOwnerProvider: () -> LifecycleOwner
) : ReadOnlyProperty<Fragment, T> {
    private val lifecycleObserver: BindingLifecycleObserver = BindingLifecycleObserver()
    private val viewLifecycleOwner: LifecycleOwner
        get() = viewLifecycleOwnerProvider.invoke()
    private var binding: T? = null

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding ?: kotlin.run {
            viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
            binder.invoke(thisRef.requireView())
                .also { binding = it }
        }
    }

    private inner class BindingLifecycleObserver : LifecycleObserver {
        private val handler = Handler(Looper.getMainLooper())

        @Keep
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            viewLifecycleOwner.lifecycle.removeObserver(this)
            handler.post {
                binding = null
            }
        }
    }
}

fun Fragment.applyStatusBarColor(@ColorRes color: Int) = activity?.apply {
    window.statusBarColor = ContextCompat.getColor(this, color)
}

inline fun <reified T> Fragment.arg(key: String): Lazy<T?> = lazy { this.arguments?.get(key) as? T }

inline fun <reified T> Fragment.requireArg(key: String): Lazy<T> =
    lazy { this.requireArguments().get(key) as T }

inline fun EditText.doOnFocusLost(
    crossinline action: (text: CharSequence?) -> Unit
) = setOnFocusChangeListener { _, hasFocus ->
    if (!hasFocus) action(text)
}
