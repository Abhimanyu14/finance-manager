package com.makeappssimple.abhimanyu.financemanager

import com.makeappssimple.abhimanyu.financemanager.entities.initialdatabasedata.InitialDatabaseOutputDataEntity
import com.makeappssimple.abhimanyu.financemanager.utils.readInitialDatabaseDataFile
import com.makeappssimple.abhimanyu.financemanager.utils.writeToInitialDatabaseDataFile

fun main() {
    readInitialDatabaseDataFile()?.let {
        writeToInitialDatabaseDataFile(InitialDatabaseOutputDataEntity(it))
    }
}
