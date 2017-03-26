package Controller;

import Dao.LugarGeograficoDao;
import Entity.LugarGeografico;
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

@ManagedBean(name = "lugarGeograficoController")
//@SessionScoped
public class LugarGeograficoController implements Serializable {

    private LugarGeografico current;
    private DataModel items = null;
    private LugarGeograficoDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public LugarGeograficoController() {
    }

    public LugarGeografico getSelected() {
        if (current == null) {
            current = new LugarGeografico();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LugarGeograficoDao getDao() {
        if (dao == null) {
            dao = new LugarGeograficoDao();
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
                    return new ListDataModel(getDao().findAllByUser());
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
            current = (LugarGeografico) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (LugarGeografico) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new LugarGeografico();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Hibernate.initialize(current.getLugarGeograficop());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
        	
        	/*if (getDao().verificaEstudiante(current)) {
                //JsfUtil.addErrorMessage("Tiene consultas asociadas, por lo que no se puede eliminar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Warning!", "El parámetro tiene registros asociados en tabla de Estudiantes."));
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
            current = (LugarGeografico) getItems().getRowData();           
            Hibernate.initialize(current.getLugarGeograficop());
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (LugarGeografico) getItems().getRowData();
            Hibernate.initialize(current.getLugarGeograficop());
        }

        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getLugarGeograficop());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            Date fecha = new Date("01/01/2015");
        	current.setAudUsuCrea(1);
        	current.setAudUsuModi(1);
        	current.setAudFechaCrea(fecha);
        	current.setAudFechaModi(fecha);
            
            getDao().update(current);            
            
            Hibernate.initialize(current.getLugarGeograficop());
            JsfUtil.addSuccessMessage("Lugar G. actualizado");
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (LugarGeografico) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (LugarGeografico) getItems().getRowData();
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
            items = new ListDataModel(getDao().findAllByUser());
            Integer i = 0;
            for (Object item : items) {
                //DateFormat dateFormat = null;
                //Date date = ((LugarGeografico) item).getAudFechaCrea();
                //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //((LugarGeografico) item).setDateForFilter(dateFormat.format(date));
                ((LugarGeografico) item).setPosition(i);
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Seleccione un lugar geográfico padre.");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        List<LugarGeografico> docentes = getDao().findAll();
        for (LugarGeografico LugarGeografico : docentes) {
            Hibernate.initialize(docentes);

        }
        return JsfUtil.getSelectItems(docentes, true, "Seleccione un lugar geográfico padre.");


    }

    /*public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUser(user), false, "Selecione un paciente");
    	return null;
    }*/

    public SelectItem[] getItemsAvailableSelectOneByUser() {
       // Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUser(user); 
        List<LugarGeografico> lugarGeografico = getDao().findAllByUser(); // .findAll(); //ETA
        for (LugarGeografico LugarGeografico : lugarGeografico) {
            Hibernate.initialize(LugarGeografico);

        }
        return JsfUtil.getSelectItems(lugarGeografico, true, "Selecione un lugar geográfico padre.");
    }

    @FacesConverter(forClass = LugarGeografico.class)
    public static class LugarGeograficoConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LugarGeograficoController controller = (LugarGeograficoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lugarGeograficoController");
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
            if (object instanceof LugarGeografico) {
            	LugarGeografico o = (LugarGeografico) object;
                return getStringKey(o.getIdLugar());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + LugarGeografico.class.getName());
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
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser Única");
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
            FacesMessage errorMessages = new FacesMessage("El email debe ser Ãºnico");
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
        PdfPCell cell = new PdfPCell(new Paragraph("Lugar Geográfico:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);
        PdfPTable pdfTable = new PdfPTable(5);
        float[] widths = new float[]{30f, 15f, 10f, 20f, 25f};
        pdfTable.setWidths(widths);


        pdf.add(pdfTable);

        //final Phrase phrase = new Phrase("Listado de los mÃ©dicos");

        //pdf.add(phrase);
    }

    /*public void preProcessDetallePDF(Object document) throws IOException, DocumentException {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        LugarGeografico paciente = (LugarGeografico) getDao().find(id, true);
        final Document pdf = (Document) document;
        pdf.addTitle("Detalles del mÃ©dico:");
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
        table.addCell(new Phrase("CÃ©dula:", font1));
        table.addCell(new Phrase(paciente.getCedula()));
        table.addCell(new Phrase("ProfesiÃ³n:", font1));
        table.addCell(new Phrase(paciente.getProfesion()));
        table.addCell(new Phrase("Lugar de trabajo:", font1));
        table.addCell(new Phrase(paciente.getLugartrabajo()));
        table.addCell(new Phrase("Estado civil:", font1));
        table.addCell(new Phrase(paciente.getEstadocivil()));
        table.addCell(new Phrase("NÃºmero de hijos:", font1));
        table.addCell(new Phrase(paciente.getNumerohijos()));
        table.addCell(new Phrase("Edad:", font1));
        table.addCell(new Phrase(paciente.getEdad()));

        table.addCell(new Phrase("Fecha de nacimento:", font1));
        table.addCell(paciente.getFechanacimiento().toString());
        table.addCell(new Phrase("Celular:", font1));
        table.addCell(new Phrase(paciente.getCelular()));
        table.addCell(new Phrase("TelÃ©fono:", font1));
        table.addCell(new Phrase(paciente.getTelefono()));
        table.addCell(new Phrase("Email:", font1));
        table.addCell(new Phrase(paciente.getEmail()));
        table.addCell(new Phrase("Nacionalidad:", font1));
        table.addCell(new Phrase(paciente.getNacionalidad()));
        table.addCell(new Phrase("Provincia:", font1));
        table.addCell(new Phrase(paciente.getProvincia()));

        table.addCell(new Phrase("DirecciÃ³n:", font1));
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
