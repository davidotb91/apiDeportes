package Controller;

import Dao.CabecerafacturaDao;
import Entity.Cabecerafactura;
import Util.JsfUtil;
import Util.PaginationHelper;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.Hibernate;

@ManagedBean(name = "cabecerafacturaController")
@SessionScoped
public class CabecerafacturaController implements Serializable {

    private Cabecerafactura current;
    private DataModel items = null;
    private CabecerafacturaDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CabecerafacturaController() {
    }

    public Cabecerafactura getSelected() {
        if (current == null) {
            current = new Cabecerafactura();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CabecerafacturaDao getDao() {
        if (dao == null) {
            dao = new CabecerafacturaDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(11) {
                @Override
                public int getItemsCount() {
                    return getDao().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getDao().findAll());
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Cabecerafactura) getItems().getRowData();
        selectedItemIndex = getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cabecerafactura();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            System.out.println("creo");
            
            Hibernate.initialize(current.getPaciente());
            getDao().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CabecerafacturaCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cabecerafactura) getItems().getRowData();
        Hibernate.initialize(current.getPaciente());
        selectedItemIndex = getItems().getRowIndex();

        System.out.println(current.getPaciente().getNombre());
        return "Edit";
    }

    public String update() {
        try {
            System.out.println("edito");
            Hibernate.initialize(current.getPaciente());
            getDao().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CabecerafacturaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cabecerafactura) getItems().getRowData();
        System.out.println(current);
        selectedItemIndex = getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getDao().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CabecerafacturaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getDao().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            /*if (pagination.getPageFirstItem() >= count) {
             pagination.previousPage();
             }*/
        }
        if (selectedItemIndex >= 0) {
            current = getDao().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = new ListDataModel(getDao().findAll());
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione una cabecera");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getDao().findAll(), true, "Selecione una cabecera");
    }

    @FacesConverter(forClass = Cabecerafactura.class)
    public static class CabecerafacturaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CabecerafacturaController controller = (CabecerafacturaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cabecerafacturaController");
            return controller.getDao().find(getKey(value), false);
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cabecerafactura) {
                Cabecerafactura o = (Cabecerafactura) object;
                return getStringKey(o.getCabecerafactura());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cabecerafactura.class.getName());
            }
        }
    }
}
