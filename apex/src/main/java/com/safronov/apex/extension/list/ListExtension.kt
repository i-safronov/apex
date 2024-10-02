package com.safronov.apex.extension.list

fun <T> List<T>.pushBack(elements: Array<out T>): List<T> {
    val mList = this.toMutableList()
    mList.addAll(elements = elements)
    return mList
}