package com.teachernavigator.teachernavigator.presentation.screens.main.activities

import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.application.di.components.DaggerParentScreenComponent
import com.teachernavigator.teachernavigator.application.di.modules.ParentScreenModule
import com.teachernavigator.teachernavigator.application.utils.rootComponent
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ProfileVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import kotlinx.android.synthetic.main.ac_profile.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject


/**
 * Created by root on 18.09.17
 */
class ProfileActivity : AppCompatActivity(), ProfileView {

    override fun setToolbarStyle(front: ToolbarStyle) = Unit
    override fun updateToolbarAlpha(alpha: Float) = Unit

    companion object {
        val IS_MY_PROFILE_KEY = "is_my_profile_key"

        val USER_ID_KEY = "user_id_key"

        val GET_PHOTO_REQUEST_CODE = 1
    }

    private val mParentScreenComponent by lazy {
        DaggerParentScreenComponent.builder()
                .rootComponent(rootComponent())
                .parentScreenModule(ParentScreenModule(this))
                .build()
    }

    @Inject
    lateinit var presenter: IProfilePresenter
    @Inject
    lateinit var postController: IPostActionsController


    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                ProfileVHBuilder(),
                InfoPostVHBuilder(
                        postController::onLike,
                        postController::onDislike,
                        postController::onComments,
                        null,
                        null,
                        postController::onReadMore,
                        null,
                        null,
                        null,
                        null
                ))
    }

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)

    private var mIsMyProfile: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_profile)
        initToolbar()
        mParentScreenComponent.inject(this)
        presenter.attachView(this)
        postController.attachView(this)

        mIsMyProfile = intent.getBooleanExtra(IS_MY_PROFILE_KEY, false)
        val userId = intent.getIntExtra(USER_ID_KEY, -1)

        presenter.initialLoad(mIsMyProfile, userId)

        acProfileSwipeLayout.setOnRefreshListener(presenter::refresh)
        acProfileSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        acProfileRvList.layoutManager = LinearLayoutManager(this)
        acProfileRvList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        postController.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (mIsMyProfile) {
            menuInflater.inflate(R.menu.exit_menu, menu)
        } else {
            menuInflater.inflate(R.menu.subscribe_menu, menu)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> presenter.navigateBack()
            R.id.action_exit -> exit()
            R.id.action_subscribe -> presenter.subscribe()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updatePost(post: PostModel) = adapter.notifyItemChanged(adapter.indexOf(post))

    override fun setProfile(data: Pair<ProfileModel, List<PostModel>>) {
        adapter.clear()
        adapter.add(data.first)
        adapter.addAll(data.second)
        adapter.notifyDataSetChanged()
    }


    override fun startProgress() {
        acProfileProgress.visibility = View.VISIBLE
        acProfileSwipeLayout?.isRefreshing = true
    }

    override fun stopProgress() {
        acProfileProgress.visibility = View.GONE
        acProfileSwipeLayout?.isRefreshing = false
    }

    override fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun setToolbarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }

    override fun getFragmentLayoutId(): Int = 0

    override fun getContext(): Context = this

    override fun getActivity(): AppCompatActivity = this

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    private fun initToolbar() {
        setSupportActionBar(acProfileToolbar)
        setToolbarTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun exit() {
        AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.you_want_exit))
                .setPositiveButton(R.string.yes, { a, b ->
                    presenter.exit()
                    a.dismiss()
                })
                .setNegativeButton(R.string.no, { a, b -> a.dismiss() })
                .setCancelable(true)
                .show()
    }


    override fun getParentView() = this

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun showToast(textRes: Int) {
        Toast.makeText(this, textRes, Toast.LENGTH_LONG).show()
    }

}