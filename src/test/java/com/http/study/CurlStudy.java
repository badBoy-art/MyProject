package com.http.study;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import com.guava.study.JsonUtil;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-28
 * @Description
 */
public class CurlStudy {

    public static String execCurl(String[] cmds) {
        ProcessBuilder process = new ProcessBuilder(cmds);
        Process p;
        try {
            p = process.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            return builder.toString();

        } catch (IOException e) {
            System.out.print("error");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        List<String> datas = demoFileRead("/Users/zhaoxuedui/Desktop/1.txt");
        StringBuffer stringBuffer = new StringBuffer();
        //System.out.println(datas.get(1));
        AtomicInteger i = new AtomicInteger(1);
        datas.subList(1, datas.size()).forEach(x -> {
            List<String> swiftPdfUrls = Splitter.on("\t")
                    .trimResults()
                    .omitEmptyStrings()
                    .splitToList(x);

            String city = null;
            String keyWord = null;
            try {
                city = java.net.URLEncoder.encode("北京", StandardCharsets.UTF_8.name());
                keyWord = java.net.URLEncoder.encode("烤鸭", StandardCharsets.UTF_8.name());

                String url = "https://********?keyword=" + keyWord + "&cityName=" + city + "&cursor=&count=1";
                String[] cmds = {"curl",
                        url,
                        "-H", "Connection: keep-alive", "-H", "'sec-ch-ua: \" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"'",
                        "-H", "Accept: application/json, text/plain, */*",
                        "-H", "x-csrf-token: v1BN37zBnUDpMGDuHJbtEvm3",
                        "-H", "sec-ch-ua-mobile: ?0",
                        "-H", "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36",
                        "-H", "Sec-Fetch-Site: same-origin",
                        "-H", "Sec-Fetch-Mode: cors",
                        "-H", "Sec-Fetch-Dest: empty",
                        "-H", "Referer: https://vc-admin.corp.xxx.com/admin/poi-search/simulate",
                        "-H", "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8",
                        "-H", "Cookie: ",
                        "--compressed"};

                Thread.sleep(20);
                MessageVO vo = JsonUtil.json2Bean(execCurl(cmds), MessageVO.class);
                // System.out.println(swiftPdfUrls.get(0));
                stringBuffer.append(swiftPdfUrls.get(0)).append("\t").append(swiftPdfUrls.get(1)).append("\t")
                        .append(swiftPdfUrls.get(2)).append("\t").append(swiftPdfUrls.get(3)).append("\t")
                        .append(CollectionUtils.isNotEmpty(vo.getData().getSearchDataVOs()) ? vo.getData().getSearchDataVOs().get(0).getKsPoi().getPoiId() + "" : "").append("\n");
                System.out.println(i.getAndIncrement());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        demoFileWrite("/Users/zhaoxuedui/Desktop/1over.txt", stringBuffer.toString());
        System.out.println("over");
    }

    public static List<String> demoFileRead(final String testFilePath) {

        File testFile = new File(testFilePath);
        List<String> lines = null;
        try {
            lines = Files.readLines(testFile, Charsets.UTF_8);
        } catch (Exception e) {
        }

        return lines;
    }

    public static void demoFileWrite(final String fileName, final String contents) {
        final File newFile = new File(fileName);
        try {
            Files.write(contents.getBytes(), newFile);
        } catch (Exception e) {
        }
    }

    public static void demoFileAppend(File newFile, final String contents) {
        try {
            Files.append(contents, newFile, Charset.defaultCharset());
        } catch (Exception e) {
        }
    }
}
