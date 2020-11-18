package com.lahmxu.springbean.spring01.AnnotationAssembly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value="student1")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    @Value("1")
    int id;
    @Value("student_name_1")
    String name;

    public void init(){
        System.out.println("hello...........");
    }

    public Student create(){
        return new Student(101,"KK101");
    }
}
