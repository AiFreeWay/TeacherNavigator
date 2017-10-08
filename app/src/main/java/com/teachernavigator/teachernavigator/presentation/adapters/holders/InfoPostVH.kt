package layout

import android.support.v4.content.ContextCompat
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.greenfrvr.hashtagview.HashtagView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.utils.find
import com.teachernavigator.teachernavigator.presentation.utils.listenClickBy
import com.teachernavigator.teachernavigator.presentation.utils.setImageOrHide
import com.teachernavigator.teachernavigator.presentation.utils.setTextOrHide
import ru.lliepmah.HolderBuilder
import ru.lliepmah.lib.DefaultViewHolder

/**
 * Created by lliepmah on 07.10.17
 */
@HolderBuilder(R.layout.v_info_post)
class InfoPostVH(itemView: View,
                 onLikeListener: OnLikeListener?,
                 onDislikeListener: OnDislikeListener?,
                 onCommentsListener: OnCommentsListener?,
                 onSaveListener: OnSaveListener?,
                 onSubscribeListener: OnSubscribeListener?,
                 onReadMoreListener: OnReadMoreListener?

) : DefaultViewHolder<PostModel>(itemView) {

    private val context = itemView.context

    private val ivAvatar: ImageView = itemView.find(R.id.v_post_iv_avatar)
    private val tvAuthorName: TextView = itemView.find(R.id.v_post_tv_author_name)
    private val tvPostTime: TextView = itemView.find(R.id.v_post_tv_post_time)
    private val ivSubscribe: ImageView = itemView.find(R.id.v_post_iv_subscribe)
    private val tvTitle: TextView = itemView.find(R.id.v_post_tv_title)
    private val tvText: TextView = itemView.find(R.id.v_post_tv_text)
    private val btnMore: Button = itemView.find(R.id.v_post_btn_more)
    private val lMore: View = itemView.find(R.id.v_post_ll_more_and_complain)
    private val tvComplain: TextView = itemView.find(R.id.v_post_tv_complain)
    private val llChoices: View = itemView.find(R.id.v_post_ll_choices)
    private val hvHasttags: HashtagView = itemView.find(R.id.v_post_hv_hasttags)
    private val tvLike: TextView = itemView.find(R.id.v_post_tv_like)
    private val tvDislike: TextView = itemView.find(R.id.v_post_tv_dislike)
    private val tvComments: TextView = itemView.find(R.id.v_post_tv_comments)
    private val ivSave: ImageView = itemView.find(R.id.v_post_iv_save)

    private var mModel: PostModel? = null

    init {

        tvLike listenClickBy onLikeListener andReturnModel { mModel }
        tvDislike listenClickBy onDislikeListener andReturnModel { mModel }
        tvComments listenClickBy onCommentsListener andReturnModelOrHide  { mModel }
        ivSave listenClickBy onSaveListener andReturnModel { mModel }
        ivSubscribe listenClickBy onSubscribeListener andReturnModelOrHide { mModel }
        btnMore listenClickBy onReadMoreListener andReturnModelOrHide { mModel }

        // TODO Temp fix
        lMore.visibility = btnMore.visibility
    }

    override fun bind(model: PostModel?) {
        mModel = model

        ivAvatar.setImageOrHide(model?.authorAvatar)
        tvAuthorName.setTextOrHide(model?.authorName)
        tvPostTime.setTextOrHide(model?.created)
        ivSubscribe.visibility = if ((model?.authorId ?: -1) > 0) View.VISIBLE else View.GONE
        tvTitle.setTextOrHide(model?.title)
        tvText.setTextOrHide(model?.text)
        // TODO: tvComplain
        // TODO: llChoices
        hvHasttags.setData(model?.tags ?: emptyList())

        tvLike.text = "${model?.count_likes ?: 0}"
        tvDislike.text = "${model?.count_dislikes ?: 0}"
        tvComments.text = "${model?.count_comments ?: 0}"

        tvLike.setCompoundDrawablesWithIntrinsicBounds(getLikeDrawable(model?.vote), null, null, null)
        tvDislike.setCompoundDrawablesWithIntrinsicBounds(getDislikeDrawable(model?.vote), null, null, null)
    }

    private fun getLikeDrawable(vote: Boolean?) =
            ContextCompat.getDrawable(context, if (vote == true) R.drawable.ic_like_active else R.drawable.ic_like)

    private fun getDislikeDrawable(vote: Boolean?) =
            ContextCompat.getDrawable(context, if (vote == false) R.drawable.ic_dislike_active else R.drawable.ic_dislike)

}

typealias OnLikeListener = (PostModel) -> Unit
typealias OnDislikeListener = (PostModel) -> Unit
typealias OnCommentsListener = (PostModel) -> Unit
typealias OnSaveListener = (PostModel) -> Unit
typealias OnSubscribeListener = (PostModel) -> Unit
typealias OnReadMoreListener = (PostModel) -> Unit