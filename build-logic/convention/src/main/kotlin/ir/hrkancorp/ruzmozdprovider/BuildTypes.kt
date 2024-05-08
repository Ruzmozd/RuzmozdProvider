package ir.hrkancorp.ruzmozdprovider

import com.android.build.api.dsl.BuildType
import org.gradle.api.NamedDomainObjectContainer


fun <BuildTypeT : BuildType> NamedDomainObjectContainer<BuildTypeT>.staging(action: BuildTypeT.() -> Unit) {
    create("staging") {
        initWith(getByName("debug"))
        action()
    }
}


