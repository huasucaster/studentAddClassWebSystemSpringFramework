package com.example.sutdentsmanagement.StudentService;

import com.example.sutdentsmanagement.dao.StudentDao;
import com.example.sutdentsmanagement.dao.UniversityClassDao;
import com.example.sutdentsmanagement.exceptions.InvalidUniversityClassException;
import com.example.sutdentsmanagement.exceptions.StudentEmptyNameException;
import com.example.sutdentsmanagement.exceptions.StudentNonExistException;
import com.example.sutdentsmanagement.mapper.StudentMapper;
import com.example.sutdentsmanagement.model.Student;
import com.example.sutdentsmanagement.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentDao studentDao;
    private UniversityClassDao universityClassDao;
    private StudentMapper studentMapper;


    @Autowired
    public StudentService(StudentDao studentDao,
                          UniversityClassDao universityClassDao,
                          StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.universityClassDao = universityClassDao;
        this.studentMapper = studentMapper;
    }

//    @Autowired
//    public StudentService(StudentDao studentDao) {
//        this.studentDao = studentDao;
//    }

    public Student addStudent(Student student) {
        if(student.getName().isEmpty()){
            throw new StudentEmptyNameException("Student name cannot be empty");
        }
        return studentDao.save(student);
    }

    public Student updateStudent(Student student){
        if(student.getId() == null || !studentDao.existsById(student.getId())){
            throw new StudentNonExistException("Cannot find student Id");
        }

        return studentDao.save(student);
    }

    public Student assignClass(Long studentId, Long classId){
        if(!studentDao.existsById(studentId)){
            throw new StudentNonExistException("Cannot find student id" + studentId);
        }
        if(!universityClassDao.existsById(classId)){
            throw new InvalidUniversityClassException("Cannot find class Id" + classId);
        }

        Student student = getStudentById(studentId).get();
        UniversityClass universityClass = universityClassDao.findById(classId).get();

        student.setUniversityClass(universityClass);
        return studentDao.save(student);

    }

    public List<Student> getAllStudents(){
        return (List<Student>) studentDao.findAll();
    }

    public Optional<Student> getStudentById(Long id){
        return studentDao.findById(id);
    }

    public List<Student> getStudentsByName(String name){
        return studentDao.findByName(name);
    }


    //Use MySQL to get the specific students and classes.
    public List<Student> getStudentsContainName(String name){
        return studentMapper.getStudentsContainStringInName("%" + name + "%");
    }

    public List<Student> getStudentsInClass(int year, int number){
        return studentMapper.getStudentInClass(year, number);
    }
}
