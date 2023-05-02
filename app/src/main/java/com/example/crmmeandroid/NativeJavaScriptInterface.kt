package com.example.crmmeandroid

import android.content.ContentValues
import android.util.Log
import android.webkit.JavascriptInterface
import org.json.JSONObject

const val CONTACTS = "contacts"

class NativeJavaScriptInterface {

    var onContacts: () -> Unit = {}

    @Suppress("unused")
    @JavascriptInterface
    fun postMessage(jsonData: String) {
        if (jsonData == null) {
            Log.d(ContentValues.TAG, "postMessage: ${jsonData}")
        } else {
            val json = JSONObject(jsonData)
            when (val command = json.optString("name")) {
                CONTACTS -> contacts()
                else -> Log.d(ContentValues.TAG, "No function: $command. Add function in ${this::class.simpleName}")
            }
        }
    }

    private fun contacts() = onContacts()
}