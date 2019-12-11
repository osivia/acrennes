package fr.toutatice.portail.acrennes.customizer.commons.service;

import org.osivia.portal.api.customization.CustomizationModuleMetadatas;
import org.osivia.portal.api.customization.ICustomizationModule;
import org.osivia.portal.api.customization.ICustomizationModulesRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.portlet.PortletContext;
import java.util.Collections;

/**
 * Customizer service implementation.
 *
 * @author Cédric Krommenhoek
 * @see CustomizerService
 */
public abstract class CustomizerServiceImpl implements CustomizerService {

    /**
     * Portlet context.
     */
    @Autowired
    private PortletContext portletContext;


    /**
     * Customization metadatas.
     */
    private CustomizationModuleMetadatas metadatas;


    /**
     * Constructor.
     */
    public CustomizerServiceImpl() {
        super();
    }


    @Override
    public void register() {
        // Customization repository
        ICustomizationModulesRepository repository = this.getCustomizationRepository();
        // Customization metadatas
        CustomizationModuleMetadatas metadatas = this.getMetadatas();

        repository.register(metadatas);
    }


    @Override
    public void unregister() {
        // Customization repository
        ICustomizationModulesRepository repository = this.getCustomizationRepository();
        // Customization metadatas
        CustomizationModuleMetadatas metadatas = this.getMetadatas();

        repository.unregister(metadatas);
    }


    /**
     * Get customization repository.
     *
     * @return customization repository
     */
    protected ICustomizationModulesRepository getCustomizationRepository() {
        return (ICustomizationModulesRepository) this.portletContext.getAttribute(ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY);
    }


    /**
     * Get customization metadatas.
     *
     * @return customization metadatas
     */
    protected CustomizationModuleMetadatas getMetadatas() {
        if (this.metadatas == null) {
            this.metadatas = new CustomizationModuleMetadatas();
            this.metadatas.setName(this.getCustomizerName());
            this.metadatas.setModule(this);
            this.metadatas.setCustomizationIDs(Collections.singletonList(this.getCustomizerId()));
        }

        return this.metadatas;
    }


    /**
     * Get customizer name.
     *
     * @return name
     */
    protected abstract String getCustomizerName();


    /**
     * Get customizer identifier.
     *
     * @return identifier
     */
    protected abstract String getCustomizerId();

}
