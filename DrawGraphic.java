/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-13 12:32:55
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-13 19:11:41
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DrawGraphic {

    public static void main(String[] args) {
        DrawGraphic d = new DrawGraphic("test.txt");
        d.findMinSource("I", "u");
    }

    // 有向图结构
    private MyGraphic g;
    // 命令列表
    private String command;
    // 记录随机游走过程
    private StringBuilder randomWalkTrace;

    // 接受文件输入路径
    public DrawGraphic(String pathFile) {
        g = new MyGraphic(pathFile);
        command = GenerateDotCommand();
        randomWalkTrace = new StringBuilder();
    }

    /**
     * 
     * @param {String} start 开始字符
     * @param {String} end 结束字符
     * @return String 方法执行提示语（成功或者失败）
     */
    public String findMinSource(String start, String end) {
        List<String> source = null;
        source = g.findShortestPath(start, end);
        if (source == null) {
            return "Fail to find the min source";
        } else {
            StringBuilder DotCommandBuilder = new StringBuilder();
            String s = GenerateDotCommand();
            for (String e : source) {
                System.out.println(e);
                DotCommandBuilder.append(e + " [color=\"lightblue\"];");
            }
            DotCommandBuilder.append(s);
            generateGraphImage(DotCommandBuilder.toString(), "tmp");
            return "success";
        }
    }

    /**
     * 查询桥接词的方法
     * 
     * @param String s 开始词
     * @param String e 结束词
     * @return List<String> 桥接词列表
     */
    public List<String> findBridgeWords(String s, String e) {
        return g.findBridgeWorlds(s, e);
    }

    /**
     * 根据桥接词补全句子的方法
     * 
     * @param String input 输入的句子
     * @return String 返回一个补全后的句子
     */
    public String fixText(String input) {
        String[] data = input.split("[^0-9a-zA-Z]+");
        StringBuilder output = new StringBuilder();
        int l = data.length;
        for (int i = 0; i < l - 1; i++) {
            List<String> d = g.findBridgeWorlds(data[i], data[i + 1]);
            output.append(data[i] + " ");
            // 没有桥接词
            if (d == null) {
                continue;
            } else {
                Random rand = new Random();
                int randomIndex = rand.nextInt(d.size());
                // 随机选择一个
                output.append(d.get(randomIndex) + " ");
            }
        }

        return output.toString();
    }

    /**
     * 执行一次代表随机游走的一步
     * 
     * @return String 当前随机游走的词,返回值为null表示随机游走结束了
     */
    public String randomWalk() {
        String w = g.RandomWalk();
        randomWalkTrace.append(w + "->");
        return w;
    }

    /**
     * 将随机游走的记录写入到文件中
     * @param String pathFile 写入文件的路径，可以默认为"randomWalk.txt"
     */
    public void WriteRandomWalk(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {  
            writer.write(randomWalkTrace.toString());  
            System.out.println("字符串已成功写入文件。");  
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
        //随机游走结束，清空数据
        freshRandomWalk();
        
    }

    /**
     * 随机游走记录清除，（注意，如果是返回操作时一定要单独调用这个方法）
     */
    public void freshRandomWalk() {
        g.RestartGraphic();
        randomWalkTrace.setLength(0);
    }


    /**
     * 根据有向图生成核心的Dot指令
     */
    public String GenerateDotCommand() {
        Map<String, GraphicEdge> map = g.getAllEdge();
        // 获取画图工具
        StringBuilder DotCommandBuilder = new StringBuilder();
        for (Map.Entry<String, GraphicEdge> entry : map.entrySet()) {
            GraphicEdge e = entry.getValue();
            DotCommandBuilder.append(e.getStart() + " -> " + e.getEnd() + " [label=\"" + e.getWeight() + "\"];");
        }
        String c = DotCommandBuilder.toString();
        return c;
    }

    /**
     * 根据dot命令调用dot.exe 注意：该方法要求电脑上已经安装dot才能使用
     * 
     * @param {String} dotContent 输入的dot命令字符串
     * @param {String} outputFileName 输出图片的地址
     */
    public void generateGraphImage(String dotContent, String outputFileName) {
        // 创建一个GraphViz实例，使用不同的变量名
        Graphviz graphGenerator = new Graphviz();
        // 开始构建图
        graphGenerator.addln(graphGenerator.start_graph());
        // 添加DOT格式的内容
        graphGenerator.add(dotContent);
        // 结束图的构建
        graphGenerator.addln(graphGenerator.end_graph());
        // 设定输出图片的类型，使用常量来替代硬编码的字符串
        String outputImageType = "jpg";
        // 写入图到文件
        File out = new File(outputFileName + "." + outputImageType);
        System.out.println(out.toPath());
        graphGenerator.writeGraphToFile(graphGenerator.getGraph(graphGenerator.getDotSource(), outputImageType), out);
    }

}