package com.javacallpython.study;

import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 * @author xuedui.zhao
 * @create 2018-08-19
 */
public class study {
    
    @Test
    public void test01() throws Exception{
        String exe = "python";
        String command = "/Users/xuedui.zhao/Projects/myProject/python/python/study/Hello.py";
        String num1 = "1";
        String num2 = "2";
        String[] cmdArr = new String[] {exe, command, num1, num2};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String str = dis.readLine();
        process.waitFor();
        System.out.println(str);
    }
    
    @Test
    public void test02() {
        System.setProperty("python.home", "/Users/xuedui.zhao/jython2.7.0");
        String python = "/Users/xuedui.zhao/Projects/myProject/python/python/study/HelloWorld.py";
        PythonInterpreter interp = new PythonInterpreter();
        interp.execfile(python);
        interp.cleanup();
        interp.close();
    }
    
    @Test
    public void test03() {
       //python 面向函数式编程
       System.setProperty("python.home","/Users/xuedui.zhao/jython2.7.0");
       String pythonFunc = "/Users/xuedui.zhao/Projects/myProject/python/python/study/calculatorFunc.py";
       PythonInterpreter pythonInterpreter = new PythonInterpreter();
       
       pythonInterpreter.execfile(pythonFunc);
        // 调用Python程序中的函数
        PyFunction pyf = pythonInterpreter.get("power", PyFunction.class);
        PyObject dddRes = pyf.__call__(Py.newInteger(2), Py.newInteger(3));
        System.out.println(dddRes);
        pythonInterpreter.cleanup();
        pythonInterpreter.close();
       
    }
    
    @Test
    public void test04() {
        System.setProperty("python.home","/Users/xuedui.zhao/jython2.7.0");
        String pythonClass = "/Users/xuedui.zhao/Projects/myProject/python/python/study/calculatorClazz.py";
        // python对象名
        String pythonObjName = "cal";
        // python类名
        String pythonClazzName = "Calculator";
        
        PythonInterpreter pi2 = new PythonInterpreter();
        // 加载python程序
        pi2.execfile(pythonClass);
        // 实例化python对象
        pi2.exec(pythonObjName + "=" + pythonClazzName + "()");
        // 获取实例化的python对象
        PyObject pyObj = pi2.get(pythonObjName);
        // 调用python对象方法,传递参数并接收返回值
        PyObject result = pyObj.invoke("power", new PyObject[] {Py.newInteger(2), Py.newInteger(3)});
        double power = Py.py2double(result);
        System.out.println(power);
    
        pi2.cleanup();
        pi2.close();
    }
    
    @Test
    public void test05() {
        System.setProperty("python.home","/Users/xuedui.zhao/jython2.7.0");
        // python程序路径
        String python = "/Users/xuedui.zhao/Projects/myProject/python/python/study/FruitController.py";
        // python实例对象名
        String pyObjName = "pyController";
        // python类名
        String pyClazzName = "FruitController";
    
        Fruit apple = new Apple();
        Fruit orange = new Orange();
    
        PythonInterpreter interpreter = new PythonInterpreter();
        // 如果在Python程序中引用了第三方库,需要将这些被引用的第三方库所在路径添加到系统环境变量中
        // 否则,在执行Python程序时将会报错: ImportError: No module named xxx
        PySystemState sys = interpreter.getSystemState();
        sys.path.add("/Users/xuedui.zhao/Projects/myProject/python/python/study");
    
        // 加载Python程序
        interpreter.execfile(python);
        // 实例 Python对象
        interpreter.exec(pyObjName + "=" + pyClazzName + "()");
    
        // 1.在Java中获取Python对象,并将Python对象转换为Java对象
        // 为什么能够转换? 因为Python类实现了Java接口,通过转换后的Java对象只能调用接口中定义的方法
        GroovyController controller = (GroovyController) interpreter.get(pyObjName).__tojava__(GroovyController.class);
        controller.controllFruit(apple);
        controller.controllFruit(orange);
    
        // 2.在Java直接通过Python对象调用其方法
        // 既可以调用实现的Java接口方法,也可以调用Python类自定义的方法
        PyObject pyObject = interpreter.get(pyObjName);
        pyObject.invoke("controllFruit", Py.java2py(apple));
        pyObject.invoke("controllFruit", Py.java2py(orange));
        pyObject.invoke("printFruit", Py.java2py(apple));
        pyObject.invoke("printFruit", Py.java2py(orange));
    
        // 3.在Java中获取Python类进行实例化对象: 没有事先创建 Python对象
        PyObject pyClass = interpreter.get("FruitController");
        PyObject pyObj = pyClass.__call__();
        pyObj.invoke("controllFruit", Py.java2py(apple));
        pyObj.invoke("controllFruit", Py.java2py(orange));
    
        PyObject power = pyObj.invoke("power", new PyObject[] {Py.newInteger(2), Py.newInteger(3)});
        if(power != null) {
            double p = Py.py2double(power);
            System.out.println(p);
        }
    
        interpreter.cleanup();
        interpreter.close();
    }
}
