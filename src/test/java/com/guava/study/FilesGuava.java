package com.guava.study;

import java.io.File;
import java.nio.charset.Charset;

import com.google.common.io.Files;

/**
 * @author badBoy <badBoy>
 * Created on 2021-05-28
 * @Description
 */
public class FilesGuava {

    public static void main(String[] args) {
        String targetPath = "/Users/zhaoxuedui/Desktop";
        String filePath = targetPath + "/" + "WriteFile" + ".java";
        File targetFile = new File(filePath);
        //1.写入java类的内容
        demoFileAppend(targetFile, "package com.bruce.write;\n");
        demoFileAppend(targetFile, "\n");
        demoFileAppend(targetFile, "/**\n");
        demoFileAppend(targetFile, " *\n");
        demoFileAppend(targetFile, " */\n");
        demoFileAppend(targetFile, "public class WriteFile{\n");
        demoFileAppend(targetFile, "\n");
        demoFileAppend(targetFile, "    protected WriteFile() {\n");
        demoFileAppend(targetFile, "       \n");
        demoFileAppend(targetFile, "    }\n");
        demoFileAppend(targetFile, "}");
        //2.目标路径,要从项目存放位置开始。例如codeproject项目存在code文件夹下

//        try {
//            /**
//             * Files.write(byte[],File) 接收两个参数。
//             * byte[]： 传入原文件内容
//             * File： 写入目标路径+文件名称
//             */
//            Files.write(classContent.getBytes(), targetFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static void demoFileAppend(File newFile, final String contents) {
        try {
            Files.append(contents, newFile, Charset.defaultCharset());
        } catch (Exception e) {
        }
    }

}
