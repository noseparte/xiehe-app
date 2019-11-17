package sample;

/**
 * Sample Skeleton for 'act_main.fxml' Controller Class
 */

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import sample.Bean.AppSet;
import sample.Bean.HXYYBean.*;
import sample.Bean.ProxyType;
import sample.Bean.Save.SaveBean;
import sample.Bean.Save.SaveBeanUser;
import sample.Bean.UserBean;
import sample.Callback.ProxyGet;
import sample.ViewCallback.BaseCallback;
import sample.Works.AKeeper;
import sample.Works.AliHuaKuaiManager;
import sample.Works.GlobalMenu;
import sample.Works.UserRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class MainClassName implements ProxyGet, BaseCallback {
    public Button mReadAccount;
    public Button mOneKeyLogin;
    public Button mModifyDocInto;//修改医生信息在账号里
    public Button mStartTask;//启动所有任务
    public Button mExecScheduler;//执行定期任务按钮
    public Button mGetAliDunData;//获取滑块数据按钮

    public RadioButton mYBSelect;
    public RadioButton mNYBSelect;

    public TextField mMaYiKey;//蚂蚁代理Key
    public TextField mMaYiSecret;//蚂蚁代理秘钥
    public TextField mMaYiHttp;//蚂蚁代理服务器地址
    public TextField mSearchText;//搜索编辑框 搜索科室
    public TextField mAliDunPwd;//打码密码
    public TextField mAliDunAccount;//打码账号
    public TextField mAliDunLimit;//阿里云盾session数量
    public TextField mTime_Hour;//小时
    public TextField mTime_Minutes;//分钟
    public TextField mTime_Seconds;//秒数

    //列表列绑定数据
    public TableColumn<UserBean, String> account;
    public TableColumn<UserBean, String> password;
    public TableColumn<UserBean, String> patient;
    public TableColumn<UserBean, String> accountState;
    public TableColumn<UserBean, String> YYKS;
    public TableColumn<UserBean, String> docName;
    public TableColumn<UserBean, String> isYB;
    public TableColumn<UserBean, String> AMPM;
    public TableColumn<UserBean, String> inviteState;
    public TableColumn<UserBean, Integer> ids;
    public TableColumn<UserBean, String> date;
    public TableColumn<UserBean, CheckBox> select;
    public TableColumn<UserBean, String> table_lasted;
    public TableColumn<UserBean, String> todayInvite;

    public TableView<UserBean> mListView;//账号信息列表

    public ListView<SearchRetBeanDataSectItems> mSectionList;//搜索到的科室列表 - 一级主列表
    public ListView mSecList;//二级选择列表

    public DatePicker mDatePicker;
    public DatePicker mNow;

    public ComboBox<String> mComboBox;

    public Label mTimeShowing;
    public Label mAliSigSize;
    public CheckBox mUseNow;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;


    //========== 程序集变量开始 =========

    /**
     * 账号文本文件
     */
    private String mAccountName = "./Account.txt";


    /**
     * 观察者对象
     */
    private ObservableList<UserBean> mUsers;

    private ArrayList<UserRunner> UserList;

    private ObservableList<SearchRetBeanDataSectItems> searchRetBeanDataSectItems;
    private ObservableList<DetailsDeptRetBeanData> mDetailsDeptRetBean;
    private ObservableList<SecDeptRetBeanData> mSecDeptRetBean;
    private ObservableList<GetDeptDocRetBean.Data.Daytype1Sch> mGetDeptDocBean;

    /**
     * 当前选择的医生信息
     */
    private GetDeptDocRetBean.Data.Daytype1Sch mDocInfo;

    /**
     * 当前科室信息 - 仅做数据展示用
     */
    private String mCurrentKS = "";

    private DetailsDeptRetBeanData mDetailsDeptRetBeanData;

    private Timer timer;

    private Boolean isStartTimer = false;

    private AKeeper mKeeper;


    @FXML
    void initialize() {

        //音频控制(通常写在控件动作中)
        //player.pause();
        //player.stop();

        File mSet = new File("./ApplicationSet.json");
        if (!mSet.exists()) {
            try {
                mSet.createNewFile();
                String init = "{\n" +
                        "  \"mayidaili\": {\n" +
                        "    \"key\": \"你申请得到的Key\",\n" +
                        "    \"secret\": \"你申请得到的secret\",\n" +
                        "    \"web\": \"s3.proxy.mayidaili.com:8123\"\n" +
                        "  },\n" +
                        "  \"jisuhuakuai\": {\n" +
                        "    \"limit\": \"100000\",\n" +
                        "    \"user\": \"11111111111\",\n" +
                        "    \"pass\": \"22222222222\"\n" +
                        "  }\n" +
                        "}";
                CollectionUtils.INSTANCE.writeText(mSet, init);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Alert mAlert = CollectionUtils.INSTANCE.getMAlert();
            mAlert.setAlertType(Alert.AlertType.WARNING);
            mAlert.setHeaderText("警告:");
            mAlert.setContentText("运行目录下没有发现ApplicationSet.json文件,已自动创建,请手动修改数据。");
            mAlert.show();
        }
        String set = CollectionUtils.INSTANCE.readText("./ApplicationSet.json");
        AppSet mApp = new Gson().fromJson(set, AppSet.class);
        //System.out.println(mApp.getDaiLi().getKey());
        mAliDunLimit.setText(mApp.getHuaKuai().getLimit());
        mAliDunAccount.setText(mApp.getHuaKuai().getUser());
        mAliDunPwd.setText(mApp.getHuaKuai().getPass());

        mMaYiKey.setText(mApp.getDaiLi().getKey());
        mMaYiSecret.setText(mApp.getDaiLi().getSecret());
        mMaYiHttp.setText(mApp.getDaiLi().getWeb());

        File mTimer = new File("./time.json");
        if (mTimer.exists()) {
            String time = CollectionUtils.INSTANCE.readText("./time.json");
            try {
                Date mDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
                mTime_Hour.setText(mDate.getHours() + "");
                mTime_Minutes.setText(mDate.getMinutes() + "");
                mTime_Seconds.setText(mDate.getSeconds() + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        ToggleGroup group = new ToggleGroup();
        mYBSelect.setToggleGroup(group);
        mNYBSelect.setToggleGroup(group);

        mDatePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null) return "";
                return object.toString();
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.equals("")) return LocalDate.now();
                return LocalDate.parse(string);
            }
        });
        mDatePicker.getEditor().setText("请选择医生日期!");
        mNow.setConverter(mDatePicker.getConverter());
        mNow.getEditor().setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        ObservableList<String> mTimeSelect = FXCollections.observableArrayList();
        mComboBox.setItems(mTimeSelect);
        mTimeSelect.add("上午");
        mTimeSelect.add("下午");
        mTimeSelect.add("随机");
        mComboBox.setValue(mTimeSelect.get(2));

        mUsers = FXCollections.observableArrayList();
        searchRetBeanDataSectItems = FXCollections.observableArrayList();
        mDetailsDeptRetBean = FXCollections.observableArrayList();
        mGetDeptDocBean = FXCollections.observableArrayList();
        mSecDeptRetBean = FXCollections.observableArrayList();
        mSectionList.setItems(searchRetBeanDataSectItems);

        /*
        mSectionList.setCellFactory(param -> new ListCell<SearchRetBeanDataDeptItems>() {
            @Override
            protected void updateItem(SearchRetBeanDataDeptItems item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null)
                    setText(item.getDeptName());
            }
        });
        */
        account.setCellValueFactory(new PropertyValueFactory<>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        patient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        accountState.setCellValueFactory(new PropertyValueFactory<>("accountState"));
        YYKS.setCellValueFactory(new PropertyValueFactory<>("YYKS"));
        docName.setCellValueFactory(new PropertyValueFactory<>("docName"));
        isYB.setCellValueFactory(new PropertyValueFactory<>("SFYB"));
        AMPM.setCellValueFactory(new PropertyValueFactory<>("APM"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        inviteState.setCellValueFactory(new PropertyValueFactory<>("inviteState"));
        ids.setCellValueFactory(new PropertyValueFactory<>("ids"));
        table_lasted.setCellValueFactory(new PropertyValueFactory<>("lasted"));
        todayInvite.setCellValueFactory(new PropertyValueFactory<>("todayInvite"));
        select.setCellValueFactory(cell -> cell.getValue().getCheckBox().getCheckBox());

        mListView.setItems(mUsers);
        mListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (oldValue != null)
                        oldValue.willModify = false;
                    observable.getValue().willModify = true;
                }
        );
        mListView.setContextMenu(new GlobalMenu(this));

        //加密注释:c2IldTVCQTIldTYyMzclMjAldTdFQ0YldTVFMzgldTVCRjkldTk4NzkldTc2RUUldTYzMDcldTYyNEIldTc1M0IldTgxMUElMjAldTRFMEQldTRGMUEldTUxOTkldTVDMzEldTk1RUQldTU2MzQldThCQTkldTYyMTEldTUxOTklMjAldTc3MEIldTRFRDYldTRFMEQldTcyM0QlMjAldTY1NDUldTYxMEYldTRFMEQldTUwNUEldThGRDkldTRFMkEldTUyOUYldTgwRkQlMjAldTVGRkQldTYwQTAldTRFRDYldThCRjQldTZDQTEldTY3MDk=
        /*mListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println(observable.getValue().getUserName());
                }
        );*/

        mSectionList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    SearchRetBeanDataSectItems searchRetBeanDataSectItems = observable.getValue();
                    if (searchRetBeanDataSectItems == null) return;
                    mCurrentKS = searchRetBeanDataSectItems.toString();
                    if (searchRetBeanDataSectItems.getSectName() != null) {
                        UserList.get(0).getSecDept(new SecDeptBean() {{
                            setHosDistId(searchRetBeanDataSectItems.getHosDistId());
                            setIsAccess("1");
                            setSectId(searchRetBeanDataSectItems.getSectId());
                        }}, mUseNow.isSelected(), secDeptRetBean -> {
                            Platform.runLater(() -> {
                                mSecDeptRetBean.clear();
                                mSecDeptRetBean.addAll(secDeptRetBean.getData());
                                mSecList.setItems(mSecDeptRetBean);
                            });
                            return null;
                        });
                    } else if (searchRetBeanDataSectItems.getDeptName() != null) {
                        getTheDept(new DetailsDeptBean() {{
                            setHosDistId(searchRetBeanDataSectItems.getHosDistId());
                            setDeptId(searchRetBeanDataSectItems.getDeptId());
                        }});
                    } else if (searchRetBeanDataSectItems.getSubjectName() != null) {
                        if (mDetailsDeptRetBeanData == null) {
                            mDetailsDeptRetBeanData = new DetailsDeptRetBeanData();
                            if (searchRetBeanDataSectItems.getDeptId() != null)
                                mDetailsDeptRetBeanData.setDeptId(searchRetBeanDataSectItems.getDeptId());
                            if (searchRetBeanDataSectItems.getSubjectId() != null)
                                mDetailsDeptRetBeanData.setSubjectId(searchRetBeanDataSectItems.getSubjectId());
                            if (searchRetBeanDataSectItems.getHosId32() != null)
                                mDetailsDeptRetBeanData.setHosId32(searchRetBeanDataSectItems.getHosId32());
                            if (searchRetBeanDataSectItems.getSubjectName() != null)
                                mDetailsDeptRetBeanData.setSubjectName(searchRetBeanDataSectItems.getSubjectName());
                        }
                        getTheDept(new GetDeptDocBean() {{
                            setDeptId(searchRetBeanDataSectItems.getDeptId());
                            setSubjectId(searchRetBeanDataSectItems.getSubjectId());
                        }});
                    }
                    //加密注释:JXU1QkEyJXU2MjM3JXU1OTJBJXU1MEJCJXU5MDNDJTIwJXU0RTBEJXU2MEYzJXU3RUQ5JXU0RUQ2JXU1MDVBJXU4RkQ5JXU0RTJBJXU1MjlGJXU4MEZEJTIwJXU1MTQ4JXU1RkZEJXU2MEEwJXU0RUQ2JXU4QkY0JXU2Q0ExJXU2NzA5JXU1OTdEJXU0RTg2
                    /* else if (observable.getValue() instanceof GetDeptDocRetBean.Data.Daytype1Sch) {
                        GetDeptDocRetBean.Data.Daytype1Sch mDetailsDeptRetBeanData = (GetDeptDocRetBean.Data.Daytype1Sch) observable.getValue();

                    }*/
                }
        );

        mSecList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (observable.getValue() instanceof SecDeptRetBeanData) {
                        SecDeptRetBeanData mSecDeptRetBeans = (SecDeptRetBeanData) observable.getValue();
                        getTheDept(new DetailsDeptBean() {{
                            setHosDistId(mSecDeptRetBeans.getHosDistId());
                            setDeptId(mSecDeptRetBeans.getDeptId());
                        }});
                    } else if (observable.getValue() instanceof DetailsDeptRetBeanData) {
                        mDetailsDeptRetBeanData = (DetailsDeptRetBeanData) observable.getValue();
                        getTheDept(new GetDeptDocBean() {{
                            setDeptId(mDetailsDeptRetBeanData.getDeptId());
                            setSubjectId(mDetailsDeptRetBeanData.getSubjectId());
                        }});
                    } else if (observable.getValue() instanceof GetDeptDocRetBean.Data.Daytype1Sch) {
                        mDocInfo = (GetDeptDocRetBean.Data.Daytype1Sch) observable.getValue();
                        System.out.println(mDocInfo.getDocName());
                    }
                }
        );

        /*new HttpClient(null).getmHttp().getServerNotify(new `NewsGet.java`())
                .enqueue(new Callback<GetNewsBean>() {
                    @Override
                    public void onResponse(Call<GetNewsBean> call, Response<GetNewsBean> response) {

                    }

                    @Override
                    public void onFailure(Call<GetNewsBean> call, Throwable t) {

                    }
                });*/
        //搜索框逻辑
        mSearchText.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (UserList.size() <= 0) {
                    return;
                }
                searchRetBeanDataSectItems.clear();
                mSecList.setItems(null);
                mDocInfo = null;
                String filterTime = "";
                if (mUseNow.isSelected()) {
                    filterTime = mNow.getEditor().getText();
                }
                UserList.get(0).getSearchAble(mSearchText.getText(), filterTime, (searchRet) -> {
                    if (searchRet != null) {
                        Platform.runLater(() -> searchRetBeanDataSectItems.addAll(searchRet));
                    }
                    return null;
                });
            }
        });

        CollectionUtils.INSTANCE.setMAlert(new Alert(Alert.AlertType.CONFIRMATION));

        //读取所有账号
        mReadAccount.setOnAction(event -> {
            mUsers.clear();
            UserList.clear();
            File mAccountFile = new File("./Account.txt");
            if (!mAccountFile.exists()) {
                try {
                    mAccountFile.createNewFile();
                    String init = "17617435979====ss445566----陈瑶\n" +
                            "15611227222----ss445566----万明先\n" +
                            "13269817555----qq123456----陈瑶\n" +
                            "13001223029----qq123456----张庆英";
                    CollectionUtils.INSTANCE.writeText(mAccountFile, init);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Alert mAlert = CollectionUtils.INSTANCE.getMAlert();
                mAlert.setAlertType(Alert.AlertType.WARNING);
                mAlert.setHeaderText("警告:");
                mAlert.setContentText("运行目录下没有发现Account.txt文件,已自动创建,请手动录入账号信息并重新点击'读取账号'按钮。");
                mAlert.show();
            }
            new Thread(() -> {
                String accs = CollectionUtils.INSTANCE.readText(mAccountName);
                String[] mArr = accs.split("\n");
                for (String s : mArr) {
                    String[] mAccount = s.split("----");
                    if (mAccount.length == 3) {
                        UserBean user = new UserBean() {{
                            setUserName(mAccount[0]);
                            setPassword(mAccount[1]);
                            setPatient(mAccount[2]);
                            setAccountState("未登录");
                        }};
                        File mSave = new File("./" + user.getUserName() + ".json");
                        if (mSave.exists()) {
                            String data = CollectionUtils.INSTANCE.readText("./" + user.getUserName() + ".json");
                            SaveBean mSaveData = new Gson().fromJson(data, SaveBean.class);
                            data = mSaveData.getUser();
                            SaveBeanUser mSaveDataA = new Gson().fromJson(data, SaveBeanUser.class);
                            user.setAPM(mSaveDataA.apm);
                            user.setDate(mSaveDataA.date);
                            user.setYYKS(mSaveDataA.YYKS);
                            user.setSFYB(mSaveDataA.SFYB);
                            if (mSaveDataA.isToday) user.setTodayInvite(true, mSaveDataA.schID);
                            else user.setTodayInvite(false, null);
                            data = mSaveData.getDocDate();
                            GetDeptDocRetBean.Data.Daytype1Sch mDoc = new Gson().fromJson(data, GetDeptDocRetBean.Data.Daytype1Sch.class);
                            user.setDocInfo(mDoc);
                            data = mSaveData.mDeptSubjectData;
                            DetailsDeptRetBeanData mDetailsDeptRetBean = new Gson().fromJson(data, DetailsDeptRetBeanData.class);
                            user.setDeptSubjectData(mDetailsDeptRetBean);
                        }
                        user.setIds(UserList.size() + 1);
                        UserRunner mUserRunner = new UserRunner(user, this);
                        //todo 警告 测试代码 上线移出
                        //mUserRunner.login();
                        UserList.add(mUserRunner);
                        mUsers.add(user);
                    }
                }
            }).start();
        });

        UserList = new ArrayList<>();

        //一键登录
        mOneKeyLogin.setOnAction(event -> new Thread(() -> {
            mOneKeyLogin.setDisable(true);
            UserList.forEach(UserRunner::login);
            mOneKeyLogin.setDisable(false);
        }).start());

        //修改医生数据
        mModifyDocInto.setOnAction(event -> {
            String mTime = mDatePicker.getEditor().getText();
            String ampm = mComboBox.getValue();
            boolean isYba = mYBSelect.isSelected();
            mUsers.forEach(userBean -> {
                if (userBean.willModify) {
                    if (mUseNow.isSelected()) {
                        userBean.setTodayInvite(true, mDocInfo.getSchId());
                    } else {
                        userBean.setTodayInvite(false, null);
                    }
                    userBean.setAPM(ampm);
                    userBean.setDate(mTime);
                    userBean.setSFYB(isYba ? "是" : "否");
                    userBean.getCheckBox().getCheckBox().getValue().setSelected(false);
                    if (mDocInfo != null) {
                        userBean.setDocInfo(mDocInfo);
                        userBean.setYYKS(mCurrentKS);
                    }
                    if (mDetailsDeptRetBeanData != null) {
                        userBean.setDeptSubjectData(mDetailsDeptRetBeanData);
                    }
                    SaveBeanUser user = new SaveBeanUser();
                    user.apm = userBean.getAPM();
                    user.date = (userBean.getDate());
                    user.schID = (userBean.schID);
                    user.isToday = (userBean.isToday);
                    user.YYKS = (userBean.getYYKS());
                    user.SFYB = (userBean.getSFYB());
                    SaveBean mSave = new SaveBean();
                    mSave.setDocDate(new Gson().toJson(userBean.getDocInfo()));
                    mSave.mDeptSubjectData = (new Gson().toJson(userBean.getDeptSubjectData()));
                    mSave.setUser(new Gson().toJson(user));

                    String str = new Gson().toJson(mSave);
                    CollectionUtils.INSTANCE.writeText(new File("./" + userBean.getUserName() + ".json"), str);
                    System.out.println(str);
                    userBean.willModify = false;
                }
                //System.out.println(userBean.getUserName());
            });
        });

        //获取批量阿里云滑块数据
        mGetAliDunData.setOnAction(event -> {
            mAliSigSize.setText("正在生成滑块数据...");
            AliHuaKuaiManager.Companion.getInstance(mAliDunAccount.getText(), mAliDunPwd.getText(), Integer.parseInt(mAliDunLimit.getText()), (size) -> {
                Platform.runLater(() -> {
                    System.out.println("剩余滑块数据:" + size);
                    mAliSigSize.setText("剩余滑块数据:" + size);
                });
                return null;
            });
        });

        //开始所有任务
        mStartTask.setOnAction(event -> {
            if (UserList.size() <= 0) {
                return;
            }
            if (mStartTask.getText().equals("启动")) {
                if (mKeeper == null) mKeeper = new AKeeper();
                mKeeper.start();
            } else mKeeper.stop();

            //开始所有任务的监控
            UserList.forEach(data -> {
                if (!data.isLogin()) return;
                if (data.isStopEx() && data.getUserInfo().getDocInfo() != null)
                    data.startThread();
                else
                    data.stopThread();
            });
            Platform.runLater(() -> mStartTask.setText(mStartTask.getText().equals("启动") ? "停止" : "启动"));
        });

        //定时启动
        mExecScheduler.setOnAction(event -> {
            if (isStartTimer) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                mExecScheduler.setText("执行定时任务");
                isStartTimer = false;
                return;
            }
            mExecScheduler.setText("取消定时任务");
            isStartTimer = true;
            if (timer == null) timer = new Timer() {{

            }};
            Date mDate = new Date();
            mDate.setHours(Integer.parseInt(mTime_Hour.getText()));
            mDate.setMinutes(Integer.parseInt(mTime_Minutes.getText()));
            mDate.setSeconds(Integer.parseInt(mTime_Seconds.getText()));
            File time = new File("./time.json");
            if (!time.exists()) {
                try {
                    time.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            CollectionUtils.INSTANCE.writeText(time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mDate));
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(mDate));
            //String time = mTime_Hour.getText() + ":" + mTime_Minutes.getText() + ":" + mTime_Seconds.getText();
            //"2019-09-25 " + time
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mStartTask.fire();
                    isStartTimer = false;
                    Platform.runLater(() -> mTimeShowing.setText("定时任务正在运行." + new SimpleDateFormat("HH:mm:ss").format(new Date())));
                    Platform.runLater(() -> mExecScheduler.setText("执行定时任务"));
                    timer.cancel();
                    timer = null;
                }
            }, mDate);
        });

        //============ 常规初始化代码结束 ============
       /* mRequestPay.setOnAction(event -> {
            //{"accessPatId":"45608775","patientName":"万明先","patId":"d9359639d83e45e4a472d7378c233188","psvFlag":"Y","cardNo":"652201196304010679","isVerify":"Y","phoneNo":"17710615965","relation":5,"usId":"57d94f0c94f44006a374f05bf6f070d7","hosPatCardNo":"652201196304010679","patIdLong":0,"documentId":"","workDept":"","primaryPatFlag":"N","addr":"","email":""}
            RequestPayBean requestPayBean = new RequestPayBean();
            requestPayBean.setCardNo("652201196304010679");
            requestPayBean.setHosPatCardNo("652201196304010679");
            requestPayBean.setHosPatCardType("0");
            requestPayBean.setPatId("d9359639d83e45e4a472d7378c233188");
            requestPayBean.setPatName("万明先");
            requestPayBean.setPhoneNo("17710615965");
            requestPayBean.setPsvFlag("Y");
            requestPayBean.setSafeUniqueCode(getSafeUUID());
            requestPayBean.setSchId("5063db36f047432f87ab9dad6488fb8e");
            requestPayBean.setTakeIndex("");
            AliBean bean = new Gson().fromJson("", AliBean.class);
            HashMap<String, String> header = new HashMap<>();
            header.put("slider_token", bean.getAll()[1].getNc_token());
            header.put("slider_sessionid", bean.getAll()[1].getCsessionid());
            header.put("slider_sig", bean.getAll()[1].getSig());
            *//*mHttpClient.getmHttp().requestPay(requestPayBean, header)
                    .enqueue(new Callback<RequestPayRetBean>() {
                        @Override
                        public void onResponse(Call<RequestPayRetBean> call, Response<RequestPayRetBean> response) {
                            System.out.println(new Gson().toJson(response.body().getData()));
                        }

                        @Override
                        public void onFailure(Call<RequestPayRetBean> call, Throwable throwable) {

                        }
                    });*//*
        });*/
    }

    private void getTheDept(DetailsDeptBean mDetailsDeptBean) {
        UserList.get(0).getTheDept(new DetailsDeptBean() {{
            setHosDistId(mDetailsDeptBean.getHosDistId());
            setDeptId(mDetailsDeptBean.getDeptId());
        }}, mUseNow.isSelected(), mDetailsDeptBeanA -> {
            Platform.runLater(() -> {
                mDetailsDeptRetBean.clear();
                mDetailsDeptRetBean.addAll(mDetailsDeptBeanA.getData());
                mSecList.setItems(mDetailsDeptRetBean);
            });
            return null;
        });
    }

    private void getTheDept(GetDeptDocBean mGetDeptDocBeans) {
        UserList.get(0).getTheDeptDocs(new GetDeptDocBean() {{
            setDeptId(mGetDeptDocBeans.getDeptId());
            setSubjectId(mGetDeptDocBeans.getSubjectId());
        }}, mUseNow.isSelected(), mGetDeptDocRetBean -> {
            Platform.runLater(() -> {
                mGetDeptDocBean.clear();
                mGetDeptDocBean.addAll(mGetDeptDocRetBean);
                mSecList.setItems(mGetDeptDocBean);
            });
            return null;
        });
    }

    @Override
    public ProxyType getProxySet() {
        return new ProxyType() {{
            setKey(mMaYiKey.getText());
            setSecret(mMaYiSecret.getText());
            String[] arr = mMaYiHttp.getText().split(":");
            if (arr.length == 2) {
                setWebIp(arr[0]);
                setWebSocket(Integer.parseInt(arr[1]));
            } else {
                Alert a = CollectionUtils.INSTANCE.getMAlert();
                a.setContentText("代理服务器设置错误，请检查数据！");
                a.setAlertType(Alert.AlertType.ERROR);
                a.show();
            }
        }};
    }

    @Override
    public void onSelectAll() {
        mUsers.forEach(userBean -> userBean.getCheckBox().getCheckBox().getValue().setSelected(true));
    }

    @Override
    public void onUnSelectAll() {
        mUsers.forEach(userBean -> userBean.getCheckBox().getCheckBox().getValue().setSelected(false));
    }

    @Override
    public void onDeleteSelect() {
        new Thread(() -> {
            mOneKeyLogin.setDisable(true);
            mUsers.removeIf(userBean -> {
                boolean will = userBean.getCheckBox().getCheckBox().getValue().isSelected();
                if (will) {
                    System.out.println("删除了:" + userBean.getUserName());
                    UserList.removeIf(userRunner -> {
                        boolean re = userRunner.getUserInfo() == userBean;
                        if (re)
                            System.out.println("任务队列移出:" + userRunner.getUserInfo().getUserName());
                        return re;
                    });
                }
                return will;
            });
            mOneKeyLogin.setDisable(false);
        }).start();
    }

    @Override
    public void onStartSelect() {
        UserList.forEach(userRunner -> {
            if (userRunner.getUserInfo().getCheckBox().getCheckBox().getValue().isSelected()) {
                userRunner.startThread();
            }
        });
    }

    @Override
    public void onStopSelect() {
        UserList.forEach(userRunner -> {
            if (userRunner.getUserInfo().getCheckBox().getCheckBox().getValue().isSelected()) {
                userRunner.stopThread();
            }
        });
    }
}
