package com.example.sutdentsmanagement.mapper;

import com.example.sutdentsmanagement.model.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper
public interface StudentMapper {

    //SELECT * FROM student where name LIKE %tT
    @Select("SELECT * FROM student where name LIKE #{name} ")
    List<Student> getStudentsContainStringInName(@Param("name") String name);

    @Select("SELECT * FROM student where university_class_id IN" +
            "(SELECT id FROM university_class where year = #{year} AND number = #{number})")

    List<Student> getStudentInClass(@Param("year") int year, @Param("number") int number);

}
