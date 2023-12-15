package com.example.day_3_source.repository;

import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.AccountCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountCourseRepository extends JpaRepository<AccountCourse, Long> {
    Optional<AccountCourse> findById(Long id);
    List<AccountCourse> findAccountCoursesByAccount_AccountId(Long accountId);
    List<AccountCourse> findAccountCoursesByCourse_CourseId(Long courseId);
    @Query("select ac from AccountCourse ac where ac.course.courseId = :courseId and ac.account.accountId = :accountId")
    AccountCourse getAccountCourseByCourseIdAndAccountId(Long courseId, Long accountId);
    AccountCourseDto findAccountCourseByAccount_AccountIdAndCourse_CourseId(Long accountId, Long courseId);
}
