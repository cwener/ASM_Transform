package github.leavesc.asm.plugins.lifecycle

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import groovyjarjarasm.asm.ClassReader.EXPAND_FRAMES
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileOutputStream

class LifeCycleTransform : Transform() {
    override fun getName(): String {
        return "lifeCycle_transform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_JARS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation) {
        if (!transformInvocation.isIncremental) {
            transformInvocation.outputProvider.deleteAll()
        }
        transformInvocation.inputs.forEach {
            it.directoryInputs.forEach { directory ->
                if (directory.file.isDirectory) {
                    val files = directory.file.walkTopDown()
                    files.forEach { file ->
                        val fileName = file.name
                        println("----------- deal with transformInvocation: lifeCycle_transform file size = $fileName -----------")
                        if (checkFileName(fileName)) {
                            println("----------- deal with \"class\" file < + $fileName + > -----------")
                            val reader = ClassReader(file.readBytes())
                            val writer = ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
                            val visitor = LifeCycleVisitor(writer)
                            reader.accept(visitor, EXPAND_FRAMES)
                            val code = writer.toByteArray()
                            val fos =
                                FileOutputStream("${file.parentFile.absolutePath}${File.separator}${fileName}")
                            fos.write(code)
                            fos.close()
                        }
                    }
                }
                val dest = transformInvocation.outputProvider.getContentLocation(
                    directory.name,
                    directory.contentTypes,
                    directory.scopes,
                    Format.DIRECTORY
                )
                FileUtils.copyDirectory(directory.file, dest)
            }
        }
    }

    private fun checkFileName(fileName: String): Boolean {
        return fileName == ".class"
                && !fileName.startsWith("R\$")
                && fileName != "R.class"
                && fileName != "BuildConfig.class"
                && "MainActivity.class" == fileName
    }
}