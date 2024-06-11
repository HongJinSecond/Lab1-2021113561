package src.test;

import src.Graph.MyGraphic;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class MyGraphicTest {


    static String[] testCase={"./testCase/testCase_1","./testCase/testCase_2",
            "./testCase/testCase_3","./testCase/testCase_4"};

    static String start="s";
    static String end="e";


    @org.junit.Test
    public void queryBridgeWords_1() {
        MyGraphic draw = new MyGraphic(testCase[0]);
        List<String> words=draw.queryBridgeWords(start,end);
        Collections.sort(words);
        String[] out={"a","b","c","d"};
        assertArrayEquals(out,words.toArray());
    }

    @org.junit.Test
    public void queryBridgeWords_2() {
        MyGraphic draw = new MyGraphic(testCase[1]);
        List<String> words=draw.queryBridgeWords(start,end);
        String[] out={};
        assertArrayEquals(out,words.toArray());
    }

    @org.junit.Test
    public void queryBridgeWords_3() {
        MyGraphic draw = new MyGraphic(testCase[2]);
        List<String> words=draw.queryBridgeWords(start,end);
        String[] out={};
        assertArrayEquals(out,words.toArray());
    }

    @org.junit.Test
    public void queryBridgeWords_4() {
        MyGraphic draw = new MyGraphic(testCase[3]);
        List<String> words=draw.queryBridgeWords(null,end);
        String[] out={};
        assertArrayEquals(out,words.toArray());
    }


}