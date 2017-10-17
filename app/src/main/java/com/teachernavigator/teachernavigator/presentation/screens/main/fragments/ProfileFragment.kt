package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.FileData
import com.miguelbcr.ui.rx_paparazzo2.entities.Response
import com.tbruyelle.rxpermissions2.RxPermissions
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVHBuilder
import com.teachernavigator.teachernavigator.presentation.adapters.holders.ProfileVHBuilder
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.models.ProfileModel
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.screens.main.fragments.abstractions.ProfileView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IPostActionsController
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IProfilePresenter
import com.teachernavigator.teachernavigator.presentation.utils.argNullable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fmt_profile.*
import ru.lliepmah.lib.UniversalAdapter
import javax.inject.Inject


/**
 * Created by root on 18.09.17
 */
class ProfileFragment : BaseFragment(), ProfileView {

    companion object {

        val FRAGMENT_KEY = "profile_fragment"
        val IS_MY_PROFILE_KEY = "is_my_profile_key"

        val USER_ID_KEY = "user_id_key"

        val GET_PHOTO_REQUEST_CODE = 1
    }

    @Inject
    lateinit var presenter: IProfilePresenter
    @Inject
    lateinit var postController: IPostActionsController

    var mTotalScrolled = 0

    val adapter: UniversalAdapter by lazy {
        UniversalAdapter(
                ProfileVHBuilder(
                        this::onChangeAvatar
                ),
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

    private var mIsMyProfile: Boolean = false

    private val rxPermissions by lazy { RxPermissions(activity) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_profile, container, false).also {
                setHasOptionsMenu(true)
            }

    override fun onStart() {
        super.onStart()
        updateAlpha()
        getParentView().setToolbarTitle("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mParentScreenComponent.inject(this)

        presenter.attachView(this)
        postController.attachView(this)

        mIsMyProfile = argNullable(IS_MY_PROFILE_KEY) ?: false
        val userId = argNullable(USER_ID_KEY) ?: -1

        presenter.initialLoad(mIsMyProfile, userId)

        acProfileSwipeLayout.setOnRefreshListener(presenter::refresh)
        acProfileSwipeLayout.setColorSchemeResources(R.color.colorAccent)
        acProfileRvList.layoutManager = LinearLayoutManager(context)
        acProfileRvList.adapter = adapter

        acProfileRvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                mTotalScrolled += dy
                updateAlpha()
            }
        })
    }

    private fun updateAlpha() {
        getParentView().updateToolbarAlpha(minOf(mTotalScrolled.toFloat() / context.resources.getDimension(R.dimen.toolbar), 1F))
    }

    override fun getToolbarStyle() = ToolbarStyle.Front

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        postController.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        if (mIsMyProfile) {
            inflater?.inflate(R.menu.exit_menu, menu)
        } else {
            inflater?.inflate(R.menu.subscribe_menu, menu)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_exit -> exit()
            R.id.action_subscribe -> presenter.profile?.let(postController::onSubscribe)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updatePost(post: PostModel) = adapter.notifyItemChanged(adapter.indexOf(post))

    override fun updateProfile(profile: ProfileModel) {
        adapter.replace(0, profile)
        adapter.notifyItemChanged(0)
    }

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

    private fun exit() {
        AlertDialog.Builder(context)
                .setTitle(getString(R.string.you_want_exit))
                .setPositiveButton(R.string.yes, { a, b ->
                    presenter.exit()
                    a.dismiss()
                })
                .setNegativeButton(R.string.no, { a, b -> a.dismiss() })
                .setCancelable(true)
                .show()
    }

    private fun onChangeAvatar(profileModel: ProfileModel) {
        rxPermissions
                .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .map { if (!it) throw Error("Permission Denied") }
                .flatMap { RxPaparazzo.single(this@ProfileFragment).usingFiles() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFileAttached, this::onAttachError)
    }


    private fun onAttachError(error: Throwable) {
        error.printStackTrace()
    }

    private fun onFileAttached(response: Response<ProfileFragment, FileData>) {
        val data = response.data()
        if (response.resultCode() == Activity.RESULT_OK && data != null) {
            presenter.uploadPhoto(data.file.name, data.file.path, data.mimeType)
        }
    }

}