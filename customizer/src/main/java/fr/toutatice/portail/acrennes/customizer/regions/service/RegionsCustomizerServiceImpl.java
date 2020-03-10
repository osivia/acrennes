package fr.toutatice.portail.acrennes.customizer.regions.service;

import fr.toutatice.portail.acrennes.customizer.commons.service.CustomizerServiceImpl;
import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.theming.IRenderedRegions;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Regions customizer service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CustomizerServiceImpl
 */
@Service
public class RegionsCustomizerServiceImpl extends CustomizerServiceImpl {

    /**
     * Customizer name.
     */
    private static final String CUSTOMIZER_NAME = "toutatice.customizer.regions";


    /**
     * Constructor.
     */
    public RegionsCustomizerServiceImpl() {
        super();
    }


    @Override
    protected String getCustomizerName() {
        return CUSTOMIZER_NAME;
    }


    @Override
    protected String getCustomizerId() {
        return IRenderedRegions.CUSTOMIZER_ID;
    }


    @Override
    public void customize(CustomizationContext customizationContext) {
        Map<String, Object> attributes = customizationContext.getAttributes();
        IRenderedRegions renderedRegion = (IRenderedRegions) attributes.get(IRenderedRegions.CUSTOMIZER_ATTRIBUTE_RENDERED_REGIONS);

        // Toolbar
        renderedRegion.customizeRenderedRegion("toolbar", "/regions/toolbar.jsp");
        // Search
        renderedRegion.removeRenderedRegion("search");
        // Tabs
        renderedRegion.removeRenderedRegion("tabs");
        // Footer
        renderedRegion.removeRenderedRegion("footer");
    }

}
