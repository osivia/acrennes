package fr.toutatice.portail.acrennes.layout.selector.portlet.model.validation;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Layout selector administration form item Validator
 * @author Cédric Krommenhoek
 * @see Validator
 */
@Component
public class LayoutSelectorAdminFormItemValidator implements Validator {

    /**
     * Constructor.
     */
    public LayoutSelectorAdminFormItemValidator() {
        super();
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return LayoutSelectorAdminFormItem.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "label", "empty");
    }
}
