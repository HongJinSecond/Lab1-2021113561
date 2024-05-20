/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-13 12:32:55
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-17 13:04:46
 */
package src.Graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
                    d.generateGraphImage(d.command, "src/pic/tmp");
                    break;
                case "2":
                    System.out.println("请输入开始符号和终结符号，用空格隔开");
                    String[] data = in.nextLine().split(" ");
                    if (data.length == 1) {
                        List<String> outs = d.calcShortestPath(data[0]);
                        for (String s : outs) {
                            //获得关键词
                            String target = s.split(":")[0].split(" ")[1];
                            System.out.println("现在到"+target+"的路径是:\n"+s);
                        }
                    } else {
                        System.out.println(d.calcShortestPath(data[0], data[1]));

                    }
                    break;
                case "3":
                    System.out.println("请输入开始符号和终结符号，用空格隔开");
                    String[] data2 = in.nextLine().split(" ");
                    List<String> bridge = d.queryBridgeWords(data2[0], data2[1]);
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
                    System.out.println("输出为:-->" + d.generateNewText(in.nextLine()));
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
        generateGraphImage(command, "src/pic/tmp");
    }

    /**
     * 
     * @param {String} start 开始字符
     * @param {String} end 结束字符
     * @return String 方法执行提示语（成功或者失败）
     */
    public String calcShortestPath(String start, String end) {
        Map<String, String> pre = g.calcShortestPath(start);

        if (pre == null) {
            return "Fail to find the min source";
        } else {
            List<String> source = findPreSource(start, end, pre);
            StringBuilder trace = new StringBuilder();
            trace.append("To "+end+":");
            StringBuilder DotCommandBuilder = new StringBuilder();
            String s = GenerateDotCommand();
            for (String e : source) {
                DotCommandBuilder.append(e + " [color=\"red\"];");
                trace.append(e + "->");
            }
            DotCommandBuilder.append(s);
            System.out.println("--------现在正在生成"+start+"--->"+end+"的图片-------");
            generateGraphImage(DotCommandBuilder.toString(), "src/pic/tmp_" + end);
            return trace.toString();
        }
    }

    /**
     * 选做：当只输入一个开始符号时，返回该符号到所有其他节点的值
     * 
     * @param start
     * @return
     */
    public List<String> calcShortestPath(String start) {
        List<String> mixTrace = new ArrayList<>();
        List<String> nodes = g.GraphicNodes;
        for (String e : nodes) {
            if (!e.equals(start)) {
                mixTrace.add(calcShortestPath(start, e));
            }
        }
        return mixTrace;
    }

    /**
     * 根据反向映射表pre构建start->target的路径，返回值是一个List<String>类型
     * 
     * @param String             start 开始节点
     * @param String             target 目标节点
     * @param Map<String,String>
     */
    public List<String> findPreSource(String start, String target, Map<String, String> pre) {

        // 找到目标节点，回溯构建路径
        List<String> path = new ArrayList<>();
        for (String node = target; node != null; node = pre.get(node)) {
            path.add(0, node); // 在列表开始处添加节点，以构建反向路径
        }
        return path;
    }

    /**
     * 查询桥接词的方法
     * 
     * @param String s 开始词
     * @param String e 结束词
     * @return List<String> 桥接词列表
     */
    public List<String> queryBridgeWords(String s, String e) {
        return g.queryBridgeWords(s, e);
    }

    /**
     * 根据桥接词补全句子的方法
     * 
     * @param String input 输入的句子
     * @return String 返回一个补全后的句子
     */
    public String generateNewText(String input) {
        String[] data = input.split("[^0-9a-zA-Z]+");
        StringBuilder output = new StringBuilder();
        int l = data.length;
        for (int i = 0; i < l - 1; i++) {
            List<String> d = g.queryBridgeWords(data[i], data[i + 1]);
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

        // 倒数第一个不能忘
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
        graphGenerator.add(dotContent);
        // 结束图的构建
        graphGenerator.addln(graphGenerator.end_graph());
        // 设定输出图片的类型，使用常量来替代硬编码的字符串
        String outputImageType = "jpg";
        // 写入图到文件
        File out = new File(outputFileName + "." + outputImageType);
        graphGenerator.writeGraphToFile(graphGenerator.getGraph(graphGenerator.getDotSource(), outputImageType), out);
    }

    /**
     * 展示某个有向图，（保存为JPG文件）
     * 
     * @param G
     */
    public void showDirectedGraph(MyGraphic G) {
        this.g = G;
        command = GenerateDotCommand();
        randomWalkTrace = new StringBuilder();
        generateGraphImage(command, "src/pic/tmp");
    }
}