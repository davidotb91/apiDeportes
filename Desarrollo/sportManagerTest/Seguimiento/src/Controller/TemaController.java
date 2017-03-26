package Controller;

import Dao.ParametroDetalleDao;
import Dao.TemaDao;
import Entity.Docente;
import Entity.ParametroDetalle;
import Entity.Tema;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "temaController")
@SessionScoped
public class TemaController implements Serializable {

    private Tema current;
    private DataModel items = null;
    private DataModel itemsReport = null;
    private TemaDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Docente user = null;
    

    public TemaController() {
    	 user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();

    }

    public Tema getSelected() {
        if (current == null) {
            current = new Tema();
            selectedItemIndex = -1;
        }
        return current;
    }

    private TemaDao getDao() {
        if (dao == null) {
            dao = new TemaDao();
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
                    return new ListDataModel(getDao().findAllByUser(user));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareListE() {
        recreateModel();
        return "ListE";
    }
    
    
    public String prepareView() {
    	
        if (getItems().getRowIndex() != -1) {
            current = (Tema) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Tema) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new Tema();
        
        //ParametroDetalle paramDetalle = new ParametroDetalle();
        ParametroDetalleController paramDetalleController = new ParametroDetalleController();
        ParametroDetalle paramDetalle = new ParametroDetalle();
        
        paramDetalle = getDao().findParametroDetalle("ING");
        if (paramDetalle != null)
        	current.setParametroDetalle(paramDetalle);
        
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            
            if  (current.getTemFechaPresentacion().after(current.getTemFechaMaximaAprobacion()))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia!", "La fecha inicial no puede ser mayor a la final"));
                return "";
            }
            
            if (user == null)
            	user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();            
            
            current.setDocente(user);            
        	current.setAudUsuCrea(user.getDocId());
        	current.setAudUsuModi(user.getDocId());
        	current.setAudFechaCrea(Util.MD5.getFechaActual());
        	current.setAudFechaModi(Util.MD5.getFechaActual());
        	current.setTemFechaAprobacion(current.getTemFechaMaximaAprobacion());        	
        	current.setTemNotaFinal(00.00);
            getDao().create(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured") + e.getMessage());
            return null;
        }
    }

    public String prepareEdit() {

        if (getItems().getRowIndex() != -1) {
            current = (Tema) getItems().getRowData();
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            selectedItemIndex = getItems().getRowIndex();
            System.out.println("prepareEdit a");
            System.out.println(current.getParametroDetalle().getPadCodigo());

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Tema) getItems().getRowData();
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            System.out.println("prepareEdit b");
            System.out.println(current.getParametroDetalle().getPadCodigo());
        }

        return "Edit";
    }

    public String update() {
        try {
        	
            if  (current.getTemFechaPresentacion().after(current.getTemFechaMaximaAprobacion()))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia!", "La fecha inicial no puede ser mayor a la final"));
                return "";            	
            }
        	
            if (user == null)
            	user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();                 
            
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            current.setDocente(user);
        	current.setAudUsuModi(user.getDocId());
        	current.setAudFechaModi(Util.MD5.getFechaActual());
        	current.setTemNotaFinal(00.00);
            
            getDao().update(current,user);
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Tema) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Tema) getItems().getRowData();
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
            JsfUtil.addSuccessMessage("Registro eliminado " + this.getClass().getName());
        } catch (Exception e) {
            FacesMessage errorMessages = new FacesMessage("Error al eliminar, el detalle es : " + e.getMessage());
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }

    private void updateCurrentItem() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
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
            //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().findAllByUser(user));
            Integer i = 0;
            for (Object item : items) {
                //DateFormat dateFormat = null;
                //Date date = ((Tema) item).getAudFechaCrea();
                //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //((Tema) item).setDateForFilter(dateFormat.format(date));
                ((Tema) item).setPosition(i);
                i++;
            }
        }
        return items;
    }
    
    public DataModel getItemsReport() {
        if (items == null) {
            //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            itemsReport = new ListDataModel(getDao().findAll(user));
            Integer i = 0;
            for (Object item : itemsReport) {
                //DateFormat dateFormat = null;
                //Date date = ((Tema) item).getAudFechaCrea();
                //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //((Tema) item).setDateForFilter(dateFormat.format(date));
                ((Tema) item).setPosition(i);
                i++;
            }
        }
        return itemsReport;
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

    /*public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione un Cat�logo");
    }/

    public SelectItem[] getItemsAvailableSelectOne() {
        List<Tema> docentes = getDao().findAll();
        for (Tema Tema : docentes) {
            Hibernate.initialize(docentes);

        }
        return JsfUtil.getSelectItems(docentes, true, "Selecione un Estado");


    }

    /*public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUser(user), false, "Selecione un paciente");
    	return null;
    }*/

    public SelectItem[] getItemsAvailableSelectOneByUser() {
    	if (user == null)
    		user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        List<Tema> temas = getDao().findAllByUser(user); 
        for (Tema Tema : temas) {
            Hibernate.initialize(Tema);

        }
        return JsfUtil.getSelectItems(temas, true, "Selecione un Tema");
    }


    
    @FacesConverter(forClass = Tema.class)
    public static class TemaConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TemaController controller = (TemaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "temaController");
            return controller.getDao().find(getKey(value), false);
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int i) {
            StringBuffer sb = new StringBuffer();
            sb.append(i);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Tema) {
            	Tema o = (Tema) object;
                return getStringKey(o.getTeId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Tema.class.getName());
            }
        }
        
    }

    /*public void validateCedula(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        String cedula = (String) arg2;
        String expreg = "^(?:\\+|-)?\\d+$";
        if (!cedula.matches(expreg)) {
            FacesMessage errorMessages = new FacesMessage("La cedula tiene un formato incorrecto");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);

        }
        if (getDao().findCedula1(cedula, id)) {
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser �nica");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }*/

    /*public void validateEmail(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        String email = (String) arg2;
        String expreg = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(expreg)) {
            FacesMessage errorMessages = new FacesMessage("El email tiene un formato incorrecto");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(errorMessages);
        }
        if (getDao().findEmail1(email, id)) {
            FacesMessage errorMessages = new FacesMessage("El email debe ser único");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(errorMessages);
        }
    }*/

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Maestr�as:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);
        PdfPTable pdfTable = new PdfPTable(5);
        float[] widths = new float[]{30f, 15f, 10f, 20f, 25f};
        pdfTable.setWidths(widths);


        pdf.add(pdfTable);

        //final Phrase phrase = new Phrase("Listado de los médicos");

        //pdf.add(phrase);
    }

    /*public void preProcessDetallePDF(Object document) throws IOException, DocumentException {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        Tema paciente = (Tema) getDao().find(id, true);
        final Document pdf = (Document) document;
        pdf.addTitle("Detalles del médico:");
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfDetalleClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Detalles del paciente:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);

        PdfPTable table = new PdfPTable(2);
        Font font1 = FontFactory.getFont("Times-Roma", 14, Font.BOLD);

        float[] widths = new float[]{30f, 70f};
        table.setWidths(widths);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        //table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.addCell(new Phrase("Nombre:", font1));
        table.addCell(new Phrase(paciente.getNombre() + " " + paciente.getApellidopaterno() + " " + paciente.getApellidomaterno()));
        table.addCell(new Phrase("Cédula:", font1));
        table.addCell(new Phrase(paciente.getCedula()));
        table.addCell(new Phrase("Profesión:", font1));
        table.addCell(new Phrase(paciente.getProfesion()));
        table.addCell(new Phrase("Lugar de trabajo:", font1));
        table.addCell(new Phrase(paciente.getLugartrabajo()));
        table.addCell(new Phrase("Estado civil:", font1));
        table.addCell(new Phrase(paciente.getEstadocivil()));
        table.addCell(new Phrase("Número de hijos:", font1));
        table.addCell(new Phrase(paciente.getNumerohijos()));
        table.addCell(new Phrase("Edad:", font1));
        table.addCell(new Phrase(paciente.getEdad()));

        table.addCell(new Phrase("Fecha de nacimento:", font1));
        table.addCell(paciente.getFechanacimiento().toString());
        table.addCell(new Phrase("Celular:", font1));
        table.addCell(new Phrase(paciente.getCelular()));
        table.addCell(new Phrase("Teléfono:", font1));
        table.addCell(new Phrase(paciente.getTelefono()));
        table.addCell(new Phrase("Email:", font1));
        table.addCell(new Phrase(paciente.getEmail()));
        table.addCell(new Phrase("Nacionalidad:", font1));
        table.addCell(new Phrase(paciente.getNacionalidad()));
        table.addCell(new Phrase("Provincia:", font1));
        table.addCell(new Phrase(paciente.getProvincia()));

        table.addCell(new Phrase("Dirección:", font1));
        table.addCell(new Phrase(paciente.getDireccion()));


        pdf.add(table);

        PdfPTable table2 = new PdfPTable(1);
        PdfPCell cell2 = new PdfPCell(new Paragraph("             "));

        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        table2.addCell(cell2);
        pdf.add(table2);



    }

    public String getDashBoardMessage() {
        Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        int count = getDao().countByUser(user);
        return count > 0 ? "Ver " + count + " paciente(s)" : "No hay paciente";
    }*/
    
    public String prepareApprove() {

        if (getItems().getRowIndex() != -1) {
            current = (Tema) getItems().getRowData();
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            selectedItemIndex = getItems().getRowIndex();
            System.out.println("prepareEdit a");
            System.out.println(current.getParametroDetalle().getPadCodigo());

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Tema) getItems().getRowData();
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            System.out.println("prepareEdit b");
            System.out.println(current.getParametroDetalle().getPadCodigo());
        }

        return "Approve";
    }
    
    public String updateApprove() {
        try {
        	
            if  (current.getTemFechaPresentacion().after(current.getTemFechaMaximaAprobacion()))
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia!", "La fecha inicial no puede ser mayor a la final"));
                return "";            	
            }
        	
            if (user == null)
            	user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();                 
            
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            current.setDocente(user);
        	current.setAudUsuModi(user.getDocId());
        	current.setAudFechaModi(Util.MD5.getFechaActual());
            
            getDao().update(current,user);
            Hibernate.initialize(current.getParametroDetalle());
            Hibernate.initialize(current.getEstudiante());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteUpdated"));
            recreateModel();
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }    
}
