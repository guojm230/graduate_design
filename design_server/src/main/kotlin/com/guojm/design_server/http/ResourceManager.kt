package com.guojm.design_server.http

import com.guojm.design_server.model.Resource
import io.vertx.core.Vertx
import io.vertx.core.buffer.Buffer
import java.io.File

class ResourceManager {
    companion object{
        fun resolveMIME(path: String): String  = when(val suffix = path.substring(path.lastIndexOf(".")+1)){
            "png","jpg","gif"-> "image/$suffix"
            "js" -> "application/javascript"
            else -> "application/octet-stream"
        }

        fun findFile(resource: Resource){

        }
    }
}