package com.example.day_3_source.controller.admin;

import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/course"))
public class CourseControllerAdmin {
    private final CourseServiceImpl courseServiceImpl;

    @Autowired
    public CourseControllerAdmin(CourseServiceImpl courseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
    }

    @GetMapping("/listCourse")
    public ResponseEntity<BaseResponseDto<List<CourseDto>>> getAll() {
        return ResponseEntity.ok(courseServiceImpl.getAll());
    }

    @GetMapping("/findCourse/{id}")
    public ResponseEntity<BaseResponseDto<CourseDto>> getById(@PathVariable("id") Long courseId) {
        return ResponseEntity.ok(courseServiceImpl.getById(courseId));
    }

    @PostMapping("/createCourse")
    public ResponseEntity<BaseResponseDto<CourseDto>> create(@RequestBody() CourseDto course) {
        return ResponseEntity.ok(courseServiceImpl.create(course));
    }

    @PutMapping("/updateCourse/{id}")
    public ResponseEntity<BaseResponseDto<CourseDto>> update(@PathVariable("id") Long courseId, @RequestBody() CourseDto course) {
        return ResponseEntity.ok(courseServiceImpl.update(courseId, course));
    }

    @DeleteMapping("/deleteCourse/{id}")
    public ResponseEntity<BaseResponseDto<Void>> delete(@PathVariable("id") Long courseId) {
        return ResponseEntity.ok(courseServiceImpl.delete(courseId));
    }
}
