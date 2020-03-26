package com.clone.study;

import org.junit.Test;

/**
 * @author xuedui.zhao
 * @create 2019-07-16
 */
public class CloneStudy {

    @Test
    public void testPerson() throws Exception {
        Person person = new Person("zhangsan", "man");
        Person newPerson = person.clone();
        person.setName("lisi");
        System.out.println(person);
        System.out.println(newPerson);
    }

    /**
     * 浅度克隆
     *
     * @throws Exception
     */
    @Test
    public void testStudent() throws Exception {
        Class aclass = new Class("shuxue", 45);
        Student student = new Student("zhangsan", aclass);
        Student newStudent = student.clone();
        student.setName("lisi");
        student.getAClass().setName("yuwen");
        student.getAClass().setClassTime(90);

        System.out.println(student);
        System.out.println(newStudent);
    }

    /**
     * 深度克隆
     *
     * @throws Exception
     */
    @Test
    public void testTeacher() throws Exception {
        Class aclass = new Class("shuxue", 45);
        Teacher teacher = new Teacher("zhangsan", aclass);
        Teacher newTeacher = teacher.clone();
        teacher.setName("lisi");
        teacher.getAClass().setName("yuwen");
        teacher.getAClass().setClassTime(90);

        System.out.println(teacher);
        System.out.println(newTeacher);
    }
}
