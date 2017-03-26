package Controller;

import Dao.ParamDao;
import Entity.Param;
import Entity.Docente;
//import Entity.Paciente;
import Util.Context;
import Util.ControlSesion;
import Util.JsfUtil;
import Util.PaginationHelper;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.hibernate.Hibernate;

@ManagedBean(name = "paramController")
@SessionScoped
public class ParamController implements Serializable {

    private Param current;
    private DataModel items = null;
    private DataModel itemsAnio = null;
    private ParamDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String anio;
    
	public ParamController() {
    }

    public Param getSelected() {
        if (current == null) {
            current = new Param();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ParamDao getDao() {
        if (dao == null) {
            dao = new ParamDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            final Docente user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            pagination = new PaginationHelper(10) {
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

    public String getAnio() {
    	
    	itemsAnio = new ListDataModel(getDao().obtenerAnio());
        Integer i = 0;
        for (Object item : itemsAnio) {
            return ((Param) item).getParValor();
            //i++;
        }
        return "---";

	}

	public void setAnio(String anio) {
		this.anio = anio;
	}    
    
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        if (getItems().getRowIndex() != -1) {
            current = (Param) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Param) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new Param();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            //Hibernate.initialize(current.getMedico());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {

        if (getItems().getRowIndex() != -1) {
            current = (Param) getItems().getRowData();
            //Hibernate.initialize(current.getMedico());
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            //current = (Param) getItems().getRowData();
            //Hibernate.initialize(current.getMedico());
        }

        return "Edit";
    }

    public String update() {
        try {
            //Hibernate.initialize(current.getMedico());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().update(current);
            //Hibernate.initialize(current.getMedico());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Param) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Param) getItems().getRowData();
        }
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
            RuntimeException error = getDao().getErrorAbstract();
            if (error == null)
            	JsfUtil.addSuccessMessage("Registro eliminado " + this.getClass().getName());
            else
	            {
	                JsfUtil.addErrorMessage("Tiene registros asociados, por lo que no se puede eliminar.");
	                return;

	            } 
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia!", "La fecha inicial no puede ser mayor a la final"));
        }
    }

    private void updateCurrentItem() {
        Docente user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        int count = getDao().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getDao().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            Docente user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().findAll());
            Integer i = 0;
            /*for (Object item : items) {
                DateFormat dateFormat = null;
                Date date = ((Param) item).getFechanacimiento();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ((Param) item).setDateForFilter(dateFormat.format(date));
                ((Param) item).setPosition(i);
                i++;
            }*/
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione un paciente");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        List<Param> docentes = getDao().findAll();
        for (Param Param : docentes) {
            Hibernate.initialize(docentes);

        }
        return JsfUtil.getSelectItems(docentes, true, "Selecione un paciente");


    }

    public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Docente user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUser(user), false, "Selecione un paciente");
    	return null;
    }

    public SelectItem[] getItemsAvailableSelectOneByUser() {
        Docente user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<Param> docentes = getDao().findAll(); //ETA
        for (Param Param : docentes) {
            Hibernate.initialize(Param);

        }
        return JsfUtil.getSelectItems(docentes, true, "Selecione un paciente");
    }

    @FacesConverter(forClass = Param.class)
    public static class ParamControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParamController controller = (ParamController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "docenteController");
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
            if (object instanceof Param) {
            	Param o = (Param) object;
                return getStringKey(o.getParId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Param.class.getName());
            }
        }
    }

   
    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Par·metros:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);
        PdfPTable pdfTable = new PdfPTable(5);
        float[] widths = new float[]{30f, 15f, 10f, 20f, 25f};
        pdfTable.setWidths(widths);


        pdf.add(pdfTable);

        //final Phrase phrase = new Phrase("Listado de los m√©dicos");

        //pdf.add(phrase);
    }

  
}
