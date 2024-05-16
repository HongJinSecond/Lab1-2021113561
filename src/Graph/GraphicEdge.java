/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-11 19:26:56
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-13 13:21:33
 */

// 图结构的一条边
package src.Graph;
import java.util.Objects;

public class GraphicEdge {
    // 起始点的字符
    private String StartNode;
    // 目标点的字符
    private String EndNode;
    // 权重
    private Integer weight;
    // 记录随机游走时是否被经过
    private boolean IsWalked = false;

    public GraphicEdge(String s, String e) {
        this.StartNode = s;
        this.EndNode = e;
        this.weight = 1;
    }

    public void addWeight() {
        this.weight++;
    }

    public String getStart() {
        return StartNode;
    }

    public String getEnd() {
        return EndNode;
    }

    public Integer getWeight() {
        return weight;
    }

    public void Walk() {
        this.IsWalked = true;
    }

    public boolean canWalk() {
        return !this.IsWalked;
    }

    public void Refresh() {
        this.IsWalked = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GraphicEdge edge = (GraphicEdge) o;
        return edge.getStart().equals(this.StartNode) && edge.getEnd().equals(this.EndNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.StartNode, this.EndNode);
    }
}