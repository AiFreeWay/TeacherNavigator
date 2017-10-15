package com.teachernavigator.teachernavigator.presentation.screens.main.activities

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.ParentScreenComponent
import com.teachernavigator.teachernavigator.presentation.models.ProfilePostConteainer
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.AcProfilePresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import java.io.File


/**
 * Created by root on 18.09.17
 */
class ProfileActivity : AppCompatActivity(), ProfileView {

    companion object {
        val IS_MY_PROFILE_KEY = "is_my_profile_key"
        val USER_ID_KEY = "user_id_key"
        val GET_PHOTO_REQUEST_CODE = 1
    }

    @BindView(R.id.ac_profile_toolbar)
    lateinit var mToolbar: Toolbar
    @BindView(R.id.ac_profile_progress)
    lateinit var mProgressBar: ProgressBar
    @BindView(R.id.ac_profile_rv_data)
    lateinit var mRvData: RecyclerView

    private val mPresenter: IProfilePresenter = AcProfilePresenter()
    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private var mAvatar: ImageView? = null
    private var mIsMyProfile = false
    private var mUserId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)
        ButterKnife.bind(this)
        initToolbar()
        mPresenter.attachView(this)
        mIsMyProfile = intent.getBooleanExtra(IS_MY_PROFILE_KEY, false)
        mUserId = intent.getIntExtra(USER_ID_KEY, -1)

//        mAdapter = StrategyMultiRvAdapter(ProfileAdapterStrategy(mProfileFacade, mIsMyProfile, mPresenter.getPostControllerFacade()))
//        mRvData.layoutManager = LinearLayoutManager(this)
//        mRvData.adapter = mAdapter
    }

    override fun onStart() {
        super.onStart()
        if (mIsMyProfile)
            mPresenter.getProfile()
        else
            mPresenter.getProfile(mUserId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GET_PHOTO_REQUEST_CODE && mAvatar != null) {
            val imageUri = data!!.data
            mPresenter.uploadPhoto(mAvatar!!, File(imageUri.toString()))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mIsMyProfile)
            menuInflater.inflate(R.menu.exit_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> mPresenter.navigateBack()
            R.id.action_exit -> exit()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadProfile(data: List<ProfilePostConteainer>) {
//        mAdapter.loadData(data)
    }

    override fun startProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        mProgressBar.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.setTitle(title)
    }

    override fun setToolbarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    override fun getFragmentLayoutId(): Int = 0

    override fun getContext(): Context = this

    override fun getActivity(): AppCompatActivity = this

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        setToolbarTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun getPicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GET_PHOTO_REQUEST_CODE)
    }

    private fun exit() {
        AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.you_want_exit))
                .setPositiveButton(R.string.yes, { a, b ->
                    mPresenter.exit()
                    a.dismiss()
                })
                .setNegativeButton(R.string.no, { a, b -> a.dismiss() })
                .setCancelable(true)
                .show()
    }
}