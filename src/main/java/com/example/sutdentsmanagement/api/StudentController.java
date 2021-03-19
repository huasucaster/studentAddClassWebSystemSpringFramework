package com.example.sutdentsmanagement.api;

import com.example.sutdentsmanagement.StudentService.StudentService;
import com.example.sutdentsmanagement.exceptions.InvalidUniversityClassException;
import com.example.sutdentsmanagement.exceptions.StudentEmptyNameException;
import com.example.sutdentsmanagement.exceptions.StudentNonExistException;
import com.example.sutdentsmanagement.model.Student;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> getAllStudents(){ return studentService.getAllStudents(); }


    @GetMapping("/name")
    //public List<Student> getStudents() {return studentService.getAllStudents();}
    //Localhost: 8080/api/student/name?name=Derek
    public List<Student> getStudent(@RequestParam String name){
        return studentService.getStudentsByName(name);
    }

    @GetMapping("/contain_name")
    //Localhost: 8080/api/student/contain_name?name=T
    public List<Student> getStudentsContainName(@RequestParam String name){
        return studentService.getStudentsContainName(name);
    }

    @GetMapping("/class")
    public List<Student> getStudentsInClass(@RequestParam int year,
                                           @RequestParam int number){
        return studentService.getStudentsInClass(year,number);
    }

    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Student student){
        try{
            Student savedStudent = studentService.addStudent(student);
            return ResponseEntity.ok("Registerd student."+savedStudent.toString());
        } catch (StudentEmptyNameException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "assignclass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId,
                                              @PathVariable("cid") Long classId){
        try{
            Student updatedStudent = studentService.assignClass(studentId, classId);
            return ResponseEntity.ok("Assign calss." + updatedStudent.toString());
        } catch (StudentNonExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidUniversityClassException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
