
/*
 * @Descripttion:
 * @version:
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-11 14:57:38
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-24 09:01:07
 */
package src.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class MyGraphic {

    // 使用HashMap来存储节点和它们指向的边的集合
    private Map<String, Set<GraphicEdge>> graph = new HashMap<>();
    // 一个表，用来计算某条边是否重复出现
    private Map<String, GraphicEdge> map = new HashMap<>();
    // 这是随机游走的当前节点
    private String randomWalkNode = null;
    // 获取所有节点的字符
    public List<String> GraphicNodes = new ArrayList<>();

    public MyGraphic(String filePath) {
        System.out.println("读入路径" + filePath);
        this.readData(filePath);
        // 初始化图结构
        for (Map.Entry<String, GraphicEdge> entry : this.map.entrySet()) {
            String startNode = entry.getValue().getStart();
            if (!graph.containsKey(startNode)) {
                graph.put(startNode, new HashSet<>());
            }
            // 将边添加到起始节点的边的集合中
            graph.get(startNode).add(entry.getValue());
        }

        System.out.println(filePath);

        for (Entry<String, Set<GraphicEdge>> e : graph.entrySet()) {
            GraphicNodes.add(e.getKey());
        }
    }

    /**
     * @param {String} s
     * @param {String} e
     * @return {*}
     * @name:
     * @msg: 添加节点的操作
     */
    public void addEdge(String s, String e) {
        // 当前的s->e已经存在了，则权值+1
        if (map.containsKey(s + "->" + e)) {
            map.get(s + "->" + e).addWeight();
        }
        // 当前的s->e不存在，更新加入新的边
        else {
            GraphicEdge newEdge = new GraphicEdge(s, e);
            map.put(s + "->" + e, newEdge);
            // 更新节点和边的数量
        }
    }

    public String showAllEdge() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String, GraphicEdge> entry : map.entrySet()) {
            b.append(entry.getValue().getStart() + "->" + entry.getValue().getEnd() + " ");
            // System.out.println(entry.getValue().getStart() + "-->" +
            // entry.getValue().getEnd());
        }
        return b.toString();
    }

    // 读取数据并且将初始数据全部存储到内存中
    public void readData(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String originString = contentBuilder.toString();
        String[] data = originString.split("[^0-9a-zA-Z]+");
        int len = data.length;
        for (int i = 0; i < len - 1; i++) {
            this.addEdge(data[i], data[i + 1]);
        }
        this.addEdge(data[len - 1], "nullnullnull");
    }

    /**
     * 获取整个图的所有边 "s->e"表示
     *
     * @return Map<String, GraphicEdge> String类型是节点描述"s->e"字符，GraphicEdge内存中实际边的存储模态
     */
    public Map<String, GraphicEdge> getAllEdge() {
        return this.map;
    }

    public void displayEdges() {
        for (Map.Entry<String, Set<GraphicEdge>> entry : graph.entrySet()) {
            System.out.println("Edges from " + entry.getKey() + ":");
            for (GraphicEdge edge : entry.getValue()) {
                System.out.println("  " + edge.getStart() + " -> " + edge.getEnd() + " (weight: " + edge.getWeight() + ")");
            }
        }
    }

    // 使用Dijkstra算法找到从源节点到目标节点的最短路径

    /**
     * @param {String} source 初始节点
     * @return {Map<String,String>} 返回一个回溯最短路径映射，其中键值配对为<节点x，节点x的前置节点>
     * @name:
     * @msg: 查找最短路径的算法
     */
    public Map<String, String> calcShortestPath(String source) {
        if (!graph.containsKey(source)) {
            return null; // 源节点或目标节点不存在
        }
        // 初始化距离和上一个节点映射
        Map<String, Integer> distance = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        for (String node : graph.keySet()) {
            distance.put(node, Integer.MAX_VALUE);
            previous.put(node, null);
        }
        distance.put(source, 0); // 源节点到自身的距离为0
        // 使用优先队列（最小堆）来实现Dijkstra算法
        PriorityQueue<String> pq = new PriorityQueue<>((a, b) -> Integer.compare(distance.get(a), distance.get(b)));
        pq.add(source);
        while (!pq.isEmpty()) {
            String current = pq.poll();
            for (GraphicEdge edge : graph.get(current)) {
                int newDistance = distance.get(current) + edge.getWeight();
                // 异常捕获防止获取null时发生报错
                try {
                    if (newDistance < distance.get(edge.getEnd())) {
                        distance.put(edge.getEnd(), newDistance);
                        previous.put(edge.getEnd(), current);
                        if (!pq.contains(edge.getEnd())) {
                            pq.add(edge.getEnd());
                        }
                    }
                } catch (NullPointerException e) {
                    continue;
                }
            }
        }
        return previous;
    }

    /**
     * 一次随机游走，更新当前边，移动到下一位。（随机游走失败后返回null）
     *
     * @return String 当前随机边的起始节点的字符。
     */
    public String RandomWalk() {
        // 随机游走初始化
        if (this.randomWalkNode == null) {
            // 初始化随机游走的初始边
            // 将key序列化从而可以进行随机选择
            String[] keys = map.keySet().toArray(new String[0]);
            // 使用Random选择一个索引
            Random rand = new Random();
            int randomIndex = rand.nextInt(keys.length);
            // 获取随机元素,初始化结束
            this.randomWalkNode = map.get(keys[randomIndex]).getStart();
            return this.randomWalkNode;
        }

        // 邻边
        List<GraphicEdge> edges = new ArrayList<>();
        // 防止空指针异常
        try {
            for (GraphicEdge e : graph.get(randomWalkNode)) {
                // 判断哪些邻边能够被走通，加入到列表中
                if (e.canWalk()) {
                    edges.add(e);
                }
            }
        } catch (NullPointerException E) {

        }

        // 如果邻边都不能走则表示随机游走中止
        if (edges.size() == 0) {
            return null;
        }
        Random rand = new Random();
        int randomIndex = rand.nextInt(edges.size());
        GraphicEdge randomWalkEdge = edges.get(randomIndex);
        // 标注为已经走过了
        randomWalkEdge.Walk();
        this.randomWalkNode = randomWalkEdge.getEnd();
        return randomWalkNode;
    }

    // 刷新整个图的数据（随机游走的记录清零）
    public void RestartGraphic() {
        for (Entry<String, GraphicEdge> e : map.entrySet()) {
            ((GraphicEdge) e.getValue()).Refresh();
        }
    }

    /**
     * @param {String} start 开始的词
     * @param {String} next 下一个词
     * @return {List<String>} 返回start->List<String>->next中的List<String>
     * @msg 查询两个词之间的桥接词，并且返回一个列表
     */
    public List<String> queryBridgeWords(String start, String next) {
        List<String> words = new ArrayList<>();
        // 没有找到对应的词(开始符号)
        if (!graph.containsKey(start)) {
            return words;
        }

        // 找到开始符号之后
        for (GraphicEdge e : graph.get(start)) {
            // 遍历开始符号连接的所有边
            try {
                for (GraphicEdge e2 : graph.get(e.getEnd())) {
                    // 成功配对了
                    if (e2.getEnd().equals(next)) {
                        words.add(e2.getStart());
                    }
                }
            } catch (Exception e2) {

            }
        }
        return words;
    }

}
