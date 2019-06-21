package com.example.yepa.service;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class YardEmmMoeService implements PageProcessor {
    public final static String startUrl = "https://yard.emm.moe/page/1";
    private Site site = Site.me().
            setRetryTimes(5).setSleepTime(10000).setSleepTime(3000).
            setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
    @Override
    public void process(Page page) {



        List<String> urls = new ArrayList<>();

        if (page.getUrl().toString().contains("https://yard.emm.moe/page")){
            Selectable xpath = page.getHtml().xpath("//*[@id=\"main\"]/article[1]/div/div[2]/header/a/@text()");
            if ("公告".equals(xpath.toString())){
                page.setSkip(true);
                return;
            }
            Selectable selectTitle = page.getHtml().xpath("//*[@id=\"main\"]/article[@class=\"kratos-hentry clearfix\"]/div/div[2]/header/h2/a");
            urls.addAll(selectTitle.links().all());
            //翻页
            Selectable selectPage = page.getHtml().xpath("//*[@id=\"page-footer\"]/ul/li/a[@class=\"next\"]");
            urls.addAll(selectPage.links().all());

            page.addTargetRequests(urls);

            //https://yard.emm.moe/ssr/emmmoe_181211a/
        }else if (page.getUrl().toString().contains("https://yard.emm.moe/")){
//            getItem(page);


//            System.out.printf(page.getUrl().toString());
//            page.putField("url",page.getUrl().toString());
//            page.putField("job",page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/h1/@title").toString());
//            //location experience number createtime prerequisite TODO
//
//            String[] split = page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[2]/@title").toString().split("\\|");
//            if (yaoqiu.size()>=split.length){
//                for (int i = 0; i < split.length; i++) {
//                    page.putField(yaoqiu.get(i),split[i].replaceAll("[\\u00A0]+", ""));
//                }
//            }
//
//            page.putField("salary", page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/strong/text()"));
//            page.putField("content", page.getHtml().xpath("/html/body/div[3]/div[2]/div[3]/div[1]/div/html()"));
//            page.putField("company", page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[1]/a[1]/text()"));
//            //https://yard.emm.moe/emmvip/emmmoe_190216a/

        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public void getItem(Page page){
        String baseContextXpath = "//*[@id=\"main\"]/article/div[@class=\"kratos-hentry kratos-post-inner clearfix\"]/div[@class=\"kratos-post-content\"]";
        //baiduyunUrl
        //*[@id="main"]/article/div[@class="kratos-hentry kratos-post-inner clearfix"]/div[@class="kratos-post-content"]/p/a[@class="downbtn downcloud"]

//      baiduyunUrl
        Selectable baiduyunUrlSelectable = page.getHtml().xpath(baseContextXpath+ "/p/a[@class=\"downbtn downcloud\"]");
        String baiduyunUrlStrPre = baiduyunUrlSelectable.links().all().get(0);
        page.putField("baiduyunUrl",StringUtils.substringAfter(baiduyunUrlStrPre,"goto="));

        //itemUrl
        page.putField("itemUrl",page.getUrl().toString());

//        title
        page.putField("title",page.getHtml().xpath("//*[@id=\"main\"]/article/div[@class=\"kratos-hentry kratos-post-inner clearfix\"]/header/h1/text()"));

//            baiduyunPass
        String contextPHTML = page.getHtml().xpath(baseContextXpath + "/p").nodes().toString();
        String baiduyunPassResult = StringUtils.substringBetween(contextPHTML, "百度网盘文件提取码：", "(傻奈最帅！)");
        page.putField("baiduyunPass",baiduyunPassResult);

//            zipPassword
        String zipPasswordResult = StringUtils.substringBetween(contextPHTML, "解压密码：","</p>");
        if (zipPasswordResult.startsWith("<script")){
            //TODO 加密的爬不到需要执行js才能获得结果
            page.setSkip(true);
            return;
        }
        page.putField("zipPassword",zipPasswordResult);



//*[@id="main"]/article/div[1]/div/p[6]/a
//*[@id="main"]/article/div[1]/div/p[8]/a
    }





    public static void main(String[] args) {
        Spider.create(new YardEmmMoeService()).addUrl(startUrl).thread(10).run();

    }

}
