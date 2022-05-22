package github.leavesc.asm.plugins.lifecycle

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class LifeCyclePlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val appExtension: AppExtension = target.extensions.getByType()
        appExtension.registerTransform(LifeCycleTransform2())
    }
}