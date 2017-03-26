package Controller;

import Dao.ParametroDao;
import Entity.Parametro;
import Entity.Usuario;
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

@ManagedBean(name = "parametroController")
@SessionScoped
public class ParametroController implements Serializable {

    private Parametro current;
    private DataModel items = null;
    private ParametroDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ParametroController() {
    }

    public Parametro getSelected() {
        if (current == null) {
            current = new Parametro();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ParametroDao getDao() {
        if (dao == null) {
            dao = new ParametroDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            //final Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
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
            current = (Parametro) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Parametro) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new Parametro();
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
            current = (Parametro) getItems().getRowData();
            //Hibernate.initialize(current.getMedico());
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            //current = (Parametro) getItems().getRowData();
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
            current = (Parametro) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Parametro) getItems().getRowData();
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
            
            System.out.println("En error parametro");
        }
    }

    private void updateCurrentItem() {
        Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
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
            Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().findAll());
            Integer i = 0;
            /*for (Object item : items) {
                DateFormat dateFormat = null;
                Date date = ((Parametro) item).getFechanacimiento();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ((Parametro) item).setDateForFilter(dateFormat.format(date));
                ((Parametro) item).setPosition(i);
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
        List<Parametro> catalogos = getDao().findAll();
        for (Parametro Parametro : catalogos) {
            Hibernate.initialize(catalogos);

        }
        return JsfUtil.getSelectItems(catalogos, true, "Selecione un cat�logo");
    }

    public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUser(user), false, "Selecione un paciente");
    	return null;
    }

    public SelectItem[] getItemsAvailableSelectOneByUser() {
        //Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        List<Parametro> parametros = getDao().findAllByUser(); 
        //List<Parametro> parametros = getDao().findAll(); //ETA
        for (Parametro Parametro : parametros) {
            Hibernate.initialize(Parametro);

        }
        return JsfUtil.getSelectItems(parametros, true, "Selecione un cat�logo");
    }

   // @FacesConverter(forClass = Parametro.class)
    /*public static class ParametroConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ParametroController controller = (ParametroController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "parametroController");
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

  
    }*/

   

    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Cat�logos:", font));

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
        Parametro paciente = (Parametro) getDao().find(id, true);
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
        Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        int count = getDao().countByUser(user);
        return count > 0 ? "Ver " + count + " paciente(s)" : "No hay paciente";
    }*/
}
