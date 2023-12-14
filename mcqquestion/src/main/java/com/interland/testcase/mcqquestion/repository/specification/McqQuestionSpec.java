package com.interland.testcase.mcqquestion.repository.specification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import com.interland.testcase.mcqquestion.entity.McqEmbedded;
import com.interland.testcase.mcqquestion.entity.McqEntity;
import com.interland.testcase.mcqquestion.util.Constants;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class McqQuestionSpec {
    public static Specification<McqEntity> getMcqQuestionSpec(String searchParam) {
        return new Specification<McqEntity>() {
            @Override
            public Predicate toPredicate(Root<McqEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate finalPredicate = null;
                JSONParser parser = new JSONParser();
                McqEmbedded ob;

                JSONObject searchObject;
                System.out.println("haii");

                try {
                    searchObject = (JSONObject) parser.parse(searchParam);
                    String status = (String) searchObject.get(Constants.STATUS);
                    String questionNo = (String) searchObject.get(Constants.QUESTIONNO);
                    String questionId = (String) searchObject.get(Constants.QUESTIONID);

                    if (!StringUtils.isEmpty(status)) {
                        Predicate statusPredicate = criteriaBuilder.equal(root.get(Constants.STATUS), status);
                        finalPredicate = criteriaBuilder.and(statusPredicate);
                    }

                    if (!StringUtils.isEmpty(questionNo)) {
                        Predicate questionNoPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.QUESTIONNO)), "%" + questionNo.toUpperCase() + "%");
                        if (finalPredicate != null) {
                            finalPredicate = criteriaBuilder.and(finalPredicate, questionNoPredicate);
                        } else {
                            finalPredicate = questionNoPredicate;
                        }
                    }

                    if (!StringUtils.isEmpty(questionId)) {
                        Predicate questionIdPredicate = criteriaBuilder.like(criteriaBuilder.upper(root.get(Constants.QUESTIONID)), "%" + questionId.toUpperCase() + "%");
                        if (finalPredicate != null) {
                            finalPredicate = criteriaBuilder.and(finalPredicate, questionIdPredicate);
                        } else {
                            finalPredicate = questionIdPredicate;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return finalPredicate;
            }
        };
    }
}
		


