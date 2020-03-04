package fr.toutatice.portail.acrennes.layout.selector.portlet.service;

import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorAdminFormItem;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.LayoutSelectorForm;
import fr.toutatice.portail.acrennes.layout.selector.portlet.model.comparator.LayoutSelectorAdminFormItemComparator;
import fr.toutatice.portail.acrennes.layout.selector.portlet.repository.LayoutSelectorRepository;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.osivia.portal.api.PortalException;
import org.osivia.portal.api.context.PortalControllerContext;
import org.osivia.portal.api.directory.v2.model.Group;
import org.osivia.portal.api.ui.layout.LayoutItem;
import org.osivia.portal.api.ui.layout.LayoutItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.portlet.PortletException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
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
     * Select2 results page size.
     */
    private final static int SELECT2_RESULTS_PAGE_SIZE = 10;


    /**
     * Application context.
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Portlet repository.
     */
    @Autowired
    private LayoutSelectorRepository repository;

    /**
     * Layout items comparator.
     */
    @Autowired
    private LayoutSelectorAdminFormItemComparator itemsComparator;

    /**
     * Layout items service.
     */
    @Autowired
    private LayoutItemsService layoutItemsService;


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
            items = this.layoutItemsService.getItems(portalControllerContext);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
        form.setItems(items);

        // Active layout item identifier
        String activeId;
        try {
            LayoutItem currentItem = this.layoutItemsService.getCurrentItem(portalControllerContext);
            if (currentItem == null) {
                activeId = null;
            } else {
                activeId = currentItem.getId();
            }
        } catch (PortalException e) {
            throw new PortletException(e);
        }
        form.setActiveId(activeId);

        return form;
    }


    @Override
    public void select(PortalControllerContext portalControllerContext, LayoutSelectorForm form, String id) throws PortletException {
        try {
            this.layoutItemsService.selectItem(portalControllerContext, id);
        } catch (PortalException e) {
            throw new PortletException(e);
        }

        // Update model
        form.setActiveId(id);
    }


    @Override
    public LayoutSelectorAdminForm getAdminForm(PortalControllerContext portalControllerContext) throws PortletException {
        // Administration form
        LayoutSelectorAdminForm form = this.applicationContext.getBean(LayoutSelectorAdminForm.class);

        if (!form.isLoaded()) {
            // Layout items
            List<LayoutItem> layoutItems;
            try {
                layoutItems = this.layoutItemsService.getItems(portalControllerContext);
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

            // Loaded indicator
            form.setLoaded(true);
        }

        return form;
    }


    @Override
    public void reorder(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form) {
        // Layout items
        List<LayoutSelectorAdminFormItem> items = form.getItems();

        if (CollectionUtils.isNotEmpty(items)) {
            items.sort(this.itemsComparator);
        }
    }


    @Override
    public void remove(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm form, String id) {
        // Layout items
        List<LayoutSelectorAdminFormItem> items = form.getItems();

        if (CollectionUtils.isNotEmpty(items)) {
            boolean removed = false;
            Iterator<LayoutSelectorAdminFormItem> iterator = items.iterator();
            while (!removed && iterator.hasNext()) {
                LayoutSelectorAdminFormItem item = iterator.next();
                if (StringUtils.equals(id, item.getId())) {
                    iterator.remove();
                    removed = true;
                }
            }
        }
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
            this.layoutItemsService.setItems(portalControllerContext, layoutItems);
        } catch (PortalException e) {
            throw new PortletException(e);
        }
    }


    @Override
    public LayoutSelectorAdminFormItem getAddedItemForm(PortalControllerContext portalControllerContext) {
        return this.applicationContext.getBean(LayoutSelectorAdminFormItem.class);
    }


    @Override
    public void addItem(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm adminForm, LayoutSelectorAdminFormItem itemForm) {
        // Layout items
        List<LayoutSelectorAdminFormItem> items = adminForm.getItems();

        if (CollectionUtils.isEmpty(items)) {
            items = new ArrayList<>(1);
            adminForm.setItems(items);
        }

        items.add(itemForm);
    }


    @Override
    public LayoutSelectorAdminFormItem getEditedItemForm(PortalControllerContext portalControllerContext, String id) throws PortletException {
        // Administration form
        LayoutSelectorAdminForm adminForm = this.getAdminForm(portalControllerContext);
        // Layout items
        List<LayoutSelectorAdminFormItem> items = adminForm.getItems();

        // Edited layout item form
        LayoutSelectorAdminFormItem itemForm = null;
        if (CollectionUtils.isNotEmpty(items)) {
            Iterator<LayoutSelectorAdminFormItem> iterator = items.iterator();
            while ((itemForm == null) && iterator.hasNext()) {
                LayoutSelectorAdminFormItem item = iterator.next();
                if (StringUtils.equals(id, item.getId())) {
                    itemForm = item.clone();
                }
            }
        }

        return itemForm;
    }


    @Override
    public void editItem(PortalControllerContext portalControllerContext, LayoutSelectorAdminForm adminForm, LayoutSelectorAdminFormItem itemForm) {
        // Layout items
        List<LayoutSelectorAdminFormItem> items = adminForm.getItems();

        if (CollectionUtils.isNotEmpty(items)) {
            int index = items.indexOf(itemForm);
            if (index >= 0) {
                items.remove(index);
                items.add(index, itemForm);
            }
        }
    }


    @Override
    public JSONObject loadProfiles(PortalControllerContext portalControllerContext, String filter, int page) throws PortletException {
        // Groups
        List<Group> groups = this.repository.searchGroups(portalControllerContext, filter);

        // JSON objects
        List<JSONObject> objects = new ArrayList<>();
        for (Group group : groups) {
            // Search result
            JSONObject object = new JSONObject();
            object.put("id", group.getCn());
            object.put("text", group.getCn());
            objects.add(object);
        }

        // Results JSON object
        JSONObject results = new JSONObject();

        // Items JSON array
        JSONArray items = new JSONArray();

        // Paginated results
        int begin = (page - 1) * SELECT2_RESULTS_PAGE_SIZE;
        int end = Math.min(objects.size(), begin + SELECT2_RESULTS_PAGE_SIZE);
        for (int i = begin; i < end; i++) {
            JSONObject object = objects.get(i);
            items.add(object);
        }

        // Pagination informations
        results.put("page", page);
        results.put("pageSize", SELECT2_RESULTS_PAGE_SIZE);
        results.put("items", items);
        results.put("total", objects.size());

        return results;
    }

}
