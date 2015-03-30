
package com.xy.weibocrawler.knn;

/**
 * 元组实体类
 * 
 * @author xiaoyong
 */
public class KNNTuple {

    private int id;

    private double distance; // 元组与测试元组的距离

    private String category; // 元组所属类别

    public KNNTuple(int id, double distance, String category) {
        super();
        this.id = id;
        this.distance = distance;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "KNNTuple [id=" + id + ", distance=" + distance + ", category=" + category + "]";
    }

}
