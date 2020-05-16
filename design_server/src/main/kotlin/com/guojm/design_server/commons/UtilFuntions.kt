package com.guojm.design_server.commons

fun notNull(vararg  strs: Any?): Boolean{
    return strs.all { it != null }
}

fun notNullElseThrow(throwable: Throwable,vararg objs: Any?): Boolean{
    return if (notNull(*objs)) true else throw throwable
}

fun notNullElseThrow(vararg objs: Any?): Boolean{
    return notNullElseThrow(NullPointerException(),*objs)
}

fun notEmpty(vararg strs: String?): Boolean{
    return strs.all{it != null && it != ""}
}

fun notEmptyElse(vararg strs: String?,elseHandler:(()->Unit)?){
    if (!notEmpty(*strs) && elseHandler != null){
        elseHandler()
    }
}
