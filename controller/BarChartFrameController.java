package com.hyh.jizhang.controller;

import com.hyh.jizhang.tools.ChartTools;
import com.hyh.jizhang.tools.PublicTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;

public class BarChartFrameController {
    private ChartTools chartTools=new ChartTools();
    private PublicTools publicTools=new PublicTools();

    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private BarChart<?,?>barChart;
    @FXML
    private ComboBox<?>barChart_comboBox;
    @FXML
    private NumberAxis numberAxis;
    /**
     * 初始化界面
     */
    public void initialize(){
        String[]items=new String[]{"今天","昨天","最近3天","最近7天","最近30天","最近一年(12月)","最近一年(4季度)"};
        publicTools.public_addComboBoxItems(barChart_comboBox,items);
    }

    /**
     * 下拉列表框事件处理
     * @param actionEvent
     */
    @FXML
    public void barChart_comboBoxEvent(ActionEvent actionEvent) {
        //获取下拉列表框选中项
        String selectedCoboboxItem=(String) barChart_comboBox.getSelectionModel().selectedItemProperty().getValue();
        //对下拉列表框选中项进行处理
        switch (selectedCoboboxItem){
            case "今天":
                chartTools.public_setBarChartData(1,barChart,categoryAxis,numberAxis);
                break;
            case "昨天":
                chartTools.public_setBarChartData(2,barChart,categoryAxis,numberAxis);
                break;
            case "最近3天":
                chartTools.public_setBarChartData(3,barChart,categoryAxis,numberAxis);
                break;
            case "最近7天":
                chartTools.public_setBarChartData(4,barChart,categoryAxis,numberAxis);
                break;
            case "最近30天":
                chartTools.public_setBarChartData(5,barChart,categoryAxis,numberAxis);
                break;
            case "最近一年(12月)":
                chartTools.public_setBarChartData(6,barChart,categoryAxis,numberAxis);
                break;
            case "最近一年(4季度)":
                chartTools.public_setBarChartData(7,barChart,categoryAxis,numberAxis);
                break;
            default:
                break;
        }
    }
}
