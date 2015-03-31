
package com.xy.weibocrawler.knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author xiaoyong
 */
public class KNN {
    /**
     * KNN分类
     * 
     * @param TrainTuples
     * @param testTuple
     * @param k
     * @return
     */
    public String startKnn(List<List<Double>> TrainTuples, List<Double> testTuple, int k) {
        // 优先队列存储近邻元素
        PriorityQueue<KNNTuple> priorityQueue = new PriorityQueue<KNNTuple>(k, comparator);
        List<Integer> randNum = getRandKNum(k, TrainTuples.size());
        for (int i = 0; i < k; i++) {
            int index = randNum.get(i);
            List<Double> currData = TrainTuples.get(index);
            String c = currData.get(currData.size() - 1).toString();
            KNNTuple node = new KNNTuple(index, calDistance(testTuple, currData), c);
            priorityQueue.add(node);
        }
        for (int i = 0; i < TrainTuples.size(); i++) {
            List<Double> t = TrainTuples.get(i);
            double distance = calDistance(testTuple, t);
            KNNTuple top = priorityQueue.peek();
            if (top.getDistance() > distance) {
                priorityQueue.remove();
                priorityQueue.add(new KNNTuple(i, distance, t.get(t.size() - 1).toString()));
            }
        }

        return getMostClass(priorityQueue);
    }

    /**
     * 设置优先级队列的比较函数，距离越大，优先级越高
     */
    private Comparator<KNNTuple> comparator = new Comparator<KNNTuple>() {
        public int compare(KNNTuple t1, KNNTuple t2) {
            if (t1.getDistance() >= t2.getDistance()) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    /**
     * 获取K个不同的随机数
     * 
     * @param k 随机数的个数
     * @param max 随机数最大的范围
     * @return 生成的随机数数组
     */
    public List<Integer> getRandKNum(int k, int max) {
        List<Integer> rand = new ArrayList<Integer>(k);
        for (int i = 0; i < k; i++) {
            int temp = (int) (Math.random() * max);
            if (!rand.contains(temp)) {
                rand.add(temp);
            } else {
                i--;
            }
        }
        return rand;
    }

    /**
     * 计算测试元组与训练元组之间的距离
     * 
     * @param d1 测试元组
     * @param d2 训练元组
     * @return 距离值
     */
    public double calDistance(List<Double> d1, List<Double> d2) {
        double distance = 0.00;
        for (int i = 0; i < d1.size(); i++) {
            distance += (d1.get(i) - d2.get(i)) * (d1.get(i) - d2.get(i));
        }
        return distance;
    }

    /** 获取所得到的k个最近邻元组的多数类
     * @param pq
     * @return
     */
    private String getMostClass(PriorityQueue<KNNTuple> pq) {
        Map<String, Integer> classCount = new HashMap<String, Integer>();
        for (int i = 0; i < pq.size(); i++) {
            KNNTuple node = pq.remove();
            String c = node.getCategory();
            if (classCount.containsKey(c)) {
                classCount.put(c, classCount.get(c) + 1);
            } else {
                classCount.put(c, 1);
            }
        }
        int maxIndex = -1;
        int maxCount = 0;
        Object[] classes = classCount.keySet().toArray();
        for (int i = 0; i < classes.length; i++) {
            if (classCount.get(classes[i]) > maxCount) {
                maxIndex = i;
                maxCount = classCount.get(classes[i]);
            }
        }
        return classes[maxIndex].toString();
    }
}
