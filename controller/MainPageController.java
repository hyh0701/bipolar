package com.hyh.jizhang.controller;

import com.hyh.jizhang.MainApp;
import com.hyh.jizhang.bean.Record;
import com.hyh.jizhang.bean.Session;
import com.hyh.jizhang.bean.TableData;
import com.hyh.jizhang.bean.User;
import com.hyh.jizhang.dao.RecordDao;
import com.hyh.jizhang.tools.DateTools;
import com.hyh.jizhang.tools.PublicTools;
import com.hyh.jizhang.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * 主界面控制器
 *
 * @author lck100
 */
/**
 * 主界面控制器
 *
 * @author lck100
 */
public class MainPageController {

    private SimpleTools simpleTools = new SimpleTools();
    private RecordDao recordDao = new RecordDao();
    private MainApp mainApp = new MainApp();
    private PublicTools publicTools = new PublicTools();
    private DateTools dateTools = new DateTools();

    @FXML
    public RadioMenuItem defaultRadioMenuItem;

    @FXML
    public RadioMenuItem blackRadioMenuItem;

    @FXML
    public RadioMenuItem whiteRadioMenuItem;

    @FXML
    private MenuItem refreshContextMenu;

    @FXML
    private TableColumn<TableData, String> classificationColumn;

    @FXML
    private TextField totalOutputTextField;

    @FXML
    private TextField totalInputTextField;

    @FXML
    private ImageView userImage;

    @FXML
    private Label userNameLabel;

    @FXML
    private TableColumn<TableData, String> moneyColumn;

    public TableView<TableData> getTableView() {
        return tableView;
    }

    public void setTableView(TableView<TableData> tableView) {
        this.tableView = tableView;
    }

    @FXML
    private TableView<TableData> tableView;

    @FXML
    private TableColumn<TableData, String> typeColumn;

    @FXML
    private TableColumn<TableData, String> memoColumn;

    @FXML
    private TextField balanceTextField;

    @FXML
    private TableColumn<TableData, String> dateColumn;

    @FXML
    private TableColumn<TableData, String> idColumn;

    /**
     * 初始化界面信息
     */
    @FXML
    public void initialize() {
        // 刷新主键编号
        new RecordDao().refreshPrimaryKey();
        // 初始化用户记录
        initUserRecord();
        // 初始化表格数据
        initAddDataToTableView();
        MainController.mainControllers.put("m1", this);

    }

    /**
     * “导入”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void importMenuItemEvent(ActionEvent actionEvent) {
        //实例化文件选择器
        FileChooser fileChooser = new FileChooser();
        //设置默认文件过滤器
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("excel(*.xls)", "xls", "xlsx"));
        //打开文件选择框，并得到选中的文件
        File result = fileChooser.showOpenDialog(null);
        if (result != null) {
            // 获取绝对路径
            String importPath = result.getAbsolutePath();
            try {
                //读取excel表内容（不包括表头）
                String[][] content = SimpleTools.readExcelContentArray(new FileInputStream(importPath));
                boolean isSuccess = false;
                for (int i = 0; i < content.length; i++) {
                    Record record = new Record();
                    record.setUserId(Session.getUser().getUserId());
                    record.setRecordType(content[i][1]);
                    record.setRecordMoney(Float.parseFloat(content[i][2]));
                    record.setRecordClassification(content[i][3]);
                    record.setRecordMemo(content[i][4]);
                    record.setRecordDate(content[i][5]);
                    //添加数据到数据库
                    isSuccess = recordDao.addRecord(record);
                }
                // 判断是否导入成功
                if (isSuccess) {
                    SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "导入excel数据成功");
                    // 刷新界面显示的数据
                    initialize();
                } else {
                    SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "导入excel数据失败");
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * “导出”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void exportMenuItemEvent(ActionEvent actionEvent) {
        String exportPath = null;
        //实例化文件选择器
        FileChooser fileChooser = new FileChooser();
        //打开保存文件选择框
        fileChooser.setInitialFileName("Excel");
        File result = fileChooser.showSaveDialog(null);
        if (result != null) {
            exportPath = result.getAbsolutePath();
            //excel表格表头
            String[] title = {"序号", "类型", "金额", "分类", "备注", "日期"};
            // 获取当前用户的支出记录
            List<Record> recordList = recordDao.selectByUserId(Session.getUser().getUserId());
            // 将支出记录转换成二维数组
            String[][] tableData = new String[recordList.size()][6];
            int j = 0;
            for (Record record : recordList) {
                tableData[j][0] = String.valueOf(record.getRecordId());
                tableData[j][1] = record.getRecordType();
                tableData[j][2] = Float.toString(record.getRecordMoney());
                tableData[j][3] = record.getRecordClassification();
                tableData[j][4] = record.getRecordMemo();
                tableData[j][5] = record.getRecordDate();
                j++;
            }
            //导出路径
            String exportExcelFilePath = SimpleTools.exportExcel(title, tableData, exportPath);
            if (new File(exportExcelFilePath).exists()) {
                SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "导出excel成功！");
            } else {
                SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "导出excel失败！");
            }
        }
    }



    /**
     * “退出”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void exitMenuItemEvent(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * ”添加“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void addMenuItemEvent(ActionEvent actionEvent) {
        // 调用添加账目界面
        mainApp.initAddFrame();
        // 刷新界面数据
         initialize();

    }

    /**
     * ”删除“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void deleteMenuItemEvent(ActionEvent actionEvent) {
        //调用删除账目界面
        mainApp.initDeleteFrame();
        //刷新
        initialize();


    }

    /**
     * ”修改“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void alterMenuItemEvent(ActionEvent actionEvent) {

        //调用修改界面控制器
        mainApp.initAlterFrame(null,false);
        initialize();
    }

    /**
     * “删除”右键菜单的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void deleteContextMenuEvent(ActionEvent event) {
        //给出提示框提示用户是否选择删除
        boolean b=SimpleTools.informationDialog(Alert.AlertType.CONFIRMATION,"提示","提示：","请问是否删除");
        if(b){
            RecordDao recordDao=new RecordDao();
            int selectedIndex=tableView.getSelectionModel().getSelectedIndex();
            //获取所选取的行中的数据
            TableData td=tableView.getSelectionModel().getSelectedItem();
            //判断是否选中表格行
            if(selectedIndex>=0){
                tableView.getItems().remove(selectedIndex);
                recordDao.deleteRecord(new Record(Integer.parseInt(td.getId())));
            }
            initialize();//刷新表格数据
        }

    }

    /**
     * “添加”右键菜单的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void addContextMenuEvent(ActionEvent event) {
        mainApp.initAddFrame();
        initialize();
    }

    /**
     * “修改”右键菜单的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void alterContextMenuEvent(ActionEvent event) {
    //获取所选中行的数据
        TableData td=tableView.getSelectionModel().getSelectedItem();
        //设置一个标志，判断是右键菜单触发的修改还是由菜单条上的菜单触发的修改
        boolean isConeextMenu=true;
        mainApp.initAlterFrame(td,isConeextMenu);
        initialize();
    }

    /**
     * ”刷新“右键菜单的事件监听器
     *
     * @param actionEvent 事件
     */
    public void refreshContextMenuEvent(ActionEvent actionEvent) {
        initialize();
    }

    /**
     * ”查询“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void checkMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initTableView();
        initialize();

    }

    /**
     * ”条形图“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void barChartMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initBarChart();

    }

    /**
     * “折线图”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void lineChartMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initLineChart();
    }


    /**
     * ”添加分类“菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void addClassificationMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initAddClassificatiopnFrame();

    }

    /**
     * “用户信息”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void userInfoMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initUserInformationFrame();
        initialize();

    }

    /**
     * “关于软件”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void abutSoftMenuItemEvent(ActionEvent actionEvent) {
        mainApp.initAboutjizhang();

    }

    /**
     * “帮助”菜单项的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void helpMenuItemEvent(ActionEvent actionEvent) {

    }

    /**
     * ”按日期查询“菜单项的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void dateCheckMenuItemEvent(ActionEvent event) {
        //打开按日期查询界面
        mainApp.initDateCheckTableView();

    }

    /**
     * ”按分类查询“菜单项的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void classificationCheckMenuItemEvent(ActionEvent event) {
        mainApp.initClassificationTableView();

    }

    /**
     * ”按备注查询“菜单项的事件监听器
     *
     * @param event 事件
     */
    @FXML
    public void memoCheckMenuItemEvent(ActionEvent event) {
        mainApp.initMemoTableView();

    }


    /**
     * 操作结果：初始化用户名、总支出、总收入及余额
     */
    public void initUserRecord() {
        // 获取登录成功的用户
        User user = Session.getUser();
        // 获取用户支出的总金额
        float totalOutput = recordDao.getTotalAccount("支出", user.getUserId());
        // 获取用户收入的总金额
        float totalInput = recordDao.getTotalAccount("收入", user.getUserId());
        // 获取余额
        float balance = totalInput - totalOutput;
        // 设置图片
        userImage.setImage(new Image("file:" + user.getUserImagePath()));
        userImage.setSmooth(true);
        userImage.setFitWidth(100);
        userImage.setFitHeight(100);
        userImage.setCache(true);
        userImage.setPreserveRatio(true);
        // 将支出金额、收入金额、余额、用户名设置到文本框种
        totalOutputTextField.setText(String.valueOf(totalOutput));
        totalInputTextField.setText(String.valueOf(totalInput));
        balanceTextField.setText(String.valueOf(balance));
        userNameLabel.setText(Session.getUser().getUserName());
    }

    /**
     * 操作结果：初始化数据表视图
     */
    public void initAddDataToTableView() {
        String sql = "select * from tb_records where uId=" + Session.getUser().getUserId() + ";";
        publicTools.public_initTableViewData(tableView
                , publicTools.public_getTableViewData(sql)
                , idColumn
                , typeColumn
                , moneyColumn
                , classificationColumn
                , memoColumn
                , dateColumn);
    }

    /**
     * 报告菜单项的事件监听器
     * @param actionEvent
     */
    @FXML
    public void reportMenuItemEvent(ActionEvent actionEvent) {
        //打开报告界面
        mainApp.initReportFrame();
    }
}

