package com.makeappssimple.abhimanyu.financemanager.android.cre.data.model

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.DataTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.InitialDataVersionNumber
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.Reminder
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class DatastoreData(
    @SerialName(value = "default_data_id")
    val defaultDataId: DefaultDataId = DefaultDataId(),

    @SerialName(value = "initial_data_version_number")
    val initialDataVersionNumber: InitialDataVersionNumber = InitialDataVersionNumber(),

    @SerialName(value = "data_timestamp")
    val dataTimestamp: DataTimestamp = DataTimestamp(),

    @SerialName(value = "reminder")
    val reminder: Reminder = Reminder(),
)
