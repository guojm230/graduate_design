package com.guojm.design_server.dao

enum class Table(val value: String) {
    SYS_USER("sys_user"),
    SYS_ROLE("sys_role"),
    SYS_AUTHORITY("sys_authority"),
    SYS_USER_ROLES("sys_user_roles"),
    SYS_ROLE_AUTHORITIES("sys_role_authorities"),
    RESOURCES("resources"),
    MOTOR_SETUP_B3("motor_setup_b3"),
    MOTOR("motor");

    override fun toString(): String {
        return value
    }
}