package github.leavesc.asm.plugins.lifecycle

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class LifeCycleVisitor(private val cv: ClassVisitor) : ClassVisitor(Opcodes.ASM6, cv), Opcodes {

    private var className: String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        println("LifecycleClassVisitor : visit -----> started ：$name")
        className = name
        super.visit(version, access, name, signature, superName, interfaces);
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        println("LifecycleClassVisitor : visitMethod : $name")
        val methodVisitor = cv.visitMethod(access, name, descriptor, signature, exceptions)
        if ("github/leavesc/asm/MainActivity" == className) {
            if ("onCreate" == name) {
                return LifecycleOnCreateMethodVisitor(methodVisitor)
            }
        }
        return methodVisitor
    }

    override fun visitEnd() {
        super.visitEnd()
    }

    private fun checkFileName(fileName: String): Boolean {
        return fileName == ".class"
                && !fileName.startsWith("R\$")
                && fileName != "R.class"
                && fileName != "BuildConfig.class"
                && "MainActivity.class" == fileName
    }
}