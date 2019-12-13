package fr.toutatice.portail.acrennes.customizer.internationalization.service;

import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.internationalization.IInternationalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

/**
 * Internationalization customizer service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CustomizerServiceImpl
 */
@Service
public class InternationalizationCustomizerServiceImpl extends CustomizerServiceImpl {

    /**
     * Customizer name.
     */
    private static final String CUSTOMIZER_NAME = "toutatice.customizer.internationalization";


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;


    /**
     * Constructor.
     */
    public InternationalizationCustomizerServiceImpl() {
        super();
    }


    @Override
    protected String getCustomizerName() {
        return CUSTOMIZER_NAME;
    }


    @Override
    protected String getCustomizerId() {
        return IInternationalizationService.CUSTOMIZER_ID;
    }


    @Override
    public void customize(CustomizationContext customizationContext) {
        Map<String, Object> attributes = customizationContext.getAttributes();
        String key = (String) attributes.get(IInternationalizationService.CUSTOMIZER_ATTRIBUTE_KEY);
        Locale locale = (Locale) attributes.get(IInternationalizationService.CUSTOMIZER_ATTRIBUTE_LOCALE);

        // Message
        String message;
        try {
            message = this.applicationContext.getMessage(key, null, locale);
        } catch (NoSuchMessageException e) {
            message = null;
        }

        if (StringUtils.isNotEmpty(message)) {
            attributes.put(IInternationalizationService.CUSTOMIZER_ATTRIBUTE_RESULT, message);
        }
    }

}
