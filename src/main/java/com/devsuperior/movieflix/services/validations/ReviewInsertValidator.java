package com.devsuperior.movieflix.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.devsuperior.movieflix.entities.ReviewDTO;
import com.devsuperior.movieflix.resources.exceptions.FieldMessage;

public class ReviewInsertValidator implements ConstraintValidator<ReviewInsertValid, ReviewDTO> {

    @Override
    public void initialize(ReviewInsertValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(ReviewDTO value, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();
        
        if (value.getText().length() == 0 || value.getText().trim().length() == 0) {
            list.add(new FieldMessage("text", "Texto vazio"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

}
