package com.teachernavigator.teachernavigator.presentation.transformers

import com.teachernavigator.teachernavigator.application.di.scopes.PerParentScreen
import com.teachernavigator.teachernavigator.domain.models.Choice
import com.teachernavigator.teachernavigator.presentation.models.ChoiceModel
import javax.inject.Inject

/**
 * Created by Arthur Korchagin on 15.10.17
 */
@PerParentScreen
class ChoiceTransformer
@Inject
constructor() : EntityTransformer<Choice, ChoiceModel> {


    override fun transform(from: Choice) = ChoiceModel(
            id = from.id ?: -1,
            choiceText = from.choice_text ?: "",
            votes = from.votes ?: 0
    )

}