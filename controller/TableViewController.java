package com.hyh.jizhang.controller;

import com.hyh.jizhang.bean.Session;
import com.hyh.jizhang.bean.TableData;
import com.hyh.jizhang.tools.PublicTools;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewController {
    private PublicTools publicTools=new PublicTools();
    @FXML
    private TableColumn<TableData,String>check_classificationColumn;

    @FXML
    private TableColumn<TableData,String>check_typeColumn;

    @FXML
    private TableColumn<TableData,String>check_memoColumn;

    @FXML
    private TableView<TableData> check_tableView;

    @FXML
    private TableColumn<TableData,String>check_idColumn;

    @FXML
    private TableColumn<TableData,String>check_moneyColumn;

    @FXML
    private TableColumn<TableData,String>check_dateColumn;

    /**
     * 初始化界面表格数据
     */
    public void initialize() {
        // 初始化表格数据
        String sql = "select * from tb_records where uId=" + Session.getUser().getUserId() + ";";
        publicTools.public_initTableViewData(check_tableView
                , publicTools.public_getTableViewData(sql)
                , check_idColumn
                , check_typeColumn
                , check_moneyColumn
                , check_classificationColumn
                , check_memoColumn
                , check_dateColumn);
    }
}
