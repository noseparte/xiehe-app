package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("act_main.fxml"));
        primaryStage.setTitle("协和医院抢单协议 v1");
        primaryStage.setScene(new Scene(root, 1432, 840));
        primaryStage.show();

        /*
        JiSuClient.getInstance()
                .getClient()
                .getReq(
                        "zmj_lxy",
                        "ZMJ123",
                        "20",
                        "{\"aid\":\"FFFF0HSYT00000000000\",\"scene\":\"register_h5\",\"token\":\"\",\"referer\":\"https://webcdn2.hsyuntai.com/page/app/hkyzh5_xh.html\"}"
                ).enqueue(new Callback<AliWebBean>() {
            @Override
            public void onResponse(Call<AliWebBean> call, Response<AliWebBean> response) {
                System.out.println(new Gson().toJson(response.body()));
                CollectionUtils.INSTANCE.getSigBeans().add(response.body());
            }

            @Override
            public void onFailure(Call<AliWebBean> call, Throwable throwable) {

            }
        });
        */


       /* String data = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIuDSrSi0bYpQLXcWn/ZpYgORefNh9qtMefBhi9Fdzr3eyIagD8SxRZQwYmTrAYMOL+ZaQPQllt0JAcHeC0QI0JU3xpRKrSrol301TKqQkrQioafRfiWYfLrPinB6xnrN3ZVSyGM6GTY/mKR7Xy+SDE/QST1tA4U22Wa3/TfBhN5AgMBAAECgYA0Nt8u3AFA/A+MAPyd/QdG9JCVQQcngMq8wmFGL+l/2D/tc52r/Ypl37OPmgU3/jr++pujId4kPEN/nfwMYY3QJ8TWjMaERLB9gmLqWLzjqnddiz1YjS7RMPscgrhxzQ4Qe6cABMyeevbclKWYwCPQjGngfZtKsKLPVHfnyFdCQQJBAMqrROqv5by0xPR3gnprAPqo731Xzhs1mCD2lFlzAzB1NtTerQslRTkeC0gcPseGUwe6g8FyLjaZEm7mJDc/q98CQQCwOYdl16U8MjVxlSn4UuaQoYxq9aQb6RLRf7gqS8na/WOjPz7HbuzHkJtNIm1/Oe2+rmgDtNiQIe27Lh95MaunAkEAulTo0dTSpcKVaiYOgjqq9cooFdeKmR2XNoc+MVc60WyS8vefpSWpFTB4Mt41IgBviiWDSXGO54eomOli1qDlhQJAF9RPqMfWQiOP8oH3IOsk3l3Z/QSmYlfMAaRBpQaGjyRAeuysco9fWUUGmxGSuOd+bJBs5ENqHWNZIDyGaP78dQJBALlEHRJF5fcfPyKZDoYdGergJ7DEvl9G1RDcgvCshFqpidRUhLEwkIW242E3K6ZrdsHklqXHZFmhY7uFf2x28+k=";
        //data="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDULnkXFuE3vTaJU3VfK2PIK89ksYASAM3BfBhur+Uj0vBI3+a0J8d4Lox7dbHBCVXll+H/VmQ2aXbfbOUi7xJcMSHehdP5nNS7KFeE1HBt0JJXqHPksTaE0Z2CbMb6EO4D1BefLJmpaPleU3db6+tkDcFoCK7d3yQ2lSY3V7f6IQIDAQAB";
        int len = data.length();
        int pos = 0;
        int left = len;
        StringBuilder ret = new StringBuilder();
        while (pos < len) {
            int size = pos + 64;
            if (size >= len) size = len;
            ret.append(data, pos, size)
            .append("\n");
            left = len - pos;
            if (left < 64) {
                pos += left;
            } else
                pos += 64;
        }
        System.out.println(ret);*/
    }
}
