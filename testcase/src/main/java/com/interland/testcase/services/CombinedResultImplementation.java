package com.interland.testcase.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interland.testcase.entity.CombinedResult;
import com.interland.testcase.repository.CodingResultRepository;
import com.interland.testcase.repository.Combinedresult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class CombinedResultImplementation implements CombinedResultService{
	
	
	@Autowired
	private CodingResultRepository codingResultRepository;
	
	@Autowired
	private Combinedresult combinedresultRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(CombinedResultImplementation.class);

	public void getResult() {
	    try {
	        List<Object[]> codeResultList = codingResultRepository.codeResult();

	        for (Object[] singleResult : codeResultList) {
	            String user = (String) singleResult[0];
	            Double codingPercentage = (Double) singleResult[1];

	            // Check if user is present in the database
	            Optional<CombinedResult> existingResult = combinedresultRepo.findById(user);

	            CombinedResult combinedResult;
	            if (existingResult.isPresent()) {
	                // If user is present, update the existing record
	                combinedResult = existingResult.get();
	            } else {
	                // If user is not present, create a new record
	                combinedResult = new CombinedResult();
	                combinedResult.setUser(user);
	            }

	            combinedResult.setCodingPercentage(codingPercentage);
	            combinedResult.setStatus("PROCESSED");

	            combinedresultRepo.save(combinedResult);
	        }
	    } catch (Exception e) {
	        // Log the exception
	        logger.error("Error occurred while processing results", e);
	        // Optionally, you may throw a custom exception or take appropriate action based on your use case.
	    }
	}

}
