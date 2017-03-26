package Controller;

import Dao.MedicoDao;
import Entity.Medico;
import Entity.Paciente;
import Util.Context;
import Util.ControlSesion;
import Util.JsfUtil;
import Util.MD5;
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
import java.awt.event.ActionEvent;
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
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "medicoController")
@SessionScoped
public class MedicoController implements Serializable {

    private Medico current;
    private DataModel items = null;
    private MedicoDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public MedicoController() {
    }

    public Medico getSelected() {
        if (current == null) {
            current = new Medico();
            selectedItemIndex = -1;
        }
        return current;
    }

    public void setSelected(Medico m) {
        this.current = m;
    }

    private MedicoDao getDao() {
        if (dao == null) {
            dao = new MedicoDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {

        if (getItems().getRowIndex() != -1) {
            current = (Medico) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Medico) getItems().getRowData();


        }
        return "View";

    }

    public String prepareCreate() {
        current = new Medico();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Hibernate.initialize(current.getCategoria());
            System.out.println(current.getCategoria().getDescripcion());
            current.setContrasena(MD5.crypt(current.getContrasena()));
            getDao().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicoCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        if (getItems().getRowIndex() != -1) {
            current = (Medico) getItems().getRowData();
            Hibernate.initialize(current.getCategoria());
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Medico) getItems().getRowData();
            Hibernate.initialize(current.getCategoria());
        }
        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getCategoria());
            if (current.getContrasena() == null || current.getContrasena().equals("")) {
                Medico med = getDao().find(current.getMedicoid(), false);
                current.setContrasena(med.getContrasena());
            } else {
                current.setContrasena(MD5.crypt(current.getContrasena()));
            }
            getDao().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Medico) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Medico) getItems().getRowData();
        }

        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";

    }

    public String delete(ActionEvent a) {

        System.out.println("Delete record Called....");
        selectedItemIndex = current.getPosition();
        getItems().setRowIndex(selectedItemIndex);
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

            if (getDao().PacienteAsociado(current)) {
                JsfUtil.addErrorMessage("Tiene pacientes asociados, por lo que no se puede eliminar");
                return;
            }

            getDao().delete(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("MedicoDeleted"));
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
            items = new ListDataModel(getDao().findAll());
            Integer i = 0;
            for (Object item : items) {
                DateFormat dateFormat = null;
                Date date = ((Medico) item).getFechanacimiento();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ((Medico) item).setDateForFilter(dateFormat.format(date));
                ((Medico) item).setPosition(i);
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione un médico");
    }

    
    
    public SelectItem[] getItemsAvailableSelectOne() {
        List<Medico> medicos = getDao().findAll();
        for (Medico medico : medicos) {

            Hibernate.initialize(medico);

        }
        return JsfUtil.getSelectItems(medicos, true, "Selecione un médico");
    }
    
    //ETA
    public SelectItem[] getItemsAvailableSelectOneByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        List<Medico> medicos = getDao().findAll();
        for (Medico medico : medicos) {
            Hibernate.initialize(medico);

        }
        return JsfUtil.getSelectItems(medicos, true, "Selecione un m�dico");
    }


    @FacesConverter(forClass = Medico.class)
    public static class MedicoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MedicoController controller = (MedicoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "medicoController");
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
            if (object instanceof Medico) {
                Medico o = (Medico) object;
                return getStringKey(o.getMedicoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Medico.class.getName());
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
        if (getDao().findCedula1(cedula, id)) {
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser única");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
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
        if (getDao().findEmail1(email, id)) {
            FacesMessage errorMessages = new FacesMessage("El email debe ser único");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(errorMessages);
        }
    }

    public void validateConfirmPassword(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        String newPass = params.get("form_create:contrasena");
        if (newPass != null && !newPass.equals(arg2)) {
            FacesMessage errorMessages = new FacesMessage("La contraseña y su confirmación no coinciden.");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();


        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de médicos:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);
        PdfPTable pdfTable = new PdfPTable(6);
        float[] widths = new float[]{25f, 10f, 15f, 10f, 20f, 20f};
        pdfTable.setWidths(widths);


        pdf.add(pdfTable);

        //final Phrase phrase = new Phrase("Listado de los médicos");

        //pdf.add(phrase);
    }

    public void preProcessDetallePDF(Object document) throws IOException, DocumentException {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        Medico medico = (Medico) getDao().find(id, true);
        final Document pdf = (Document) document;
        pdf.addTitle("Detalles del médico:");
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfDetalleClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Detalles del médico:", font));

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
        table.addCell(new Phrase(medico.getNombre() + " " + medico.getApellidopaterno() + " " + medico.getApellidomaterno()));
        table.addCell(new Phrase("Cédula:", font1));
        table.addCell(new Phrase(medico.getCedula()));
        table.addCell(new Phrase("Profesión:", font1));
        table.addCell(new Phrase(medico.getProfesion()));
        table.addCell(new Phrase("Lugar de trabajo:", font1));
        table.addCell(new Phrase(medico.getLugartrabajo()));
        table.addCell(new Phrase("Estado civil:", font1));
        table.addCell(new Phrase(medico.getEstadocivil()));
        table.addCell(new Phrase("Número de hijos:", font1));
        table.addCell(new Phrase(medico.getNumerohijos()));
        table.addCell(new Phrase("Edad:", font1));
        table.addCell(new Phrase(medico.getEdad()));

        table.addCell(new Phrase("Fecha de nacimento:", font1));
        table.addCell(medico.getFechanacimiento().toString());
        table.addCell(new Phrase("Celular:", font1));
        table.addCell(new Phrase(medico.getCelular()));
        table.addCell(new Phrase("Teléfono:", font1));
        table.addCell(new Phrase(medico.getTelefono()));
        table.addCell(new Phrase("Email:", font1));
        table.addCell(new Phrase(medico.getEmail()));
        table.addCell(new Phrase("Nacionalidad:", font1));
        table.addCell(new Phrase(medico.getNacionalidad()));
        table.addCell(new Phrase("Provincia:", font1));
        table.addCell(new Phrase(medico.getProvincia()));
        table.addCell(new Phrase("Localidad:", font1));
        table.addCell(new Phrase(medico.getLocalidad()));
        table.addCell(new Phrase("Dirección:", font1));
        table.addCell(new Phrase(medico.getDireccion()));
        Hibernate.initialize(medico.getCategoria());
        table.addCell(new Phrase("Categoría:", font1));
        table.addCell(new Phrase(medico.getCategoria().getDescripcion()));
        table.addCell(new Phrase("Sueldo:", font1));
        table.addCell("$" + String.valueOf(medico.getSueldo()));
        table.addCell(new Phrase("Fecha de alta:", font1));
        table.addCell(medico.getFechaalta().toString());
        table.addCell(new Phrase("Fecha de baja:", font1));
        table.addCell(medico.getFechabaja().toString());

        pdf.add(table);

        PdfPTable table2 = new PdfPTable(1);
        PdfPCell cell2 = new PdfPCell(new Paragraph("             "));

        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell2.setBackgroundColor(Color.LIGHT_GRAY);
        table2.addCell(cell2);
        pdf.add(table2);



    }

    public String getDashBoardMessage() {
        int count = getDao().count();
        return count > 0 ? "Ver " + count + " médico(s)" : "No hay médicos";
    }
}
