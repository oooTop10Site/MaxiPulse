package org.example.project.data.model.sensor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SensorResponse (
    @SerialName("name") val deviceName: String? = null,
    @SerialName("address") val deviceAddress: String? = null,
    @SerialName("rssi") val rssi: Int? = null,
    @SerialName("company_id") val companyId: Int? = null,
    @SerialName("battery_charge") val battery: Int? = null,
    @SerialName("running_counter") val runningCounter : Int? = null,
    @SerialName("acc") val acc: Double? = null,
    @SerialName("hr") val heartRate: Int? = null,
    @SerialName("rr") val rateInterval: Int? = null
)