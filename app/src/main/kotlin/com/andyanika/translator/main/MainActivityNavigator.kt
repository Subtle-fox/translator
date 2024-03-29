package com.andyanika.translator.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.andyanika.translator.R
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class MainActivityNavigator constructor(activity: MainActivity) :
    AppNavigator(activity, activity.fragmentContainerId) {

    public override fun back() {
        activity.setTitle(R.string.app_name)
        super.back()
    }

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        var animationEnter = 0
        var animationExit = 0
        if (currentFragment != null) {
            animationEnter = android.R.anim.slide_in_left
            animationExit = android.R.anim.slide_out_right
        }
        fragmentTransaction.setCustomAnimations(animationEnter, animationExit, animationEnter, animationExit)
    }
}
