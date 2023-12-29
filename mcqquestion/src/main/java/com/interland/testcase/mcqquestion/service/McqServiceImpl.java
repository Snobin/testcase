package com.interland.testcase.mcqquestion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.interland.testcase.mcqquestion.dto.Dto;
import com.interland.testcase.mcqquestion.dto.ServiceResponse;
import com.interland.testcase.mcqquestion.entity.DescriptiveEntity;
import com.interland.testcase.mcqquestion.entity.FileEntity;
import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;
import com.interland.testcase.mcqquestion.exception.RecordCreateException;
import com.interland.testcase.mcqquestion.exception.RecordNotFoundException;
import com.interland.testcase.mcqquestion.repository.DescRepository;
import com.interland.testcase.mcqquestion.repository.FileRepository;
import com.interland.testcase.mcqquestion.repository.McqRepository;
import com.interland.testcase.mcqquestion.repository.specification.McqQuestionSpec;
import com.interland.testcase.mcqquestion.util.Constants;


@Service
public class McqServiceImpl implements McqService {
	
	@Autowired
	McqRepository mcqrep;
	@Autowired
	FileRepository filerep;
	@Autowired
	DescRepository descrep;
	@Autowired
	MessageSource messageSource;
	private String DAQ;
	
	
	private JSONArray countByStatus() {
		JSONArray array = new JSONArray();
		try {
			List<McqEntity> headerList = mcqrep.findAll();
			Map<String, Long> countByStatus = headerList.stream()
					.collect(Collectors.groupingBy(McqEntity::getStatus, Collectors.counting()));
			for (String status : countByStatus.keySet()) {
				JSONObject obj = new JSONObject();
				obj.put("name", status);
				obj.put("count", countByStatus.get(status));
				array.add(obj);
			}
		} catch (Exception e) {
		
		}
		return array;
	}
	
	

	@Override
	public JSONObject searchByLimit(String searchParam, int start, int pageSize) {
		JSONObject result = new JSONObject();
		try {
			Pageable pageable = PageRequest.of(start / pageSize, pageSize);
			Specification<McqEntity> spec = McqQuestionSpec.getMcqQuestionSpec(searchParam);
			Page<McqEntity> mcqentList = mcqrep.findAll(spec, pageable);
			JSONArray array = new JSONArray();
			JSONArray countByStatus = countByStatus();
			for (McqEntity mcqent : mcqentList) {
				JSONObject obj = new JSONObject();
				obj.put(Constants.QUESTIONNO, mcqent.getPrimarykey().getQuestionNo());
				obj.put(Constants.QUESTIONID, mcqent.getPrimarykey().getQuestionId());
				obj.put(Constants.QUESTION, mcqent.getQuestion());
				obj.put(Constants.STATUS, mcqent.getStatus());
				obj.put(Constants.OPTIONA, mcqent.getOptionA());
				obj.put(Constants.OPTIONB, mcqent.getOptionB());
				obj.put(Constants.OPTIONC, mcqent.getOptionC());
				obj.put(Constants.OPTIOND, mcqent.getOptionC());
				obj.put(Constants.ANSWERS, mcqent.getAnswers());
				obj.put(Constants.SCORE, mcqent.getScore());
			
				array.add(obj);
			}
			result.put(Constants.AA_DATA, array);
			result.put(Constants.TOTAL_DISPLAY_RECORD, mcqrep.findAll(spec).size());
			result.put(Constants.TOTAL_RECORD, mcqrep.findAll(spec).size());
			result.put(Constants.COUNT_BY_STATUS, countByStatus);
		} catch (Exception e) {
		
		}
		return result;
	
	}
	
	
	
	public Dto getById(String questionId, String questionNo) {
		
		McqEmbedded bed = new McqEmbedded();
		bed.setQuestionId(questionId);
		bed.setQuestionNo(questionNo);
		
		Optional<McqEntity>entity1=mcqrep.findById(bed); 
		
		McqEntity entity=entity1.get();
	
		
		Dto dto =new Dto();
		dto.setQuestionNo(entity.getPrimarykey().getQuestionNo());
		dto.setQuestionId(entity.getPrimarykey().getQuestionId());
		dto.setQuestion(entity.getQuestion());
		dto.setOptionA(entity.getOptionA());
		dto.setOptionB(entity.getOptionB());
		dto.setOptionC(entity.getOptionC());
	    dto.setOptionD(entity.getOptionD());
	    dto.setAnswers(entity.getAnswers());
	    dto.setScore(entity.getScore());
	    dto.setStatus(entity.getStatus());
	    return dto;
	}

	public ServiceResponse create(Dto dto) {
	    try {
	        if ("DAQ".equals(dto.getQuestionId())) {
	            DescriptiveEntity des = new DescriptiveEntity();
	            McqEmbedded obj = new McqEmbedded();
	            obj.setQuestionId(dto.getQuestionId());
	            obj.setQuestionNo(dto.getQuestionNo());

	            Optional<McqEntity> existingQuestion = mcqrep.findById(obj);

	            if (existingQuestion.isPresent()) {
	                throw new RecordCreateException("Question already present");
	            }

	            des.setPrimarykey(obj);
	            des.setAnswer(dto.getAnswers());
	            des.setQuestion(dto.getQuestion());
	            des.setScore(dto.getScore());
	            descrep.save(des);
	        }

	        return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS, null, null);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, null, null);
	    }
	}


	
    @Override
	public ServiceResponse updateQuestion(Dto dto1) {
		List<JSONObject> customMessage = new ArrayList();
		JSONObject msgDet = new JSONObject();

		McqEntity mcqentity = new McqEntity();
		McqEmbedded emb= new McqEmbedded();
		try {
			emb.setQuestionId(dto1.getQuestionId());
			emb.setQuestionNo(dto1.getQuestionNo());
			mcqentity.setPrimarykey(emb);
			
			Optional<McqEntity> question = mcqrep.findById(emb);
			if (!question.isPresent()) {
				throw new RecordNotFoundException("mcq.test.tst.VAL0003");
			}
			McqEntity newEntity = question.get();
			BeanUtils.copyProperties(dto1, newEntity);
			
			newEntity.setQuestion(dto1.getQuestion());
			newEntity.setOptionA(dto1.getOptionA());
			newEntity.setOptionB(dto1.getOptionB());
			newEntity.setOptionC(dto1.getOptionC());
			newEntity.setOptionD(dto1.getOptionD());
			newEntity.setAnswers(dto1.getAnswers());
			newEntity.setScore(dto1.getScore());
			newEntity.setStatus(dto1.getStatus());
			mcqrep.save(newEntity);
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("mcq.test.tst.VAL0004", null, LocaleContextHolder.getLocale()), null);
		
		} catch (Exception e) {
			return new ServiceResponse("mcq.test.tst.VAL0005",
					messageSource.getMessage("mcq.test.tst.VAL0005", null, LocaleContextHolder.getLocale()), null);
		}

	}
    
    
    
    @Override
	public ServiceResponse delete(String questionNo, String questionId) throws RecordNotFoundException {
		try {
			McqEmbedded emb2 = new McqEmbedded();
			
			emb2.setQuestionNo(questionNo);
			emb2.setQuestionId(questionId);
			Optional<McqEntity> findByKey = mcqrep.findById(emb2);
			if (!findByKey.isPresent()) {
				throw new RecordNotFoundException("mcq.test.tst.VAL0006");
			} else {
				McqEntity mcqen = findByKey.get();
				
				if (mcqen.getStatus().contentEquals(Constants.MESSAGE_STATUS.DELETED)) {
					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
							.getMessage("mcq.test.tst.VAL0007", null, LocaleContextHolder.getLocale()), null);
				}
				if (mcqen.getStatus().contentEquals(Constants.VERIFYDELETED)) {
					return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED, messageSource
							.getMessage("mcq.test.tst.VAL0008", null, LocaleContextHolder.getLocale()), null);
				}
				
				mcqen.setStatus(Constants.VERIFYDELETED);
				mcqrep.save(mcqen);
			}
			return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,
					messageSource.getMessage("mcq.test.tst.VAL0009", null, LocaleContextHolder.getLocale()), null);
		} catch (RecordNotFoundException e) {
			return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,
					messageSource.getMessage("mcq.test.tst.VAL0010", null, LocaleContextHolder.getLocale()), null);
		}
			
	}


	@Override
	public ServiceResponse verifyQuestion(Dto dto2) throws RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
 	
	}



		
	


