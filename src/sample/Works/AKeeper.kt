package sample.Works

class AKeeper {
    private val size = 60

    private var runs = false
    fun start() {
        println("保持者启动了")
        if (runs) {
            runs = false
        }
        runs = true
        Thread {
            Thread.sleep(4 * 60 * 1000)
            while (runs) {
                AliHuaKuaiManager.getInstance()?.getSingle()
                Thread.sleep(1 * 60 * 1000)
            }
            println("保持者退出了")
        }.start()
    }

    fun stop() {
        runs = false
    }

    fun isRun() = runs
}