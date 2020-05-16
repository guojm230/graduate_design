package com.guojm.design_server.http.handler

import com.guojm.design_server.commons.invalidParamException
import com.guojm.design_server.commons.notEmptyElse
import com.guojm.design_server.dao.SystemDao
import com.guojm.design_server.http.security.PasswordEncoder
import com.guojm.design_server.model.RegisterUser
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 注册用户
 */
class RegisterHandler @Inject constructor(val systemDao: SystemDao,
                                          val passwordEncoder: PasswordEncoder
) : AbstractSuspendRouteHandler() {

    override suspend fun suspendHandle(context: RoutingContext) {
        val request = context.request()
        val response = context.response()
        request.isExpectMultipart = true
        val type = request.getParam("type") ?: "design"
        request.endHandler{
            launch(context){
                val user = checkFormAttribute(context)
                if (type == "provider"){
                    user.roleIds = listOf(systemDao.findRoleByName("PROVIDER")!!.id)
                } else {
                    user.roleIds = listOf(systemDao.findRoleByName("DESIGNER")!!.id)
                }
                systemDao.registerUser(user)
                response.end()
            }
        }
    }

    private fun checkFormAttribute(context: RoutingContext): RegisterUser{
        val request = context.request()
        val username = request.getFormAttribute("username")
        val password = request.getFormAttribute("password")
        notEmptyElse(username,password){throw invalidParamException("用户名和密码不能为空")}
        val name = request.getFormAttribute("name")
        notEmptyElse(name){throw invalidParamException("公司名称不能为空")}
        val email = request.getFormAttribute("email")
        val telephone = request.getFormAttribute("telephone")
        val description = request.getFormAttribute("description")
        return RegisterUser(username,passwordEncoder.encode(password),name,email,telephone,description,true)
    }
}