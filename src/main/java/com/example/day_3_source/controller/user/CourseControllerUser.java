package com.example.day_3_source.controller.user;

import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.AccountDto;
import com.example.day_3_source.model.dto.CourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.services.impl.AccountCourseServiceImpl;
import com.example.day_3_source.services.impl.AccountServiceImpl;
import com.example.day_3_source.services.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/courses"))
public class CourseControllerUser {
    private final CourseServiceImpl courseServiceImpl;
    private final AccountCourseServiceImpl accountCourseServiceImpl;

    @Autowired
    public CourseControllerUser(CourseServiceImpl courseServiceImpl, AccountCourseServiceImpl accountCourseServiceImpl) {
        this.courseServiceImpl = courseServiceImpl;
        this.accountCourseServiceImpl = accountCourseServiceImpl;
    }

    @GetMapping("/allCourses")
    public ResponseEntity<BaseResponseDto<List<CourseDto>>> getAll() {
        return ResponseEntity.ok(courseServiceImpl.getAll());
    }

    @PostMapping("/assignAccountCourse/{accountId}/{courseId}")
    public ResponseEntity<BaseResponseDto<AccountCourseDto>> create(@PathVariable("accountId") Long accountId, @PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(accountCourseServiceImpl.assignByUser(accountId, courseId));
    }

    @DeleteMapping("/unassignedAccountCourse/{accountId}/{courseId}")
    public ResponseEntity<BaseResponseDto<Void>> delete (@PathVariable("accountId") Long accountId, @PathVariable("courseId") Long courseId) {
        return ResponseEntity.ok(accountCourseServiceImpl.deleteAccountCourseByUser(accountId, courseId));
    }
}
