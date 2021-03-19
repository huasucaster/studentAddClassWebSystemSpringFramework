package com.example.sutdentsmanagement.api;

import com.example.sutdentsmanagement.StudentService.UniversityClassService;
import com.example.sutdentsmanagement.exceptions.InvalidUniversityClassException;
import com.example.sutdentsmanagement.model.UniversityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class")
public class UniversityClassController {

    private UniversityClassService universityClassService;

    @Autowired
    public UniversityClassController(UniversityClassService universityClassService){
        this.universityClassService = universityClassService;
    }

    @GetMapping
    List<UniversityClass> getAllClasses(){
        return universityClassService.getAllClasses();
    }

    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<String> addClass(@RequestBody UniversityClass universityClass){
        try{
            UniversityClass savedUniversityClass = universityClassService.addClass(universityClass);
            return ResponseEntity.ok("Add class." + savedUniversityClass.toString());
        } catch (InvalidUniversityClassException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

