
/**
 * @author xuedui.zhao
 * @create 2018-12-02
 */
public class Foo {

    static boolean boolValue;

    public static void main(String[] args) {
        //将这个 true 替换为 2 或者 3，再看看打印结果
        boolValue = Boolean.valueOf(""+2);
        if (boolValue) {
      System.out.println("Hello java");
        }
        if (boolValue == true) {
      System.out.println("Hello JVM");
        }
    }
}
