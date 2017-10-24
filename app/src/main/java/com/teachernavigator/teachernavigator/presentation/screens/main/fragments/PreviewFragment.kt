package com.teachernavigator.teachernavigator.presentation.screens.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.adapters.holders.InfoPostVH
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.screens.common.BaseFragment
import com.teachernavigator.teachernavigator.presentation.utils.argNullable
import kotlinx.android.synthetic.main.fmt_preview_post.*

/**
 * Created by Arthur Korchagin on 18.10.17
 */
class PreviewFragment : BaseFragment() {

    companion object {
        val FRAGMENT_KEY = "preview_fragment"

        val KEY_TITLE = "key_title"
        val KEY_TEXT = "key_text"
        val KEY_TAGS = "key_tags"
        val KEY_FILE = "key_file"
    }

    private val infoPostVH by lazy { InfoPostVH(fmtPreviewPostView, {}, {}, {}, {}, {}, null, null, null, {}, {}) }


    override fun onStart() {
        super.onStart()
        getParentView().setToolbarTitle(R.string.preview)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fmt_preview_post, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model = PostModel(
                id = 0,
                title = argNullable(KEY_TITLE) ?: "",
                text = argNullable(KEY_TEXT) ?: "",
                created = getString(R.string.just_now),
                tags = arguments.getStringArrayList(KEY_TAGS) ?: emptyList(),
                count_likes = 0,
                count_dislikes = 0,
                vote = true,
                isMine = true,
                count_comments = 0,
                comments = emptyList(),
                authorId = 121,
                authorName = "Вы",
                authorAvatar = null,
                type = PostType.post,
                file = argNullable(KEY_FILE),
                shortTitle = "",
                choices = emptyList(),
                pollPassed = false
        )
        infoPostVH.bind(model)
    }


}