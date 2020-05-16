package com.guojm.design_server.http.handler

import com.guojm.design_server.commons.ApiException
import com.guojm.design_server.commons.ErrorBody
import com.guojm.design_server.commons.StatusCode
import com.guojm.design_server.http.writeErrorBody
import io.vertx.core.Handler
import io.vertx.ext.web.RoutingContext

class GlobalErrorHandler : Handler<RoutingContext> {
    override fun handle(frc: RoutingContext) {
        if(frc.response().ended()){
            return
        }
        val exception = frc.failure()
        if (exception == null){
            if (frc.statusCode() == -1){
                frc.response().writeErrorBody(ErrorBody(StatusCode.SERVER_ERROR))
            }
        } else {
            resolveException(frc,exception)
        }
    }

    private fun resolveException(context: RoutingContext, exception: Throwable){
        if (exception is ApiException){
            context.response().writeErrorBody(ErrorBody(exception))
        } else {
            context.response().writeErrorBody(ErrorBody(StatusCode.SERVER_ERROR))
        }
    }
}