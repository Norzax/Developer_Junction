package com.example.day_3_source.model.mapper.Impl;

import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.entity.Account;
import com.example.day_3_source.model.entity.AccountCourse;
import com.example.day_3_source.model.entity.Course;
import com.example.day_3_source.model.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountCourseMapper implements Mapper<AccountCourse, AccountCourseDto>{

    @Override
    public List<AccountCourseDto> mapListToDtoList(List<AccountCourse> accountCoursEntities) {
        return accountCoursEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountCourseDto mapEntityToDto(AccountCourse accountCourse) {
        if (accountCourse == null) {
            return null;
        }

        AccountCourseDto accountCourseDto = new AccountCourseDto();
        accountCourseDto.setAccountCourseId(accountCourse.getAccountCourseId());

        if (accountCourse.getAccount() != null && accountCourse.getCourse() != null) {
            accountCourseDto.setAccountId(accountCourse.getAccount().getAccountId());
            accountCourseDto.setCourseId(accountCourse.getCourse().getCourseId());
        }

        accountCourseDto.setPurchasePrice(accountCourse.getPurchasePrice());
        accountCourseDto.setRegstrationDate(accountCourse.getRegstrationDate());

        return accountCourseDto;
    }

    @Override
    public List<AccountCourse> mapDtoListToList(List<AccountCourseDto> accountCoursesDto) {
        return accountCoursesDto.stream()
                .map(this::mapDtoToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountCourse mapDtoToEntity(AccountCourseDto accountCourseDto) {
        AccountCourse accountCourse = new AccountCourse();
        if(accountCourseDto.getAccountCourseId() !=  null) {
            accountCourse.setAccountCourseId(accountCourseDto.getAccountCourseId());
        }
        if(accountCourseDto.getCourseId() != null){
            Course course = new Course();
            course.setCourseId(accountCourseDto.getCourseId());
            accountCourse.setCourse(course);
        }
        if(accountCourseDto.getAccountId() != null){
            Account account = new Account();
            account.setAccountId(accountCourseDto.getAccountId());
            accountCourse.setAccount(account);
        }
        accountCourse.setPurchasePrice(accountCourseDto.getPurchasePrice());
        accountCourse.setRegstrationDate(accountCourseDto.getRegstrationDate());

        return accountCourse;
    }
}
