package com.hyh.jizhang.dao;

import com.hyh.jizhang.bean.Classification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassificationDao {

    public List<Classification> selectByType(String classificationType) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Classification> classificationList = new ArrayList<>();

        try {
            conn = JDBCUtils.getConnection();
            stmt = conn.createStatement();
            String sql = "select * from tb_classification where cType='" + classificationType + "';";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Classification classification = new Classification();
                classification.setcId(rs.getInt(1));
                classification.setcName(rs.getString(2));
                classification.setcType(rs.getString(3));
                classificationList.add(classification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, stmt, conn);
        }

        return classificationList;
    }

    public boolean addClassification(Classification classification) {
        Connection conn = null;
        int num = 0;

        try {
            conn = JDBCUtils.getConnection();
            String cName = classification.getcName();

            // 检查 cName 是否为 null
            if (cName == null) {
                return false;
            }

            String sql = "INSERT INTO tb_classification(cName, cType) VALUES (?, ?);";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, cName);
                pstmt.setString(2, classification.getcType());
                num = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(null, null, conn);
        }

        return num > 0;
    }
    public boolean deleteClassification(Classification classification) {
        Connection conn = null;
        int num = 0;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "DELETE FROM tb_classification WHERE cName = ?;";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, classification.getcName());
                num = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(null, null, conn);
        }

        return num > 0;
    }

    public boolean updateClassification(String newName, String oldName) {
        Connection conn = null;
        int num = 0;

        try {
            conn = JDBCUtils.getConnection();
            String sql = "UPDATE tb_classification SET cName=? WHERE cName=?;";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, newName);
                pstmt.setString(2, oldName);
                num = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(null, null, conn);
        }

        return num > 0;
    }
}
