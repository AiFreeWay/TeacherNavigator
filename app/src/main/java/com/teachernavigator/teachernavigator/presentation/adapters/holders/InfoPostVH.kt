package com.teachernavigator.teachernavigator.presentation.adapters.holders

import android.support.v4.content.ContextCompat
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.greenfrvr.hashtagview.HashtagView
import com.teachernavigator.teachernavigator.R
import com.teachernavigator.teachernavigator.domain.models.PostType
import com.teachernavigator.teachernavigator.presentation.models.ChoiceModel
import com.teachernavigator.teachernavigator.presentation.models.PostModel
import com.teachernavigator.teachernavigator.presentation.utils.*
import com.teachernavigator.teachernavigator.presentation.views.PollChoiceView
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
                 onSubscribeListener: OnSubscribePostListener?,
                 onReadMoreListener: OnReadMoreListener?,
                 onComplaintListener: OnComplaintListener?,
                 onPollPassListener: OnPollPassListener?

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
    private val llChoices: RadioGroup = itemView.find(R.id.v_post_ll_choices)
    private val llChoisedChoices: LinearLayout = itemView.find(R.id.v_post_ll_choiced_choices)
    private val hvHasttags: HashtagView = itemView.find(R.id.v_post_hv_hasttags)
    private val tvLike: TextView = itemView.find(R.id.v_post_tv_like)
    private val tvDislike: TextView = itemView.find(R.id.v_post_tv_dislike)
    private val tvComments: TextView = itemView.find(R.id.v_post_tv_comments)
    private val ivSave: ImageView = itemView.find(R.id.v_post_iv_save)
    private val passTestButton: Button = itemView.find(R.id.v_post_btn_pass_test)

    private var mModel: PostModel? = null

    init {
        tvLike listenClickBy onLikeListener andReturnModel { mModel }
        tvDislike listenClickBy onDislikeListener andReturnModel { mModel }
        tvComments listenClickBy onCommentsListener andReturnModelOrHide { mModel }
        ivSave listenClickBy onSaveListener andReturnModel { mModel }
        ivSubscribe listenClickBy onSubscribeListener andReturnModelOrHide { mModel }
        btnMore listenClickBy onReadMoreListener andReturnModelOrHide { mModel }
        tvComplain listenClickBy onComplaintListener andReturnModelOrHide { mModel }

        if (onPollPassListener != null) {
            passTestButton.setOnClickListener { passTest(onPollPassListener) }
        }

    }

    private fun passTest(onPollPassListener: OnPollPassListener) {
        val model = mModel ?: return
        val choices = model.choices

        for (i in 0 until llChoices.childCount) {
            val radioBtn = (llChoices.getChildAt(i) as RadioButton)
            if (radioBtn.isChecked) {
                val selectedChoice = choices.find { it.id == radioBtn.tag }
                if (selectedChoice != null) {
                    onPollPassListener(model, selectedChoice)
                }
                return
            }
        }

    }

    override fun bind(model: PostModel?) {
        mModel = model

        ivAvatar.setImageOrPlaceholder(model?.authorAvatar)
        tvAuthorName.setTextOrHide(model?.authorName)
        tvPostTime.setTextOrHide(model?.created)

        val hasAuthor = (model?.authorId ?: -1) > 0
        tvAuthorName.visibility = if (hasAuthor) View.VISIBLE else View.GONE
        ivAvatar.visibility = if (hasAuthor) View.VISIBLE else View.GONE
        ivSubscribe.visibility = if (hasAuthor) View.VISIBLE else View.GONE

        tvTitle.setTextOrHide(model?.title)
        tvText.setTextOrHide(model?.text)


        if (model?.type == PostType.poll && btnMore.visibility == GONE && !model.choices.isNullOrEmpty()) {

            prepareChoiceView(model)

        } else {
            llChoices.visibility = GONE
            passTestButton.visibility = GONE
            llChoisedChoices.visibility = GONE
        }

        hvHasttags.setData(model?.tags ?: emptyList())

        tvLike.text = (model?.count_likes ?: 0).toString()
        tvDislike.text = (model?.count_dislikes ?: 0).toString()
        tvComments.text = (model?.count_comments ?: 0).toString()

        tvLike.setCompoundDrawablesWithIntrinsicBounds(getLikeDrawable(model?.vote), null, null, null)
        tvDislike.setCompoundDrawablesWithIntrinsicBounds(getDislikeDrawable(model?.vote), null, null, null)
    }


    // TODO Refactor This Method!!!!!
    private fun prepareChoiceView(model: PostModel) {
        if (model.pollPassed) {
            llChoices.visibility = GONE
            passTestButton.visibility = GONE
            llChoisedChoices.visibility = VISIBLE


            for (index in 0 until model.choices.size) {

                val choice = model.choices[index]
                var pollChoiceView: PollChoiceView?

                if (index >= llChoisedChoices.childCount) {
                    pollChoiceView = PollChoiceView(context)
                    pollChoiceView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    llChoisedChoices.addView(pollChoiceView)
                } else {
                    pollChoiceView = llChoisedChoices.getChildAt(index) as PollChoiceView
                }

                pollChoiceView.setChoiсe(choice, model)
                pollChoiceView.visibility = VISIBLE
            }

            for (index in model.choices.size until llChoisedChoices.childCount) {
                llChoisedChoices.getChildAt(index).visibility = GONE
            }


        } else {

            llChoisedChoices.visibility = GONE
            llChoices.visibility = VISIBLE
            passTestButton.visibility = VISIBLE

            for (index in 0 until model.choices.size) {

                val choice = model.choices[index]
                var radioButton: RadioButton?

                if (index >= llChoices.childCount) {
                    radioButton = RadioButton(context)
                    radioButton.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    llChoices.addView(radioButton)
                } else {
                    radioButton = llChoices.getChildAt(index) as RadioButton
                }

                radioButton.text = choice.choiceText
                radioButton.tag = choice.id
                radioButton.visibility = VISIBLE
            }


            for (index in model.choices.size until llChoices.childCount) {
                llChoices.getChildAt(index).visibility = GONE
            }

        }
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
typealias OnSubscribePostListener = (PostModel) -> Unit
typealias OnReadMoreListener = (PostModel) -> Unit
typealias OnComplaintListener = (PostModel) -> Unit
typealias OnPollPassListener = (PostModel, ChoiceModel) -> Unit