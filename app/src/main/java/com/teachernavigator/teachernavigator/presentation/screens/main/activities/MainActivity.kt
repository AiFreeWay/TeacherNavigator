package com.teachernavigator.teachernavigator.presentation.screens.main.activities

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import com.neovisionaries.ws.client.*
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.ToolbarStyle
import com.teachernavigator.teachernavigator.presentation.screens.main.activities.abstractions.MainView
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.AcMainPresenter
import com.teachernavigator.teachernavigator.presentation.screens.main.presenters.abstractions.IMainPresenter
import com.teachernavigator.teachernavigator.presentation.views.MenuArrowDrawable
import kotlinx.android.synthetic.main.ac_main.*
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), MainView {

    private val mLifecycle: LifecycleRegistry = LifecycleRegistry(this)
    private val mPresenter: IMainPresenter = AcMainPresenter()
    private val mMenuArrowDrawable by lazy { MenuArrowDrawable(this) }
    private val mDrawerToggle by lazy {
        ActionBarDrawerToggle(this, acMainDrawer, acMainToolbar, 0, 0).apply {
            drawerArrowDrawable = mMenuArrowDrawable
            acMainToolbar.setNavigationOnClickListener { onMenuClick() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac_main)
        initToolbar()
        mPresenter.attachView(this)
        mPresenter.loadMenuItemsToViewGroup(acMainLlMenu)
        mPresenter.openStartFragment(savedInstanceState)

        Executors.newCachedThreadPool().submit {

            val factory = WebSocketFactory()
            val socket = factory.createSocket("ws://pronm.pr-solution.ru/chat/stream?token=jazuNxuxxQROQDsqSb7GfELaudsoIe")

            socket.addListener(object : WebSocketAdapter() {
                override fun onError(websocket: WebSocket?, cause: WebSocketException?) {
                    super.onError(websocket, cause)
                    d(javaClass.name, "-> onError -> $cause")
                    cause?.printStackTrace()
                }

                override fun onConnectError(websocket: WebSocket?, exception: WebSocketException?) {
                    super.onConnectError(websocket, exception)
                    d(javaClass.name, "-> onConnectError -> $exception")
                    exception?.printStackTrace()
                }

                override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
                    super.onConnected(websocket, headers)
                    d(javaClass.name, "-> onConnected ->")
                    socket.sendText("Message!")
                }

                override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
                    super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer)
                    d(javaClass.name, "-> onDisconnected ->")
                }

                override fun onTextFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onTextFrame(websocket, frame)
                    d(javaClass.name, "-> onTextFrame ->")
                }

                override fun onTextMessage(websocket: WebSocket?, text: String?) {
                    super.onTextMessage(websocket, text)
                    d(javaClass.name, "-> onTextMessage ->$text")
                }

                override fun onTextMessageError(websocket: WebSocket?, cause: WebSocketException?, data: ByteArray?) {
                    super.onTextMessageError(websocket, cause, data)
                    d(javaClass.name, "-> onTextMessageError ->")
                }

                override fun onSendError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
                    super.onSendError(websocket, cause, frame)
                    d(javaClass.name, "-> onSendError ->")
                }

                override fun onFrameUnsent(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onFrameUnsent(websocket, frame)
                    d(javaClass.name, "-> onFrameUnsent ->")
                }

                override fun onSendingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onSendingFrame(websocket, frame)
                    d(javaClass.name, "-> onSendingFrame ->")
                }

                override fun onBinaryMessage(websocket: WebSocket?, binary: ByteArray?) {
                    super.onBinaryMessage(websocket, binary)
                    d(javaClass.name, "-> onBinaryMessage ->")
                }

                override fun onMessageDecompressionError(websocket: WebSocket?, cause: WebSocketException?, compressed: ByteArray?) {
                    super.onMessageDecompressionError(websocket, cause, compressed)
                    d(javaClass.name, "-> onMessageDecompressionError ->")
                }

                override fun onMessageError(websocket: WebSocket?, cause: WebSocketException?, frames: MutableList<WebSocketFrame>?) {
                    super.onMessageError(websocket, cause, frames)
                    d(javaClass.name, "-> onMessageError ->")
                }

                override fun handleCallbackError(websocket: WebSocket?, cause: Throwable?) {
                    super.handleCallbackError(websocket, cause)
                    d(javaClass.name, "-> handleCallbackError ->")
                }

                override fun onFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onFrame(websocket, frame)
                    d(javaClass.name, "-> onFrame ->")
                }

                override fun onThreadCreated(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
                    super.onThreadCreated(websocket, threadType, thread)
                    d(javaClass.name, "-> onThreadCreated ->")
                }

                override fun onThreadStarted(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
                    super.onThreadStarted(websocket, threadType, thread)
                    d(javaClass.name, "-> onThreadStarted ->${threadType?.name ?: "-"} threadname=${thread?.name ?: "-"}")
                }

                override fun onStateChanged(websocket: WebSocket?, newState: WebSocketState?) {
                    super.onStateChanged(websocket, newState)
                    d(javaClass.name, "-> onStateChanged ->")
                }

                override fun onUnexpectedError(websocket: WebSocket?, cause: WebSocketException?) {
                    super.onUnexpectedError(websocket, cause)
                    d(javaClass.name, "-> onUnexpectedError ->")
                }

                override fun onBinaryFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onBinaryFrame(websocket, frame)
                    d(javaClass.name, "-> onBinaryFrame ->")
                }

                override fun onPingFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onPingFrame(websocket, frame)
                    d(javaClass.name, "-> onPingFrame ->")
                }

                override fun onSendingHandshake(websocket: WebSocket?, requestLine: String?, headers: MutableList<Array<String>>?) {
                    super.onSendingHandshake(websocket, requestLine, headers)
                    d(javaClass.name, "-> onSendingHandshake ->")
                }

                override fun onFrameError(websocket: WebSocket?, cause: WebSocketException?, frame: WebSocketFrame?) {
                    super.onFrameError(websocket, cause, frame)
                    d(javaClass.name, "-> onFrameError ->")
                }

                override fun onCloseFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onCloseFrame(websocket, frame)
                    d(javaClass.name, "-> onCloseFrame ->")
                }

                override fun onContinuationFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onContinuationFrame(websocket, frame)
                    d(javaClass.name, "-> onContinuationFrame ->")
                }

                override fun onFrameSent(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onFrameSent(websocket, frame)
                    d(javaClass.name, "-> onFrameSent ->")
                }

                override fun onThreadStopping(websocket: WebSocket?, threadType: ThreadType?, thread: Thread?) {
                    super.onThreadStopping(websocket, threadType, thread)
                    d(javaClass.name, "-> onThreadStopping ->")
                }

                override fun onPongFrame(websocket: WebSocket?, frame: WebSocketFrame?) {
                    super.onPongFrame(websocket, frame)
                    d(javaClass.name, "-> onPongFrame ->")
                }
            })

            socket.connect()
            d(javaClass.name, "-> trying to connect ->")
        }
//
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDrawerToggle.syncState()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.loadProfile()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mPresenter.navigateBack()
            return true
        }
        return false
    }

    override fun getLifecycle(): LifecycleRegistry = mLifecycle

    override fun getActivity(): AppCompatActivity = this

    override fun getFragmentLayoutId(): Int = R.id.acMainFlBody

    override fun startProgress() {
        acMainProgress.visibility = View.VISIBLE
    }

    override fun stopProgress() {
        acMainProgress.visibility = View.GONE
    }

    override fun openSideMenu() = acMainDrawer.openDrawer(Gravity.START)

    override fun closeSideMenu() = acMainDrawer.closeDrawer(Gravity.START)

    override fun setToolbarTitle(title: String) {
        setTitle(title)
    }

    override fun setToolbarTitle(title: Int) {
        setTitle(title)
    }

    override fun getContext(): Context = this

    private fun initToolbar() {
        setSupportActionBar(acMainToolbar)
        /* acMainDrawer.addDrawerListener(mDrawerToggle) */
        supportFragmentManager.addOnBackStackChangedListener { this.onBackStackChanged() }
        onBackStackChanged()
    }

    private fun onMenuClick() {
        if (!isRootFragment) {
            mPresenter.navigateBack()
        } else {
            if (acMainDrawer.isDrawerOpen(Gravity.START))
                acMainDrawer.closeDrawer(Gravity.START)
            else
                acMainDrawer.openDrawer(Gravity.START)
        }
    }

    private val isRootFragment
        get() = supportFragmentManager.backStackEntryCount <= 0

    private fun onBackStackChanged() {
        hideSoftKeyboard()
        updateBackIndicator()
    }

    private fun updateBackIndicator() {
        mMenuArrowDrawable.animateDrawable(!isRootFragment)
    }

    private fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }


    override fun setToolbarStyle(style: ToolbarStyle) {

//        if (acMainFlBody != null) {

        val lp = acMainFlBody.layoutParams

        if (lp is RelativeLayout.LayoutParams) {

            if (style == ToolbarStyle.Front) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    lp.removeRule(RelativeLayout.BELOW)
                }
            } else {
                lp.addRule(RelativeLayout.BELOW, R.id.acMainToolbar)
            }
        }
//        }
    }

    override fun updateToolbarAlpha(alpha: Float) {
        acMainToolbar?.background?.alpha = (alpha * 255f).toInt()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            acMainAppBarLayout?.elevation = (15 * alpha)
//        }
    }

}
