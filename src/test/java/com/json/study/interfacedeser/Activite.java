package com.json.study.interfacedeser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaoxuedui <zhaoxuedui@''.com>
 * Created on 2020-06-02
 * @Description
 */
@AllArgsConstructor
@NoArgsConstructor
public class Activite {

    private String name;

    private Animal animal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
