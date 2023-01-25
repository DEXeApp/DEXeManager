package tw.app.dexemanager.ui.apps

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import tw.app.dexemanager.BuildConfig
import tw.app.dexemanager.databinding.ViewManagerAppBinding

class AppManagerView(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    private var listener: OnUpdateClickedListener? = null
    private var _binding: ViewManagerAppBinding? = null
    private val binding get() = _binding!!

    init {
        val inflater = LayoutInflater.from(context)
        _binding = ViewManagerAppBinding.inflate(inflater, this, true)
    }

    private fun setup() {
        binding.appVersionTextView.text = BuildConfig.VERSION_NAME

        binding.updateManagerButton.setOnClickListener {
            if (isListenerAvailable())
                listener!!.onClicked()
        }
    }

    private fun isListenerAvailable(): Boolean {
        return listener != null
    }

    fun isUpdateAvailable(isAvailable: Boolean) {
        if (isAvailable)
            binding.updateManagerButton.visibility = VISIBLE
        else
            binding.updateManagerButton.visibility = GONE
    }

    fun setOnUpdateClickListener(_listener: OnUpdateClickedListener) {
        listener = _listener
    }

    interface OnUpdateClickedListener {
        fun onClicked()
    }
}