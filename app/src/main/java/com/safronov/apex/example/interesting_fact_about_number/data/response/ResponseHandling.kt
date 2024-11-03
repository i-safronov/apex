package com.safronov.apex.example.interesting_fact_about_number.data.response

import com.safronov.apex.example.interesting_fact_about_number.domain.response.Response
import kotlinx.coroutines.flow.FlowCollector

suspend fun <F, S> retrofit2.Response<F>.onlySuccess(
    block: suspend (F) -> Unit,
    scope: FlowCollector<Response<S>>
) {
    if (isSuccessful) {
        val body = this.body()
        if (body != null) block(body) else {
            scope.emit(
                Response.Error(
                    error = IllegalStateException("Response's body is empty")
                )
            )
        }
    } else {
        scope.emit(
            Response.Error(
                error = IllegalStateException("Response's code is invalid, code: ${code()}")
            )
        )
    }
}