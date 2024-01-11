package com.interland.studentservice.repository.specification;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.interland.studentservice.entity.Student;
import com.interland.studentservice.utils.Constants;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class StudentSpec {
	public static Specification<Student> getStudentSpec(String searchParam) {
		return new Specification<Student>() {
			@Override
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate finalPredicate = null;
				JSONParser parser = new JSONParser();
				JSONObject searchObject;
				try {
					searchObject = (JSONObject) parser.parse(searchParam);
					String id = (String) searchObject.get(Constants.ID);
					String firstName = (String) searchObject.get(Constants.FIRST_NAME);
					String lastName = (String) searchObject.get(Constants.LAST_NAME);
					String college = (String) searchObject.get(Constants.COLLEGE);
					String department = (String) searchObject.get(Constants.DEPARTMENT);
					String place = (String) searchObject.get(Constants.PLACE);
					String qualification = (String) searchObject.get(Constants.QUALIFICATION);
					String result = (String) searchObject.get(Constants.RESULT);
					String email = (String) searchObject.get(Constants.EMAIL);
					String phoneNo = (String) searchObject.get(Constants.PHONENO);
					String status = (String) searchObject.get(Constants.STATUS);
		            if(!StringUtils.isEmpty(status)) {
		            	Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.STATUS), status);
		            	finalPredicate = criteriaBuilder.and(statusPredicate);
		            }
		            
		            if(!StringUtils.isEmpty(id)) {
		            	Predicate idPredicate = criteriaBuilder.equal(root.get(Constants.ID),id);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, idPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(idPredicate);
		            	}
		            	
		            }
		            
		            if(!StringUtils.isEmpty(firstName)) {
		            	Predicate firstNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.FIRST_NAME)),"%"+firstName.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, firstNamePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(firstNamePredicate);
		            	}
		            	
		            }

		            if(!StringUtils.isEmpty(lastName)) {
		            	Predicate lastNamePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.LAST_NAME)),"%"+lastName.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, lastNamePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(lastNamePredicate);
		            	}
		            	
		            }
		            
		            if(!StringUtils.isEmpty(college)) {
		            	Predicate collegePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.COLLEGE)),"%"+college.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, collegePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(collegePredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(department)) {
		            	Predicate departmentPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.DEPARTMENT)),"%"+department.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, departmentPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(departmentPredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(place)) {
		            	Predicate placePredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.PLACE)),"%"+place.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, placePredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(placePredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(qualification)) {
		            	Predicate qualificationPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.QUALIFICATION)),"%"+qualification.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, qualificationPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(qualificationPredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(result)) {
		            	Predicate resultPredicate = criteriaBuilder.equal(root.get(Constants.RESULT),result);
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, resultPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(resultPredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(email)) {
		            	Predicate emailPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.EMAIL)),"%"+email.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, emailPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(emailPredicate);
		            	}
		            }
		            
		            if(!StringUtils.isEmpty(phoneNo)) {
		            	Predicate phoneNoPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.PHONENO)),"%"+phoneNo.toUpperCase()+"%");
		            	if(finalPredicate!=null) {
		            		finalPredicate = criteriaBuilder.and(finalPredicate, phoneNoPredicate);
		            	}else {
		            		finalPredicate = criteriaBuilder.and(phoneNoPredicate);
		            	}
		            }
		            
//		            Order proTimeOrder = criteriaBuilder.desc(root.get("modifiedTime"));
//		            query.orderBy(proTimeOrder);
		            
				} catch (ParseException e) {
					e.printStackTrace();
				}
	            
				return finalPredicate;
			}
		};
	}
}
