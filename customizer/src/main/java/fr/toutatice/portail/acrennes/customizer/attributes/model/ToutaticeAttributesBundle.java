package fr.toutatice.portail.acrennes.customizer.attributes.model;

import fr.toutatice.portail.acrennes.customizer.attributes.service.AttributesCustomizerService;
import org.jboss.portal.core.controller.ControllerException;
import org.jboss.portal.core.model.portal.command.render.RenderPageCommand;
import org.jboss.portal.core.theme.PageRendition;
import org.osivia.portal.api.theming.IAttributesBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * Toutatice attributes bundle.
 *
 * @author Cédric Krommenhoek
 * @see IAttributesBundle
 */
@Component
public class ToutaticeAttributesBundle implements IAttributesBundle {

    /**
     * Customizer service.
     */
    @Autowired
    private AttributesCustomizerService service;


    /**
     * Constructor.
     */
    public ToutaticeAttributesBundle() {
        super();
    }


    @Override
    public Set<String> getAttributeNames() {
        return this.service.getAttributeNames();
    }


    @Override
    public void fill(RenderPageCommand renderPageCommand, PageRendition pageRendition, Map<String, Object> attributes) throws ControllerException {
        this.service.fill(renderPageCommand, pageRendition, attributes);
    }

}
