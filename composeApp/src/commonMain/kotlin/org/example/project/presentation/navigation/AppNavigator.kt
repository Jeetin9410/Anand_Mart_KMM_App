package org.example.project.presentation.navigation


import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator

object AppNavigator {
    // For simple navigation
    fun navigateTo(navigator: Navigator, screen: Screen) {
        navigator.push(screen)
    }

    fun navigateBack(navigator: Navigator) {
        navigator.pop()
    }

    fun replaceAll(navigator: Navigator, screen: Screen) {
        navigator.replaceAll(screen)
    }

    /*// For tab navigation (if needed)
    fun selectTab(tabNavigator: TabNavigator, tab: ScreenTab) {
        tabNavigator.current = tab
    }*/
}