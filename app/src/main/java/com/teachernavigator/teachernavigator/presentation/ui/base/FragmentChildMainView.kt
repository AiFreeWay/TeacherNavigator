package com.teachernavigator.teachernavigator.presentation.ui.base

import android.support.v4.app.Fragment
import com.teachernavigator.teachernavigator.presentation.ui.main.activities.abstractions.MainView

/**
 * Created by root on 14.08.17.
 */
open class FragmentChildMainView : Fragment() {


    fun getMainView(): MainView = activity as MainView
}