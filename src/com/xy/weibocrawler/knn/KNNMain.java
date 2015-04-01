
package com.xy.weibocrawler.knn;

import com.xy.weibocrawler.db.JDBC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KNNMain {

    private static final String FRUITNUM = "fruitnum";

    private static final String WINENUM = "winenum";

    private static final String MILKNUM = "milknum";

    private static final String CATEGORY = "category";

    private static final String CONTENT = "content";

    public static void main(String[] args) throws SQLException {
        JDBC.INSTANCE.getDbConnection();

        KNN knn = new KNN();
        List<List<Double>> trainTuples = new ArrayList<List<Double>>();
        List<List<Double>> testTuples = new ArrayList<List<Double>>();
        // 数据库数据读取
        String testSql = "SELECT * FROM weiboinfo WHERE category IS NULL";
        String trainSql = "SELECT * FROM weiboinfo WHERE category IS NOT NULL";
        String updateSql = "UPDATE weiboinfo SET category = ? WHERE content = ?";

        List<Map<String, Object>> trainList = JDBC.INSTANCE.dbSelectMultiData(trainSql, null);
        List<Map<String, Object>> testList = JDBC.INSTANCE.dbSelectMultiData(testSql, null);

        for (Map<String, Object> map : trainList) {
            List<Double> list = new ArrayList<Double>();
            list.add(Double.parseDouble(map.get(FRUITNUM).toString()));
            list.add(Double.parseDouble(map.get(WINENUM).toString()));
            list.add(Double.parseDouble(map.get(MILKNUM).toString()));
            list.add((double) category2Int((String) map.get(CATEGORY)));
            trainTuples.add(list);
        }

        for (Map<String, Object> map : testList) {
            List<Double> list = new ArrayList<Double>();
            list.add(Double.parseDouble(map.get(FRUITNUM).toString()));
            list.add(Double.parseDouble(map.get(WINENUM).toString()));
            list.add(Double.parseDouble(map.get(MILKNUM).toString()));
            testTuples.add(list);
        }

        for (int i = 0; i < testTuples.size(); i++) {
            int category = Math.round(Float.parseFloat((knn.startKnn(trainTuples,
                    testTuples.get(i), 30))));
            // update数据库category字段
            String content = (String) testList.get(i).get(CONTENT);
            List<String> updateParams = new ArrayList<String>();
            updateParams.add(int2Category(category));
            updateParams.add(content);
            if (JDBC.INSTANCE.dbUpdateBySQL(updateSql, updateParams)) {
                System.err.println("dbUpdateBySQL returns true");
            }

        }
        JDBC.INSTANCE.dbClose();
    }

    private static int category2Int(String c) {
        if (c.equals("fruit")) {
            return 1;
        } else if (c.equals("wine")) {
            return 2;
        } else {
            return 3;
        }
    }

    private static String int2Category(int c) {
        if (c == 1) {
            return "fruit";
        } else if (c == 2) {
            return "wine";
        } else {
            return "milk";
        }
    }
}
