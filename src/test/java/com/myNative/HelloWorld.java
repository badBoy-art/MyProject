/**
 * native-0study
 * gcc -I/Library/Java/JavaVirtualMachines/jdk1.8.0_71.jdk/Contents/Home/include/ -I/Library/Java/JavaVirtualMachines/jdk1.8.0_71
 * .jdk/Contents/Home/include/darwin/ -dynamiclib HelloWorldImpl.cpp -o libhell.jnilib
 *
 * @author xuedui.zhao
 * @create 2018-04-23
 */
public class HelloWorld {
    public native void hello();

    static {
        System.loadLibrary("hello");
    }

    public static void main(String[] args) {

        new HelloWorld().hello();
    }
}
