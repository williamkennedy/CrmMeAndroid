package com.example.crmmeandroid.base



class Api {
    companion object {
        private val baseUrl = if (true)
          "http://10.0.2.2:3003"
        else
            ""

        val rootUrl = "$baseUrl/"
    }

}