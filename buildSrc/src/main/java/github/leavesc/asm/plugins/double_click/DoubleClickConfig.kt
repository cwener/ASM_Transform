package github.leavesc.asm.plugins.double_click

/**
 * @Author: leavesC
 * @Date: 2021/12/4 16:04
 * @Desc:
 */
class DoubleClickConfig(
    private val doubleCheckClass: String = "github.leavesc.asm.double_click.ViewDoubleClickCheck",
    val doubleCheckMethodName: String = "canClick",
    val doubleCheckMethodDescriptor: String = "(Landroid/view/View;)Z",
    private val checkViewOnClickAnnotation: String = "github.leavesc.asm.double_click.CheckViewOnClick",
    private val uncheckViewOnClickAnnotation: String = "github.leavesc.asm.double_click.UncheckViewOnClick",
    val hookPointList: List<DoubleClickHookPoint> = extraHookPoints
) {

    val formatDoubleCheckClass: String
        get() = doubleCheckClass.replace(".", "/")

    val formatCheckViewOnClickAnnotation: String
        get() = "L" + checkViewOnClickAnnotation.replace(".", "/") + ";"

    val formatUncheckViewOnClickAnnotation: String
        get() = "L" + uncheckViewOnClickAnnotation.replace(".", "/") + ";"

}

data class DoubleClickHookPoint(
    val interfaceName: String,
    val methodName: String,
    val methodSign: String,
) {

    val interfaceSignSuffix = "L$interfaceName;"

}

private val extraHookPoints = listOf(
    DoubleClickHookPoint(
        interfaceName = "android/view/View\$OnClickListener",
        methodName = "onClick",
        methodSign = "onClick(Landroid/view/View;)V"
    ),
    DoubleClickHookPoint(
        interfaceName = "com/chad/library/adapter/base/BaseQuickAdapter\$OnItemClickListener",
        methodName = "onItemClick",
        methodSign = "onItemClick(Lcom/chad/library/adapter/base/BaseQuickAdapter;Landroid/view/View;I)V"
    ),
    DoubleClickHookPoint(
        interfaceName = "com/chad/library/adapter/base/BaseQuickAdapter\$OnItemChildClickListener",
        methodName = "onItemChildClick",
        methodSign = "onItemChildClick(Lcom/chad/library/adapter/base/BaseQuickAdapter;Landroid/view/View;I)V",
    )
)