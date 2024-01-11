package com.interland.studentservice.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.interland.studentservice.dto.ServiceResponse;
import com.interland.studentservice.dto.StudentDto;
import com.interland.studentservice.entity.Student;
import com.interland.studentservice.exceptions.RecordCreateException;
import com.interland.studentservice.exceptions.RecordNotFoundException;
import com.interland.studentservice.repository.StudentRepository;
import com.interland.studentservice.repository.specification.StudentSpec;
import com.interland.studentservice.utils.Constants;

import jakarta.validation.Valid;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	StudentRepository repository;
	
	@Autowired
	MessageSource messageSource;
	
	private static Logger logger = LogManager.getLogger(StudentServiceImpl.class);

	@Override
	public Student getStudentById(String id) throws RecordNotFoundException {
		Optional<Student> student = repository.findById(id);
		if (student.isPresent()) {
			return student.get();
		} else {
			throw new RecordNotFoundException("user.details.psh.VAL0001");
		}
	}

	@Override
	public ServiceResponse createStudent(@Valid StudentDto dto) throws RecordCreateException {
		Student student = new Student();
		student.setId(dto.getId());
		student.setFirstName(dto.getFirstName());
		student.setLastName(dto.getLastName());
		student.setDob(dto.getDob());
		student.setCollege(dto.getCollege());
		student.setDeptarment(dto.getDeptarment());
		student.setPlace(dto.getPlace());
		student.setQualification(dto.getQualification());
		student.setEmail(dto.getEmail());
		student.setPhoneNo(dto.getPhoneNo());
		try {
			Optional<Student> user = repository.findById(dto.getId());
			if (user.isPresent()) {
				throw new RecordCreateException("user.details.psh.VAL0002");
			}
			student.setResult(Constants.PENDING);
			student.setScore(0);
			student.setStatus(Constants.MESSAGE_STATUS.PROCESSED);
			repository.save(student);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, messageSource.getMessage("user.details.psh.VAL0003", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordCreateException e) {
			throw e;
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
			return new ServiceResponse("user.details.psh.VAL0004", messageSource.getMessage("user.details.psh.VAL0004", null, LocaleContextHolder.getLocale()), null);
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray countByStatus(Specification<Student> spec) {
		JSONArray array = new JSONArray();
		try {
			List<Student> headerList = repository.findAll(spec);
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(Student::getStatus, Collectors.counting()));
			for (String status : countByStatus.keySet()) {
				JSONObject obj = new JSONObject();
				obj.put("name", status);
				obj.put("count", countByStatus.get(status));
				array.add(obj);
			}
		} catch (Exception e) {
			logger.error("Error:" + e.getMessage(), e);
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject search(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<Student> spec = StudentSpec.getStudentSpec(searchParam);
			Page<Student> studentsList = repository.findAll(spec, pageable);
			JSONArray array = new JSONArray();
			JSONArray countByStatus = countByStatus(spec);
			for (Student students : studentsList) {
				JSONObject obj = new JSONObject();
				obj.put("id", students.getId());
				obj.put("firstName", students.getFirstName());
				obj.put("lastName", students.getLastName());
				obj.put("dob", students.getDob());
				obj.put("college", students.getCollege());
				obj.put("department", students.getDeptarment());
				obj.put("place", students.getPlace());
				obj.put("qualification", students.getQualification());
				obj.put("result", students.getResult());
				obj.put("email", students.getEmail());
				obj.put("phoneNo", students.getPhoneNo());
				obj.put("score", students.getScore());
				obj.put("status", students.getStatus());
				array.add(obj);
			}
			result.put("aaData", array);
			result.put("iTotalDisplayRecords", repository.findAll(spec).size());
			result.put("iTotalRecords", repository.findAll().size());
			result.put("countByStatus", countByStatus);
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage(), e);
			result.put(Constants.AA_DATA, new JSONArray());
			result.put(Constants.TOTAL_DISPLAY_RECORD, 0);
			result.put(Constants.TOTAL_RECORD, 0);
			result.put("countByStatus", 0);
		}
		return result;
	}
}
