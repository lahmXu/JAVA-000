package com.lahmxu.springbean.spring02;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {

    private int id;

    private String name;

    public static Student create(){
        Student student = new Student();
        student.setId(1);
        student.setName("NoName");
        return student;
    }
}
