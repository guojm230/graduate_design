package com.guojm.design_server.dao

import com.guojm.design_server.model.*

interface SystemDao {
    suspend fun findUser(id: Int): User?

    suspend fun findUserByUsername(username: String): User?

    suspend fun findRole(id: Int): Role?

    suspend fun findRoleByName(name: String): Role?

    suspend fun findAuthority(id: Int): Authority?

    suspend fun findAuthoritiesByName(name: String): Authority?

    suspend fun findFullUser(userId: Int): FullUser?

    /**
     * 根据AccountId查询角色列表,自动去重
     */
    suspend fun findRolesByUserId(userId: Int): List<Role>

    /**
     * 根据角色Id查询权限列表,自动去重
     */
    suspend fun findAuthoritiesByRoleIds(roleIds: List<Int>): List<Authority>

    suspend fun findAuthoritiesByRoleId(roleId: Int): List<Authority>{
        return findAuthoritiesByRoleIds(listOf(roleId))
    }

    /**
     * 返回AccountId
     * 如果插入失败则会抛出异常
     */
    suspend fun registerUser(rc: RegisterUser): Int
}