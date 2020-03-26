# coding=utf-8

from com.javacallpython.study import GroovyController

from calculatorClazz import Calculator


# 在Python中实现Java接口:
class FruitController(GroovyController):
    #实现接口方法
    def controllFruit(self, fruit):
        #python中调用java方法
        fruit.show()

        if (fruit.getType() == "apple"):
            print "controllFruit Python Apple"
        if (fruit.getType() == "Orange"):
            print "controllFruit Python Orange"

        print "end"

    #新定义方法
    def printFruit(self, fruit):
        fruit.show()

        if (fruit.getType() == "apple"):
            print ("printFruit Python Apple")

        if (fruit.getType() == "Orange"):
            print ("printFruit Python Orange")

        print "end"

    def power(self,x,y):
        cal = Calculator()
        return cal.power(x,y)