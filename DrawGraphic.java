/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-13 12:32:55
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-13 16:50:57
 */

import java.io.File;
import java.util.List;
import java.util.Map;

public class DrawGraphic {

    public static void main(String[] args) {
        DrawGraphic d = new DrawGraphic("test.txt");
        d.findMinSource("I", "u");
    }

    // 有向图结构
    private MyGraphic g;
    //
    public String command;

    public DrawGraphic(String pathFile) {
        g = new MyGraphic(pathFile);
        command = GenerateDotCommand();
    }

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