package fr.toutatice.portail.acrennes.rss.portlet.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.toutatice.portail.acrennes.rss.portlet.model.RssSettings;

/**
 * Admin Rss form validator.
 *
 * @author Frédéric Boudan
 * @see Validator
 */
@Component
public class AdminRssFormValidator implements Validator {

    /**
     * Constructor.
     */
    public AdminRssFormValidator() {
        super();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return RssSettings.class.isAssignableFrom(clazz);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {
    	RssSettings form = (RssSettings) target;
    	ValidationUtils.rejectIfEmpty(errors, "container", "NotEmpty");
    }
}
