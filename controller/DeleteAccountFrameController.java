package com.hyh.jizhang.controller;

import com.hyh.jizhang.bean.Record;
import com.hyh.jizhang.bean.Session;
import com.hyh.jizhang.dao.RecordDao;
import com.hyh.jizhang.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeleteAccountFrameController {
    @FXML
    private TextField idTextField;
    @FXML
    private Label contentLabel;

    /**
     * ”查询“按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void checkButtonEvent(ActionEvent actionEvent) {
        // 实例化Record对象
        Record record = new Record();
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 通过记录ID和用户ID查询账目记录
        Record checkedRecord = recordDao.selectRecordByIdAndUserId(Integer.parseInt(idTextField.getText()), Session.getUser().getUserId());
        String info = "";
        if (checkedRecord.getRecordType() == null && checkedRecord.getRecordClassification() == null) {
            info = "无此查询结果！";
        } else {
            info =
                    "类型：\t\t" + checkedRecord.getRecordType() + "\n"
                            + "金额：\t\t" + checkedRecord.getRecordMoney() + "\n"
                            + "分类：\t\t" + checkedRecord.getRecordClassification() + "\n"
                            + "备注：\t\t" + checkedRecord.getRecordMemo() + "\n"
                            + "日期：\t\t" + checkedRecord.getRecordDate() + "\n";
        }
        // 显示查询结果
        contentLabel.setText(info);
    }
    /**
     * ”删除“按钮的事件监听器
     *
     * @param actionEvent 事件
     */
    @FXML
    public void deleteButtonEvent(ActionEvent actionEvent) {
        // 将string类型数据转换为int类型数据
        int id = Integer.parseInt(idTextField.getText());
        // 实例化RecordDao对象
        RecordDao recordDao = new RecordDao();
        // 根据ID删除记录
        boolean b = recordDao.deleteRecord(new Record(id));
        if (b) {
            SimpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "删除数据成功！");
            // 删除成功后就清除窗体数据
            idTextField.setText("");
            contentLabel.setText("");
            MainPageController m1 = (MainPageController) MainController.mainControllers.get("m1");
            m1.initialize();
        } else {
            SimpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "删除数据失败！");
        }
    }
}
