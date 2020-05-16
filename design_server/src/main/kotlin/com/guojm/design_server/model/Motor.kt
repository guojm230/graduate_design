package com.guojm.design_server.model

data class Motor(
    val id: Int,
    val userId: Int,
    val model: String,
    val poles: Int,
    val ratedPower: Int,
    val speed: Int,
    val current: Int,
    val efficiency: Double,
    val power_factor: Double,
    val SDRCurrent: Double,
    val SDRTorque: Double,
    val MDRTorque: Double,
    val MOI: Double,
    val weight: Int,
    val setupType: Int,
    val imageResource: Resource,
    val modelResource: Resource
)

/**
 * B3安装参数
 */
data class MotorSetupB3(
    val id: Int,
    val motorId: Int,
    val A: Double,
    val B: Double,
    val C: Double,
    val D: Double,
    val E: Double,
    val F: Double,
    val G: Double,
    val H: Double,
    val K: Double,
    val AB: Double,
    val AC: Double,
    val AD: Double,
    val HD: Double,
    val L: Double
)