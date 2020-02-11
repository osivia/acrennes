package fr.toutatice.portail.acrennes.layout.selector.portlet.service;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import org.apache.commons.collections.CollectionUtils;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.ui.layout.LayoutItem;
import org.osivia.portal.api.ui.layout.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.portlet.PortletException;
import java.util.ArrayList;
import java.util.List;

/**
 * Layout selector portlet service implementation.
 *
 * @author Cédric Krommenhoek
 * @see LayoutSelectorService
 */
@Service
public class LayoutSelectorServiceImpl implements LayoutSelectorService {

    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Layout service.
     */
    @Autowired
    private LayoutService layoutService;


    /**
     * Constructor.
     */
    public LayoutSelectorServiceImpl() {
        super();
    }


    @Override
    public LayoutSelectorForm getForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Form
        LayoutSelectorForm form = this.applicationContext.getBean(LayoutSelectorForm.class);

        // Layout items
        List<LayoutItem> items;
        try {
            items = this.layoutService.getItems(portalControllerContext);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
        form.setItems(items);

        return form;
    }


    @Override
    public void select(PortalControllerContext portalControllerContext, LayoutSelectorForm form, String id) throws PortletException {
        try {
            this.layoutService.selectItem(portalControllerContext, id);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
    }


    @Override
    public void save(PortalControllerContext portalControllerContext) throws PortletException {
        List<LayoutItem> items;
        try {
            items = this.layoutService.getItems(portalControllerContext);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
        if (CollectionUtils.isEmpty(items)) {
            items = new ArrayList<>(1);
        }

        LayoutItem item = this.layoutService.createItem(portalControllerContext, "cua-client");
        item.setLabel("Client CUA");
        item.setIcon("glyphicons glyphicons-basic-mouse");
        items.add(item);

        try {
            this.layoutService.setItems(portalControllerContext, items);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
    }

}
