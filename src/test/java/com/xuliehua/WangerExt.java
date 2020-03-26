package com.xuliehua;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-03-13
 * @Description
 */
public class WangerExt implements Externalizable {
    private static final long serialVersionUID = 3151812628068632864L;
    private String name;
    private String add;
    private int age;
    private transient String content = "是的，我将会被序列化，不管我是否被transient关键字修饰";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(add);
        out.writeInt(age);
        out.writeObject(content);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        add = (String) in.readObject();
        age = in.readInt();
        content = (String) in.readObject();
    }

    @Override
    public String toString() {
        return "WangerExt{" + "name=" + name + ",age=" + age + ",add=" + add + ",content=" + content + "}";
    }
}
