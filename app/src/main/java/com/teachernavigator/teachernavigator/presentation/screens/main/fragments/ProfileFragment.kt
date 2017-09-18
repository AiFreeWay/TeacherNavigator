package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.*
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.Profile
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.FmtProfilePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter

/**
 * Created by root on 18.09.17.
 */
class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        val FRAGMENT_KEY = "profile_fragment"
    }

    private val mPresenter: IProfilePresenter = FmtProfilePresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fmt_profile, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        mPresenter.attachView(this)
        mPresenter.getProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

    override fun loadProfile(profile: Profile) {

    }
}