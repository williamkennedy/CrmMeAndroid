package com.example.crmmeandroid

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.crmmeandroid.base.Api
import com.example.crmmeandroid.fragments.HelloWorldFragment
import com.example.crmmeandroid.fragments.WebFragment
import com.example.crmmeandroid.fragments.WebBottomSheetFragment
import dev.hotwire.turbo.config.TurboPathConfiguration
import dev.hotwire.turbo.session.TurboSessionNavHostFragment
import dev.hotwire.turbo.views.TurboWebView
import kotlin.reflect.KClass

class MainSessionNavHostFragment : TurboSessionNavHostFragment() {

    override var sessionName = "main"
    override var startLocation = Api.rootUrl

    var nativeAppJavaScriptInterface: NativeJavaScriptInterface = NativeJavaScriptInterface()


    override val registeredActivities: List<KClass<out AppCompatActivity>>
        get() = listOf()



    override val registeredFragments: List<KClass<out Fragment>>
        get() = listOf(
            WebFragment::class,
            WebBottomSheetFragment::class,
            HelloWorldFragment::class
        )

    override fun onSessionCreated() {
        super.onSessionCreated()
        session.webView.settings.userAgentString = "Turbo Native Android"
    }

    override fun onCreateWebView(context: Context): TurboWebView {
        val turboWebView = super.onCreateWebView(context)
        bindNativeBridge(turboWebView, context = context)
        return turboWebView
    }

    override val pathConfigurationLocation: TurboPathConfiguration.Location
        get() = TurboPathConfiguration.Location( assetFilePath = "json/configuration.json")

    @SuppressLint("Range")
    private fun bindNativeBridge(webView: TurboWebView, context: Context) {
        nativeAppJavaScriptInterface.onContacts = {
            Handler(Looper.getMainLooper()).post {

                val permissions = arrayOf(
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.QUERY_ALL_PACKAGES
                )

                val requestCode = 123 // This can be any integer value

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED &&
                        context.checkSelfPermission(permissions[1]) == PackageManager.PERMISSION_GRANTED) {
                        // Permissions already granted
                        // Do the contact reading operation here
                    } else {
                        // Permissions not granted
                        requestPermissions(permissions, requestCode)
                    }
                } else {
                    // Permissions not needed in older versions of Android
                    // Do the contact reading operation here
                }

                val contactsList: MutableList<String> = mutableListOf()
                val cursor = context.contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )

                if (cursor?.count ?: 0 > 0) {
                    while (cursor != null && cursor.moveToNext()) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        contactsList.add(name)
                    }
                }

                cursor?.close()

                for (contact in contactsList) {
                    Log.d("Contact", contact)
                    val script = "Bridge.importingContacts('${contact}')"
                    session.webView.evaluateJavascript(script, null)
                }
                Log.d(ContentValues.TAG, "bindNativeBridge onImportContacts")
            }
        }

        webView.addJavascriptInterface(nativeAppJavaScriptInterface, "nativeApp")

        webView.loadData("", "text/html", null)
    }
}