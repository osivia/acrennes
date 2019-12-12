package fr.toutatice.portail.acrennes.customizer.commons.service;

import org.osivia.portal.api.customization.CustomizationContext;
import org.osivia.portal.api.customization.ICustomizationModule;

/**
 * Customizer service interface.
 *
 * @author Cédric Krommenhoek
 * @see ICustomizationModule
 */
public interface CustomizerService extends ICustomizationModule {

    /**
     * Customization modules repository attribute name.
     */
    String ATTRIBUTE_CUSTOMIZATION_MODULES_REPOSITORY = "CustomizationModulesRepository";


    /**
     * Register customization metadatas.
     */
    void register();


    /**
     * Unregister customization metadatas.
     */
    void unregister();

}
