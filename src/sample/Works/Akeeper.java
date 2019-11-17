package sample.Works;

public class Akeeper {
    private int size = 60;
    private boolean runs = false;

    public void start() {
        try {
            Thread.sleep(4 * 60 * 1000);
            System.out.println("保持者启动了");
            if (runs) {
                runs = false;
            }
            runs = true;
            while (runs) {
                AliHuaKuaiManager.getInstance().getSingle();
                Thread.sleep(1 * 60 * 1000);
            }
            System.out.println("保持者退出了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        runs = false;
    }

    public void isRun() {
        runs = true;
    }

}
