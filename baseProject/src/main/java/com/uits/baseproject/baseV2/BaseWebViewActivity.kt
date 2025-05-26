package com.uits.baseproject.baseV2


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.uits.baseproject.R
import com.uits.baseproject.databinding.ActivityBaseWebviewBinding
import com.uits.baseproject.utils.Constants

class BaseWebViewActivity : BaseActivity<ActivityBaseWebviewBinding>() {
    companion object {
        private lateinit var backPressedCallback: OnBackPressedCallback
        fun createIntent(context: Context, url: String): Intent =
            Intent(context, BaseWebViewActivity::class.java).apply {
                putExtra(
                    "URL",
                    url
                )
            }

        private fun checkUrlContainsPackage(context: Context, url: String) {
            val packageKeywords = context.resources.getStringArray(R.array.package_name)

            val matchedKeyword = packageKeywords.firstOrNull { keyword ->
                url.contains(keyword, ignoreCase = true)
            }

            if (matchedKeyword != null) {
                if (isAppInstalled(context, matchedKeyword)) {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                } else {
                    val playStoreIntent = Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=$matchedKeyword".toUri()
                    )
                    playStoreIntent.setPackage("com.android.vending")
                    context.startActivity(playStoreIntent)
                }
            } else {
                Toast.makeText(
                    context,
                    "本アプリで許可されていないアプリを開こうとしました。\n",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        @Suppress("DEPRECATION")
        private fun isAppInstalled(context: Context, packageName: String): Boolean {
            return try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    context.packageManager.getPackageInfo(
                        packageName,
                        PackageManager.PackageInfoFlags.of(0)
                    )
                } else {
                    context.packageManager.getPackageInfo(packageName, 0)
                }
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        private fun launchAppOrGoToPlayStore(
            context: Context,
            appUri: String,
            fallbackPackage: String,
        ) {
            try {
                if (isAppInstalled(context, fallbackPackage)) {
                    val intent = Intent(Intent.ACTION_VIEW, appUri.toUri())
                    context.startActivity(intent)
                } else {
                    val playStoreIntent = Intent(
                        Intent.ACTION_VIEW,
                        "market://details?id=$fallbackPackage".toUri()
                    )
                    playStoreIntent.setPackage("com.android.vending")
                    context.startActivity(playStoreIntent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    context,
                    "本アプリで許可されていないアプリを開こうとしました。\n",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fun handleDeepLink(context: Context, url: String): Boolean {
            Log.d("TAG", "handleDeepLink: $url")
            return if (url.startsWith("http") || url.startsWith("https")) {
                false
            } else {

                return when {
                    url.startsWith("paypay://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "jp.ne.paypay.android.app"
                        )
                        true
                    }

                    url.startsWith("dpayment://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.nttdocomo.keitai.payment"
                        )
                        true
                    }

                    url.startsWith("aupay://") || url.startsWith("auwallet://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "jp.auone.wallet"
                        )
                        true
                    }

                    url.startsWith("weixin://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.tencent.mm"
                        )
                        true
                    }

                    url.startsWith("aeonwalletapp://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "jp.co.aeon.credit.android.wallet"
                        )
                        true
                    }

                    url.startsWith("alipays://") || url.startsWith("alipayhk://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.eg.android.AlipayGphone"
                        )
                        true
                    }

                    url.startsWith("gcash://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.globe.gcash.android"
                        )
                        true
                    }

                    url.startsWith("mercari://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.kouzoh.mercari"
                        )
                        true
                    }

                    url.startsWith("upwrp://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.unionpay"
                        )
                        true
                    }

                    url.startsWith("market://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "com.android.vending"
                        )
                        true
                    }

                    url.startsWith("line://") -> {
                        launchAppOrGoToPlayStore(
                            context,
                            url,
                            "jp.naver.line.android"
                        )
                        true
                    }

                    // handle other custom schemes here click link intent from webview
                    url.startsWith("intent://") -> {
                        try {
                            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            checkUrlContainsPackage(context, url)
                            e.printStackTrace()
                        }
                        true
                    }

                    else -> {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            checkUrlContainsPackage(context, url)
                            e.printStackTrace()
                        }
                        true
                    }
                }
            }
        }
    }

    override val bindingLayoutInflater: (LayoutInflater) -> ActivityBaseWebviewBinding
        get() = ActivityBaseWebviewBinding::inflate

    private fun backPress() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            // If no history, allow normal back press
            finish()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewBindingCreated(savedInstanceState: Bundle?) {
        super.onViewBindingCreated(savedInstanceState)
        binding.txtTitle.text = ""
        binding.webview.settings.apply {
            javaScriptEnabled = true
            setGeolocationEnabled(true)
            domStorageEnabled = true
        }
        val linkHost = intent?.getStringExtra("URL") ?: ""

        binding.imgBtnBack.setOnClickListener {
            backPress()
        }

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onGeolocationPermissionsShowPrompt(
                origin: String?,
                callback: GeolocationPermissions.Callback?,
            ) {
                if (checkLocationPermission()) {
                    // Permission already granted
                    callback?.invoke(origin, true, false)
                } else {
                    // Request permission
                    requestLocationPermission(callback, origin)
                }
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                // Update the activity title with the page title
                title?.let {
                    binding.txtTitle.text = it
                }
            }
        }

        // Set WebViewClient to detect link clicks
        binding.webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?,
            ): Boolean {
                val url = request?.url.toString()
                return handleDeepLink(this@BaseWebViewActivity, url)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                println(url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.visibility = View.VISIBLE

                println(url)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
                println(url)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?,
            ) {
                binding.progressBar.visibility = View.GONE
                val failingUrl = request?.url.toString()
                println("WebViewError : $failingUrl")
                openLinkWithIntent(view?.context, failingUrl)
            }
        }
        binding.webview.loadUrl(linkHost)

        backPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPress()
            }
        }

        onBackPressedDispatcher.addCallback(
            this, backPressedCallback
        )
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission(
        callback: GeolocationPermissions.Callback?,
        origin: String?,
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            Constants.LOCATION_PERMISSION_REQUEST
        )

        // Store callback and origin for use in onRequestPermissionsResult
        this.geolocationCallback = callback
        this.geolocationOrigin = origin
    }

    private var geolocationCallback: GeolocationPermissions.Callback? = null
    private var geolocationOrigin: String? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            Constants.LOCATION_PERMISSION_REQUEST -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission granted
                    geolocationCallback?.invoke(geolocationOrigin, true, false)
                } else {
                    // Permission denied
                    geolocationCallback?.invoke(geolocationOrigin, false, false)
                }
                geolocationCallback = null
                geolocationOrigin = null
            }
        }
    }

    fun openLinkWithIntent(context: Context?, url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context?.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                this@BaseWebViewActivity,
                "本アプリで許可されていないアプリを開こうとしました。\n",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}