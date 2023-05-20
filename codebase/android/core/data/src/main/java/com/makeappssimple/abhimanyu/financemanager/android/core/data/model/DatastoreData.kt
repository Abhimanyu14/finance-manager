package com.makeappssimple.abhimanyu.financemanager.android.core.data.model

import com.makeappssimple.abhimanyu.financemanager.android.core.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.InitialDataVersionNumber
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatastoreData(
    @SerialName(value = "default_data_id")
    val defaultDataId: DefaultDataId = DefaultDataId(),

    @SerialName(value = "initial_data_version_number")
    val initialDataVersionNumber: InitialDataVersionNumber = InitialDataVersionNumber(),

    @SerialName(value = "data_timestamp")
    val dataTimestamp: DataTimestamp = DataTimestamp(),
)
