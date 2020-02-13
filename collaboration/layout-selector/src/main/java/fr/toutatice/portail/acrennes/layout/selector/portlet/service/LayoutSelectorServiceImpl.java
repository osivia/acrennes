package fr.toutatice.portail.acrennes.layout.selector.portlet.service;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.ui.layout.LayoutItem;
import org.osivia.portal.api.ui.layout.LayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.portlet.PortletException;
import java.lang.reflect.InvocationTargetException;
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
    public LayoutSelectorAdminForm getAdminForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Administration form
        LayoutSelectorAdminForm form = this.applicationContext.getBean(LayoutSelectorAdminForm.class);

        // Layout items
        List<LayoutItem> layoutItems;
        try {
            layoutItems = this.layoutService.getItems(portalControllerContext);
        } catch (PortalException e) {
            throw new PortletException(e);
        }

        // Form items
        List<LayoutSelectorAdminFormItem> formItems;
        if (CollectionUtils.isEmpty(layoutItems)) {
            formItems = null;
        } else {
            formItems = new ArrayList<>(layoutItems.size());
            for (LayoutItem layoutItem : layoutItems) {
                LayoutSelectorAdminFormItem formItem = this.applicationContext.getBean(LayoutSelectorAdminFormItem.class);
                try {
                    BeanUtils.copyProperties(formItem, layoutItem);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new PortletException(e);
                }
                formItems.add(formItem);
            }
        }
        form.setItems(formItems);

        return form;
    }


    @Override
    public void saveAdministration(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form) throws PortletException {
        // Layout items
        List<LayoutItem> layoutItems;
        if (CollectionUtils.isEmpty(form.getItems())) {
            layoutItems = null;
        } else {
            layoutItems = new ArrayList<>(form.getItems());
        }

        try {
            this.layoutService.setItems(portalControllerContext, layoutItems);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
    }

}
