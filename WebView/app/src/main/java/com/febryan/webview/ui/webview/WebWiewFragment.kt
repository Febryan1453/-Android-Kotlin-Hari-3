package com.febryan.webview.ui.webview

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Message
import android.view.*
import androidx.fragment.app.Fragment
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.febryan.webview.R
import com.febryan.webview.databinding.WebWiewFragmentBinding
import android.widget.LinearLayout

import android.widget.TextView

import android.widget.ProgressBar
import android.widget.Toast


class WebWiewFragment : Fragment() {

    companion object {
        fun newInstance() = WebWiewFragment()
    }

    private lateinit var viewModel: WebWiewViewModel
    private var webViewBinding: WebWiewFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.web_wiew_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = WebWiewFragmentBinding.bind(view)
        webViewBinding = binding

        val loading = setProgressDialog(activity, "Loading IDN.ID")

        binding.webView.settings.javaScriptEnabled = true

        binding.webView.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                loading.show()

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                loading.dismiss()

            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)

                loading.dismiss()

                binding.imgError.visibility = View.VISIBLE
                binding.webView.visibility = View.GONE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    Toast.makeText(context, error?.description, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context, "WEB ERROR", Toast.LENGTH_SHORT).show()
                }

            }

        }

        binding.webView.loadUrl("https://www.idn.id/")


    }

    fun setProgressDialog(context: Context?, message:String): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = message
        tvText.setTextColor(Color.parseColor("#000000"))
        tvText.textSize = 20.toFloat()
        tvText.layoutParams = llParam

        ll.addView(progressBar)
        ll.addView(tvText)

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        builder.setView(ll)

        val dialog = builder.create()
        val window = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window?.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window?.attributes = layoutParams
        }
        return dialog
    }

}