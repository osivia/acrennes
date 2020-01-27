package fr.toutatice.portail.acrennes.cua.client.portlet.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * CUA client form java-bean.
 *
 * @author Cédric Krommenhoek
 * @see AbstractCuaClientForm
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CuaClientSettingsForm extends AbstractCuaClientForm {

    /**
     * Constructor.
     */
    public CuaClientSettingsForm() {
        super();
    }

}
