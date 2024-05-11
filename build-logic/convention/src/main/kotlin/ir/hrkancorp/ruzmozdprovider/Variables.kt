package ir.hrkancorp.ruzmozdprovider

import com.android.build.api.dsl.BuildType
import ir.hrkancorp.ruzmozdprovider.Variables.Type.BOOLEAN
import ir.hrkancorp.ruzmozdprovider.Variables.Type.STRING

object Variables {
    object Type {
        const val STRING = "String"
        const val BOOLEAN = "boolean"
    }

    object Key {
        const val BASE_URL = "BASE_URL"
        const val IS_MAP_FROM_RUZMOZD_SERVER = "IS_MAP_FROM_RUZMOZD_SERVER"
    }

    object Value {
        object Development {
            const val BASE_URL = "\"https://dev.ruzmozd.ir\""
            const val IS_MAP_FROM_RUZMOZD_SERVER = "false"
        }

        object Staging {
            const val BASE_URL = "\"https://staging.ruzmozd.ir\""
            const val IS_MAP_FROM_RUZMOZD_SERVER = "true"
        }
        object Production {
            const val BASE_URL = "\"https://ruzmozd.ir\""
            const val IS_MAP_FROM_RUZMOZD_SERVER = "true"
        }
    }
}


fun <BuildTypeT : BuildType> BuildTypeT.stagingVariables() {
    buildConfigField(
        STRING,
        Variables.Key.BASE_URL,
        Variables.Value.Staging.BASE_URL
    )
    buildConfigField(
        BOOLEAN,
        Variables.Key.IS_MAP_FROM_RUZMOZD_SERVER,
        Variables.Value.Staging.IS_MAP_FROM_RUZMOZD_SERVER
    )
}

fun <BuildTypeT : BuildType> BuildTypeT.productionVariables() {
    buildConfigField(
        STRING,
        Variables.Key.BASE_URL,
        Variables.Value.Production.BASE_URL
    )
    buildConfigField(
        BOOLEAN,
        Variables.Key.IS_MAP_FROM_RUZMOZD_SERVER,
        Variables.Value.Production.IS_MAP_FROM_RUZMOZD_SERVER
    )
}


fun <BuildTypeT : BuildType> BuildTypeT.developmentVariables() {
    buildConfigField(
        STRING,
        Variables.Key.BASE_URL,
        Variables.Value.Development.BASE_URL
    )
    buildConfigField(
        BOOLEAN,
        Variables.Key.IS_MAP_FROM_RUZMOZD_SERVER,
        Variables.Value.Development.IS_MAP_FROM_RUZMOZD_SERVER
    )
}