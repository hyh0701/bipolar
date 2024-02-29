package com.hyh.jizhang.controller;

import com.hyh.jizhang.bean.TableData;
import com.hyh.jizhang.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DateCheckFrameController {
    private PublicTools publicTools=new PublicTools();
    @FXML
    private TableColumn<TableData,String> check_classificationColumn;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableColumn<TableData,String>check_idColumn;
    @FXML
    private TableColumn<TableData,String>check_typeColumn;
    @FXML
    private TableColumn<TableData,String>check_memoColumn;
    @FXML
    private TableView<TableData>check_tableView;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private TableColumn<TableData,String>check_moneyColumn;
    @FXML
    private TableColumn<TableData,String>check_dateColumn;

    @FXML
    public void dateCheckButtonEvent(ActionEvent actionEvent) {
        //获取用户输入的开始日期
        String startDate=String.valueOf(startDatePicker.getValue());
        //获取用户输入的结束日期
        String endDate=String.valueOf(endDatePicker.getValue());
        //组装sql语句
        String sql="select *from tb_records where rDate between '"+startDate+"'and'"+endDate+"';";
        //设置表格数据
        publicTools.public_initTableViewData(check_tableView
        ,publicTools.public_getTableViewData(sql)
        ,check_idColumn
        ,check_typeColumn
        ,check_moneyColumn
        ,check_classificationColumn
        ,check_memoColumn
        ,check_dateColumn);
    }
}
