package com.apachecommon.cli;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.junit.Test;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-04-22
 * @Description 开发命令行工具
 * Apache Commons CLI 支持多种输入参数格式，主要支持的格式有以下几种：
 * <p>
 * POSIX（Portable Operating System Interface of Unix）中的参数形式，例如 tar -zxvf foo.tar.gz
 * GNU 中的长参数形式，例如 du --human-readable --max-depth=1
 * Java 命令中的参数形式，例如 java -Djava.net.useSystemProxies=true Foo
 * 短杠参数带参数值的参数形式，例如 gcc -O2 foo.c
 * 长杠参数不带参数值的形式，例如 ant – projecthelp
 * <a href="https://www.ibm.com/developerworks/cn/java/j-lo-commonscli/"></a>
 */
public class CliStudy {

    public static void main(String[] args) throws ParseException {
        simpleTest(args);
    }

    public static void simpleTest(String[] args) throws ParseException {
        Options options = createOptions();
        CommandLine cmd = new DefaultParser().parse(options, args);

        //查询交互
        //你的程序应当写在这里，从这里启动
        if (cmd.hasOption("h")) {
            String formatstr = "CLI  cli  test";
            HelpFormatter hf = new HelpFormatter();
            hf.printHelp(formatstr, "", options, "");
            return;
        }

        if (cmd.hasOption("t")) {
            System.out.printf("system time has setted  %s \n", cmd.getOptionValue("t"));
            return;
        }

        System.out.println("error");
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption("h", false, "list help");//false代表不强制有
        options.addOption("t", true, "set time on system");
        return options;
    }

}
