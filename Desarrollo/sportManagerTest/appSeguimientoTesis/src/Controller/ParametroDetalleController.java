package Controller;

import Dao.ParametroDetalleDao;
import Entity.Consulta;
import Entity.ParametroDetalle;
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

@ManagedBean(name = "parametroDetalleController")
@SessionScoped
public class ParametroDetalleController implements Serializable {

    private ParametroDetalle current;
    private DataModel items = null;
    private ParametroDetalleDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ParametroDetalleController() {
    }

    public ParametroDetalle getSelected() {
        if (current == null) {
            current = new ParametroDetalle();
            selectedItemIndex = -1;
        }
        return current;
    }

    public ParametroDetalleDao getDao() {
        if (dao == null) {
            dao = new ParametroDetalleDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            //final Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getDao().count(); 
                }
                
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getDao().findAllCatalog());
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
        if (getItems().getRowIndex() != -1) {
            current = (ParametroDetalle) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (ParametroDetalle) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new ParametroDetalle();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Hibernate.initialize(current.getParametro());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
        	
        	/*if (getDao().verificaEstudiante(current)) {
                //JsfUtil.addErrorMessage("Tiene consultas asociadas, por lo que no se puede eliminar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning!", "El par�metro tiene registros asociados en tabla de Estudiantes."));
                return "";
            }*/
            
            //Calendar c1 = Calendar.getInstance();
            //c1.gety           
            
            Date fecha = new Date("01/01/2015");
        	current.setAudUsuCrea(1);
        	current.setAudUsuModi(1);
        	current.setAudFechaCrea(fecha);
        	current.setAudFechaModi(fecha);
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
            current = (ParametroDetalle) getItems().getRowData();
            Hibernate.initialize(current.getParametro());
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (ParametroDetalle) getItems().getRowData();
            Hibernate.initialize(current.getParametro());
        }

        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getParametro());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().update(current);
            Hibernate.initialize(current.getParametro());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (ParametroDetalle) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (ParametroDetalle) getItems().getRowData();
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
            /*if (getDao().ConsultaAsociado(current)) {
                JsfUtil.addErrorMessage("Tiene consultas asociadas, por lo que no se puede eliminar");
                return;
            }
            if (getDao().CitasAsociado(current)) {
                JsfUtil.addErrorMessage("Tiene citas asociadas, por lo que no se puede eliminar");
                return;
            }
            if (getDao().RecetaAsociado(current)) {
                JsfUtil.addErrorMessage("Tiene recetas asociadas, por lo que no se puede eliminar");
                return;
            }
			if (getDao().OdontogramaAsociado(current)) {
				JsfUtil.addErrorMessage("Tiene odontogramas asociadas,por lo que no se puede eliminar");
				return;
            }
			if (getDao().InformeAsociado(current)) {
				JsfUtil.addErrorMessage("Tiene informes asociados,por lo que no se puede eliminar");
				return;
            }*/
            getDao().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
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
            items = new ListDataModel(getDao().findAllCatalog());
            Integer i = 0;
            for (Object item : items) {
                //DateFormat dateFormat = null;
                //Date date = ((ParametroDetalle) item).getAudFechaCrea();
                //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //((ParametroDetalle) item).setDateForFilter(dateFormat.format(date));
                ((ParametroDetalle) item).setPosition(i);
                i++;
            }
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Seleccione un Cat�logo 2");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        List<ParametroDetalle> docentes = getDao().findAll();
        for (ParametroDetalle ParametroDetalle : docentes) {
            Hibernate.initialize(docentes);

        }
        return JsfUtil.getSelectItems(docentes, true, "Seleccione un Cat�logo 1");


    }

    /*public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUser(user), false, "Selecione un paciente");
    	return null;
    }*/

    public SelectItem[] getItemsAvailableSelectOneByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser("ESTTEM"); 
        //List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser();
        for (ParametroDetalle ParametroDetalle : parametrosDetalle) {
            Hibernate.initialize(ParametroDetalle);

        }
        return JsfUtil.getSelectItems(parametrosDetalle, true, "Seleccione Estado Empleado");
    }
    
    public SelectItem[] getItemsAvailableCatalogEstudent() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser("ESTDOC"); 
        //List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser(); 
        for (ParametroDetalle ParametroDetalle : parametrosDetalle) {
            Hibernate.initialize(ParametroDetalle);

        }
        return JsfUtil.getSelectItems(parametrosDetalle, true, "Seleccione Estado Estudiante");
    }

    public SelectItem[] getItemsAvailablesByState() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser("ESTDOC"); 
        //List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser(); 
        for (ParametroDetalle ParametroDetalle : parametrosDetalle) {
            Hibernate.initialize(ParametroDetalle);

        }
        return JsfUtil.getSelectItems(parametrosDetalle, true, "Seleccione Estado Docente");
    }

    public SelectItem[] getItemsAvailablesByFacultad() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser("FACULT"); 
        //List<ParametroDetalle> parametrosDetalle = getDao().findAllByUser(); 
        for (ParametroDetalle ParametroDetalle : parametrosDetalle) {
            Hibernate.initialize(ParametroDetalle);

        }
        return JsfUtil.getSelectItems(parametrosDetalle, true, "Seleccione Facultad");
    }
    

    @FacesConverter(forClass = ParametroDetalle.class)
    public static class ParametroDetalleConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametroDetalleController controller = (ParametroDetalleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametroDetalleController");
            return controller.getDao().find(getStringKey(value), false);
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(String string) {
            StringBuffer sb = new StringBuffer();
            sb.append(string);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ParametroDetalle) {
            	ParametroDetalle o = (ParametroDetalle) object;
                return getStringKey(o.getPadCodigo());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ParametroDetalle.class.getName());
            }
        }
    }

    public void validateCedula(FacesContext arg0, UIComponent arg1, Object arg2)
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
        /*if (getDao().findCedula1(cedula, id)) {
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser �nica");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }*/
    }

    public void validateEmail(FacesContext arg0, UIComponent arg1, Object arg2)
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
        /*if (getDao().findEmail1(email, id)) {
            FacesMessage errorMessages = new FacesMessage("El email debe ser único");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(errorMessages);
        }*/
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Par�metros:", font));

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
        ParametroDetalle paciente = (ParametroDetalle) getDao().find(id, true);
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
}
