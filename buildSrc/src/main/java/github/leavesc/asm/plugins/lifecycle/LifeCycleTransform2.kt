package github.leavesc.asm.plugins.lifecycle

import github.leavesc.asm.base.BaseTransform
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class LifeCycleTransform2: BaseTransform() {
    override fun modifyClass(byteArray: ByteArray): ByteArray {
        val reader = ClassReader(byteArray)
        val writer = ClassWriter(reader, ClassWriter.COMPUTE_MAXS)
        val visitor = LifeCycleVisitor(writer)
        reader.accept(visitor, groovyjarjarasm.asm.ClassReader.EXPAND_FRAMES)
        return writer.toByteArray()
    }
}