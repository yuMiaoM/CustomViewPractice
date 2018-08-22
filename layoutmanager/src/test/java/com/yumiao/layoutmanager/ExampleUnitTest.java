package com.yumiao.layoutmanager;

import com.alibaba.fastjson.JSON;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.jar.JarInputStream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private Document document;
    private String attr;
    private Elements select;

    @Test
    public void addition_isCorrect() throws IOException {
        document = Jsoup.connect("http://www.5zdm.com").get();
        select = document.getElementsByAttributeValue("class","p1 m1");
        JSON.parse("");
        for(Element e:select){
            String title=e.select("a").attr("title");
            String imgSrc=e.select("a").select("img").attr("data-original");
            System.out.println("title:"+title  + "---  imgSrc: "+imgSrc);

        }
    }
}