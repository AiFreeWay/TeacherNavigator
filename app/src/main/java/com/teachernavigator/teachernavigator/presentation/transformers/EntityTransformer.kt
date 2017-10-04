package com.teachernavigator.teachernavigator.presentation.transformers

import android.content.Context
import android.support.annotation.StringRes
import android.text.Html
import com.teachernavigator.teachernavigator.R
import io.reactivex.Observable
import io.reactivex.Single
import java.text.NumberFormat
import java.util.*

/**
 * Created by lliepmah on 24.09.17
 */
interface EntityTransformer<in From, out To> {
    fun transform(from: From): To
}

fun Context.spanned(@StringRes stringResId: Int, vararg formatArgs: Any) =
        Html.fromHtml(getString(stringResId, *formatArgs))

fun Context.formatRoubles(salary : Int) : CharSequence {
    val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
    format.currency = Currency.getInstance("RUB")
    return spanned(R.string.cost_roubles, format.format(salary))
}

fun <From, To> Single<From>.transformEntity(transformer: EntityTransformer<From, To>) =
        map { transformer.transform(it) }

fun <From, To> Single<List<From>>.transformListEntity(transformer: EntityTransformer<From, To>) =
        map { it.map { transformer.transform(it) } }

fun <From, To> Observable<From>.transformEntity(transformer: EntityTransformer<From, To>) =
        map { transformer.transform(it) }