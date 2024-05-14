/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-13 12:32:55
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-14 19:39:43
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DrawGraphic {

    public static void main(String[] args) {
        System.out.println("请输入文件路径:");
        Scanner in = new Scanner(System.in);
        DrawGraphic d = new DrawGraphic(in.nextLine());
        String command = "";
        while (true) {
            System.out.println("请选择操作（输入“ESC”退出）");
            System.out.println("1.保存有向图");
            System.out.println("2.查找最短路径");
            System.out.println("3.查找桥接词");
            System.out.println("4.句子补全");
            System.out.println("5.随机游走");
            command = in.nextLine();
            switch (command) {
                case "1":
                    d.generateGraphImage(d.command, "tmp");
                    break;
                case "2":
                    System.out.println("请输入开始符号和终结符号，用空格隔开");
                    String[] data = in.nextLine().split(" ");
                    System.out.println(d.findMinSource(data[0], data[1]));
                    break;
                case "3":
                    System.out.println("请输入开始符号和终结符号，用空格隔开");
                    String[] data2 = in.nextLine().split(" ");
                    List<String> bridge = d.findBridgeWords(data2[0], data2[1]);
                    if (bridge.size() == 0) {
                        System.out.println("没有桥接词");
                    } else {
                        for (String s : bridge) {
                            System.out.println("桥接词是" + s);
                        }
                    }
                    break;
                case "4":
                    System.out.println("请输入文本:");
                    System.out.println("输出为:-->" + d.fixText(in.nextLine()));
                    break;
                case "5":
                    System.out.println("输入n游走下一步，输入s保存数据，输入e退出随机游走");
                    String c = in.nextLine();
                    while (!c.equals("e")) {
                        if (c.equals("n")) {
                            String now = d.randomWalk();
                            if (now == null) {
                                System.out.println("随机游走已经结束了，请退出!");
                            } else {
                                System.out.println("当前随机游走的节点是--->" + now);
                            }
                        }

                        if (c.equals("s")) {
                            d.WriteRandomWalk("out.txt");
                            System.out.println("随机游走结果已经保存在\"out.txt\"中。");
                            break;
                        }
                        System.out.println("输入n游走下一步，输入s保存数据，输入e退出随机游走");
                        c = in.nextLine();
                    }
                    break;
                case "ESC":
                    in.close();
                    return;
                default:
                    System.out.println("错误的命令！");
                    break;
            }
        }
    }

    // 有向图结构
    private MyGraphic g;
    // 命令列表
    public String command;
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
            if (d.size() == 0) {
                continue;
            } else {
                Random rand = new Random();
                int randomIndex = rand.nextInt(d.size());
                // 随机选择一个
                output.append(d.get(randomIndex) + " ");
            }
        }
        
        //倒数第一个不能忘
        output.append(data[data.length - 1]);
        return output.toString();
    }

    /**
     * 执行一次代表随机游走的一步
     * 
     * @return String 当前随机游走的词,返回值为null表示随机游走结束了
     */
    public String randomWalk() {
        String w = g.RandomWalk();
        // 新增，w为null时也会调用toString被写入到文件，这显然是不合理的
        if (w != null) {
            randomWalkTrace.append(w + "->");
        }

        return w;
    }

    /**
     * 将随机游走的记录写入到文件中
     * 
     * @param String pathFile 写入文件的路径，可以默认为"randomWalk.txt"
     */
    public void WriteRandomWalk(String pathFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile))) {
            writer.write(randomWalkTrace.toString());
            System.out.println("字符串已成功写入文件。");
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
        }
        // 随机游走结束，清空数据
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
        graphGenerator
                .add("graph[ fontname = \"Helvetica-Oblique\",fontsize = 12,label = \"some label\",size = \"15,10\" ];"
                        + dotContent);
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