package com.interland.testcase.mcqquestion.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.interland.testcase.mcqquestion.dto.Dto;
import com.interland.testcase.mcqquestion.dto.ServiceResponse;
import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;
import com.interland.testcase.mcqquestion.exception.RecordCreateException;
import com.interland.testcase.mcqquestion.exception.RecordNotFoundException;
import com.interland.testcase.mcqquestion.repository.McqRepository;
import com.interland.testcase.mcqquestion.util.Constants;


@Service
public class McqServiceImpl implements McqService {
	
	@Autowired
	McqRepository mcqrep;
	@Autowired
	MessageSource messageSource;
	
	
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

	

	

	public ServiceResponse create(Dto dto)  {
    	try {
		McqEntity mcqentity = new McqEntity();
		System.out.println(dto.toString());
		
		
		McqEmbedded obj=new McqEmbedded();
		obj.setQuestionId(dto.getQuestionId());
		obj.setQuestionNo(dto.getQuestionNo());
		Optional<McqEntity> existingQuestion = mcqrep.findById(obj);
		if(existingQuestion.isPresent())
		{
			throw new RecordCreateException("Question already present");
			
		}
		else
		{
			mcqentity.setPrimarykey(obj);
			mcqentity.setQuestion(dto.getQuestion());
			mcqentity.setOptionA(dto.getOptionA());
			mcqentity.setOptionB(dto.getOptionB());
			mcqentity.setOptionC(dto.getOptionC());
			mcqentity.setOptionD(dto.getOptionD());
			mcqentity.setAnswers(dto.getAnswers());
	    	mcqentity.setScore(dto.getScore());
	    	mcqentity.setStatus(dto.getStatus());
	    	
	    	mcqrep.save(mcqentity);
	    }
    	return new ServiceResponse(Constants.MESSAGE_STATUS.SUCCESS,null,null);

	} catch (Exception e) {
		e.printStackTrace();
		
		return new ServiceResponse(Constants.MESSAGE_STATUS.FAILED,null, null);
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
	


		
	


