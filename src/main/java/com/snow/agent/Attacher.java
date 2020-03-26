package com.snow.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * @author badBoy
 * @create 2019-09-06
 */
public class Attacher {

    public static void main(String[] args) throws AttachNotSupportedException, IOException, AgentLoadException, AgentInitializationException {
        // 传入目标 JVM pid
        VirtualMachine vm = VirtualMachine.attach("19309");
        vm.loadAgent("/Users/xuedui.zhao/Projects/myProject/target/mySist-jar-with-dependencies.jar");
    }
}
