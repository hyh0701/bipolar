package com.hyh.jizhang.controller;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.hyh.jizhang.tools.ChartTools;
import com.hyh.jizhang.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;

public class LineChartFrameController {
    private PublicTools publicTools=new PublicTools();
    private ChartTools chartTools=new ChartTools();
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private ComboBox<?>lineChart_comboBox;
    @FXML
    private LineChart<?,?>lineChart;
    @FXML
    private NumberAxis numberAxis;
    /**
     * 初始化界面
     */
    public void initialize(){
        String[] items=new String[]{"最近3天","最近7天","最近30天","最近一年(12月)","最近一年(4季度)"};
        publicTools.public_addComboBoxItems(lineChart_comboBox,items);
    }

    /**
     * 折线图界面下拉列表框事件方法
     * @param actionEvent
     */
    @FXML
    public void lineChart_comboBoxEvent(ActionEvent actionEvent) {
        //获取下拉列表框选中项
        String selectedCoboboxItem=(String) lineChart_comboBox.getSelectionModel().selectedItemProperty().getValue();
        //对下拉列表框选中项进行处理
        switch(selectedCoboboxItem){
            case "最近3天":
                chartTools.public_setDayLineChartData(3,lineChart,categoryAxis,numberAxis);
                break;
            case "最近7天":
                chartTools.public_setDayLineChartData(4,lineChart,categoryAxis,numberAxis);
                break;
            case "最近30天":
                chartTools.public_setDayLineChartData(5,lineChart,categoryAxis,numberAxis);
                break;
            case "最近一年(12月)":
                chartTools.public_setDayLineChartData(6,lineChart,categoryAxis,numberAxis);
            case "最近一年(4季度)":
                chartTools.public_setDayLineChartData(7,lineChart,categoryAxis,numberAxis);
                break;
            default:
                break;
        }
    }
}
