package com.teachernavigator.teachernavigator.domain.interactors

import com.teachernavigator.teachernavigator.data.models.PostNetwork

/**
 * Created by Arthur Korchagin on 18.10.17
 */
class Filter(val text: CharSequence, val publicationsContent: Pair<Boolean, Boolean>, val searchTags: HashSet<String>) {


    operator fun invoke(post: PostNetwork): Boolean {
        if (text.isNotBlank() && post.title?.contains(text, true) != true) {
            return false
        }

        if (searchTags.isNotEmpty() && (post.tags?.filter(searchTags::contains)?.count() ?: 0) == 0) {
            return false
        }

        if (!publicationsContent.second && !post.file.isNullOrBlank()) {
            return false
        }

        return true
    }


}