package github.leavesc.asm.plugins.optimized_thread

/**
 * @Author: leavesC
 * @Date: 2021/12/17 11:02
 * @Desc:
 */
class OptimizedThreadConfig(
    private val optimizedThreadClass: String = "github.leavesc.asm.optimized_thread.OptimizedThread",
    private val optimizedThreadPoolClass: String = "github.leavesc.asm.optimized_thread.OptimizedExecutors",
    val threadHookPointList: List<ThreadHookPoint> = threadHookPoints,
) {

    val formatOptimizedThreadClass: String
        get() = optimizedThreadClass.replace(".", "/")

    val formatOptimizedThreadPoolClass: String
        get() = optimizedThreadPoolClass.replace(".", "/")

}

private val threadHookPoints = listOf(
    ThreadHookPoint(
        methodName = "newFixedThreadPool"
    ),
    ThreadHookPoint(
        methodName = "newSingleThreadExecutor"
    ),
    ThreadHookPoint(
        methodName = "newCachedThreadPool"
    ),
    ThreadHookPoint(
        methodName = "newSingleThreadScheduledExecutor"
    ),
    ThreadHookPoint(
        methodName = "newScheduledThreadPool"
    ),
)

data class ThreadHookPoint(
    val methodName: String
)