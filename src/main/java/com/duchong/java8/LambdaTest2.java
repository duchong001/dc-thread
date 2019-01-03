package com.duchong.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DUCHONG
 * @since 2019-01-02 15:00
 **/
public class LambdaTest2 {

    public static void main(String[] args) {


        List<Employee> employees = Arrays.asList(
                new Employee("duchong", 18, '男', 0),
                new Employee("duchong", 17, '男', 0),
                new Employee("duchong1", 20, '女', 5000),
                new Employee("duchong2", 18, '女', 8000),
                new Employee("duchong3", 22, '男', 9000),
                new Employee("duchong4", 30, '男', 11000)
        );


        //过滤大于5000
        employees.stream()
                .filter((e) -> e.getSalary() >= 5000)
                .forEach(System.out::println);

        System.out.println("---------------------------------------");
        //获取名称 不去重
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
        //获取名称 去重
        System.out.println("---------------------------------------");
        employees.stream()
                .map(Employee::getName)
                .distinct()
                .forEach(System.out::println);

        //获取唯一性的Name列表
        List<String> employeeList=employees.stream()
                                            .map(Employee::getName)
                                            .distinct()
                                            .collect(Collectors.toList());
    }
}
