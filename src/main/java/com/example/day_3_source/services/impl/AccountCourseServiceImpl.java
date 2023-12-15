package com.example.day_3_source.services.impl;

import com.example.day_3_source.exception.ApplicationException;
import com.example.day_3_source.model.dto.AccountCourseDto;
import com.example.day_3_source.model.dto.response.BaseResponseDto;
import com.example.day_3_source.model.entity.Account;
import com.example.day_3_source.model.entity.AccountCourse;
import com.example.day_3_source.model.entity.Course;
import com.example.day_3_source.model.mapper.Impl.AccountCourseMapper;
import com.example.day_3_source.repository.AccountCourseRepository;
import com.example.day_3_source.repository.AccountRepository;
import com.example.day_3_source.repository.CourseRepository;
import com.example.day_3_source.services.AccountCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountCourseServiceImpl implements AccountCourseService {
    private final AccountCourseRepository accountCourseRepository;
    private final AccountCourseMapper accountCourseMapper;
    private final AccountRepository accountRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public AccountCourseServiceImpl(AccountCourseRepository accountCourseRepository, AccountCourseMapper accountCourseMapper, AccountRepository accountRepository, CourseRepository courseRepository) {
        this.accountCourseRepository = accountCourseRepository;
        this.accountCourseMapper = accountCourseMapper;
        this.accountRepository = accountRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public BaseResponseDto<List<AccountCourseDto>> getAll() {
        BaseResponseDto<List<AccountCourseDto>> responseDto = new BaseResponseDto<>();
        List<AccountCourse> listAccountCourse = accountCourseRepository.findAll();
        List<AccountCourseDto> listAccountCourseDto = accountCourseMapper.mapListToDtoList(listAccountCourse);

        try {
            responseDto.setStatus("200");
            responseDto.setData(listAccountCourseDto);
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountCourseDto> getById(Long id) {
        BaseResponseDto<AccountCourseDto> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(accountCourseMapper.mapEntityToDto(accountCourseRepository.findById(id).orElse(null)));
            responseDto.setMessage("Success");

        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<List<AccountCourseDto>> getAccountCoursesByAccountId (Long accountId){
        BaseResponseDto<List<AccountCourseDto>> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(accountCourseMapper.mapListToDtoList(accountCourseRepository.findAccountCoursesByAccount_AccountId(accountId)));
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public BaseResponseDto<List<AccountCourseDto>> getAccountCoursesByCourseId (Long courseId){
        BaseResponseDto<List<AccountCourseDto>> responseDto = new BaseResponseDto<>();

        try {
            responseDto.setStatus("200");
            responseDto.setData(accountCourseMapper.mapListToDtoList(accountCourseRepository.findAccountCoursesByCourse_CourseId(courseId)));
            responseDto.setMessage("Success");
        } catch (ApplicationException e) {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @Override
    public boolean isAssigned(Long courseId, Long accountId) {
        return (accountCourseMapper
                .mapEntityToDto(accountCourseRepository
                .getAccountCourseByCourseIdAndAccountId(courseId, accountId)) != null) ? true : false;
    }

    @Override
    public BaseResponseDto<AccountCourseDto> create(AccountCourseDto accountCourseDto) {
        AccountCourse accountCourse = accountCourseMapper.mapDtoToEntity(accountCourseDto);
        Account account = new Account();
        account.setAccountId(accountCourseDto.getAccountId());

        Course course = new Course();
        course.setCourseId(accountCourseDto.getCourseId());

        accountCourse.setAccount(account);
        accountCourse.setCourse(course);

        Date createdDate = new Date();
        accountCourse.setRegstrationDate(createdDate);

        BaseResponseDto<AccountCourseDto> responseDto = new BaseResponseDto<>();
        if(!isAssigned(accountCourseDto.getCourseId(), accountCourseDto.getAccountId())) {
            accountCourse.setPurchasePrice(courseRepository.getById(course.getCourseId()).getPrice());
            accountCourseRepository.save(accountCourse);

            responseDto.setStatus("200");
            responseDto.setData(accountCourseDto);
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("409");
            responseDto.setData(null);
            responseDto.setMessage("This course already assigned, cannot assign more!");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountCourseDto> assignByUser(Long accountId, Long courseId) {
        AccountCourseDto newAccountCourse = new AccountCourseDto();
        newAccountCourse.setAccountId(accountId);
        newAccountCourse.setCourseId(courseId);
        Date createdDate = new Date();
        newAccountCourse.setRegstrationDate(createdDate);

        BaseResponseDto<AccountCourseDto> responseDto = new BaseResponseDto<>();
        if (!isAssigned(courseId, accountId)) {
            AccountCourse existingAccountCourse = accountCourseRepository.getAccountCourseByCourseIdAndAccountId(courseId, accountId);
            if (existingAccountCourse == null) {
                Account account = accountRepository.findById(accountId).orElse(null);
                Course course = courseRepository.findById(courseId).orElse(null);

                if (account != null && course != null) {
                    AccountCourse accountCourse = new AccountCourse();
                    accountCourse.setAccount(account);
                    accountCourse.setCourse(course);
                    accountCourse.setRegstrationDate(createdDate);

                    accountCourse.setPurchasePrice(course.getPrice());

                    accountCourseRepository.save(accountCourse);

                    responseDto.setStatus("200");
                    responseDto.setData(accountCourseMapper.mapEntityToDto(accountCourse));
                    responseDto.setMessage("Success");
                } else {
                    responseDto.setStatus("404");
                    responseDto.setData(null);
                    responseDto.setMessage("Account or Course not found");
                }
            } else {
                responseDto.setStatus("409");
                responseDto.setData(null);
                responseDto.setMessage("This course already assigned, cannot assign more!");
            }
        } else {
            responseDto.setStatus("409");
            responseDto.setData(null);
            responseDto.setMessage("This course already assigned, cannot assign more!");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<AccountCourseDto> update(Long accountCourseId, AccountCourseDto updatedAccountCourse) {
        Optional<AccountCourse> optionalAccountCourse = accountCourseRepository.findById(accountCourseId);

        BaseResponseDto<AccountCourseDto> responseDto = new BaseResponseDto<>();
        if (optionalAccountCourse.isPresent()) {
            AccountCourseDto existingAccountCourse = accountCourseMapper.mapEntityToDto(optionalAccountCourse.get());

            if(updatedAccountCourse.getRegstrationDate() != null && !updatedAccountCourse.getRegstrationDate().equals(existingAccountCourse.getRegstrationDate())) {
                existingAccountCourse.setRegstrationDate(existingAccountCourse.getRegstrationDate());
            }
            if(updatedAccountCourse.getPurchasePrice() != 0 && updatedAccountCourse.getPurchasePrice() != existingAccountCourse.getPurchasePrice()) {
                existingAccountCourse.setPurchasePrice(existingAccountCourse.getPurchasePrice());
            }

            AccountCourse savedAccountCourse = accountCourseRepository.save(accountCourseMapper.mapDtoToEntity(existingAccountCourse));

            responseDto.setStatus("200");
            responseDto.setData(accountCourseMapper.mapEntityToDto(savedAccountCourse));
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setData(null);
            responseDto.setMessage("Not found");
        }
        return responseDto;
    }

    @Override
    public BaseResponseDto<Void> delete(Long accountCourseId) {
        AccountCourse accountCourseToDelete = accountCourseRepository.findById(accountCourseId).orElse(null);
        accountCourseRepository.delete(accountCourseToDelete);

        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();
        responseDto.setStatus("200");
        responseDto.setMessage("Success");
        return responseDto;
    }

    @Override
    @Transactional
    public BaseResponseDto<Void> deleteAccountCourseByUser(Long accountId, Long courseId) {
        BaseResponseDto<Void> responseDto = new BaseResponseDto<>();

        AccountCourseDto accountCourseToDelete = accountCourseRepository.findAccountCourseByAccount_AccountIdAndCourse_CourseId(accountId,courseId);
        if(accountCourseToDelete != null) {
            delete(accountCourseToDelete.getAccountCourseId());

            responseDto.setStatus("200");
            responseDto.setMessage("Success");
        } else {
            responseDto.setStatus("404");
            responseDto.setMessage("This account not assign this course yet");
        }

        return responseDto;
    }
}
