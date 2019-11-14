package sample.Works;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import sample.ViewCallback.BaseCallback;

public class GlobalMenu extends ContextMenu {
    /**
     * 单例
     */
    private static GlobalMenu INSTANCE = null;

    /**
     * 私有构造函数
     */
    private BaseCallback callback;

    public GlobalMenu(BaseCallback callback) {
        this.callback = callback;
        MenuItem settingMenuItem = new MenuItem("全部选中");
        MenuItem updateMenuItem = new MenuItem("取消全选");
        MenuItem feedbackMenuItem = new MenuItem("删除选中");
        MenuItem aboutMenuItem = new MenuItem("启动选中任务");
        MenuItem companyMenuItem = new MenuItem("停止选中任务");
        settingMenuItem.setOnAction(event -> this.callback.onSelectAll());
        updateMenuItem.setOnAction(event -> this.callback.onUnSelectAll());
        feedbackMenuItem.setOnAction(event -> this.callback.onDeleteSelect());
        aboutMenuItem.setOnAction(event -> this.callback.onStartSelect());
        companyMenuItem.setOnAction(event -> this.callback.onStopSelect());
        getItems().add(settingMenuItem);
        getItems().add(updateMenuItem);
        getItems().add(feedbackMenuItem);
        getItems().add(aboutMenuItem);
        getItems().add(companyMenuItem);
    }

    /**
     * 获取实例
     *
     * @return GlobalMenu
     */
    public static GlobalMenu getInstance(BaseCallback callback) {
        if (INSTANCE == null) {
            INSTANCE = new GlobalMenu(callback);
        }

        return INSTANCE;
    }
}
