package com.example.crmmeandroid.base

import android.view.MenuItem
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.example.crmmeandroid.R
import dev.hotwire.turbo.config.TurboPathConfigurationProperties
import dev.hotwire.turbo.config.context
import dev.hotwire.turbo.nav.TurboNavDestination
import dev.hotwire.turbo.nav.TurboNavPresentationContext

interface NavDestination: TurboNavDestination {
    val menuProgress: MenuItem?
        get() = toolbarForNavigation()?.menu?.findItem(R.id.menu_progress)

    override fun getNavigationOptions(
        newLocation: String,
        newPathProperties: TurboPathConfigurationProperties
    ): NavOptions {
        return when (newPathProperties.context) {
            TurboNavPresentationContext.MODAL -> noAnim()
            else -> super.getNavigationOptions(newLocation, newPathProperties)
        }
    }
    private fun noAnim(): NavOptions {
        return navOptions {  }
    }

}