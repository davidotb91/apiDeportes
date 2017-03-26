package Controller;

import Dao.OdontogramaDao;
import Dao.TratamientoDao;
import Entity.Medico;
import Entity.Odontograma;
import Entity.Paciente;
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
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

@ManagedBean(name = "odontogramaController")
@SessionScoped
public class OdontogramaController implements Serializable {

    private Odontograma current;
    private DataModel items = null;
    private OdontogramaDao dao;
    private TratamientoDao tratamientodao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OdontogramaController() {
    }

    public Odontograma getSelected() {
    	
        //ETA
        Paciente pacienteTmp = ((ControlSesion) Context.getBean("SesionBean")).getPacienteTmp();
        //ETA
    	
        if (current == null) {
            current = new Odontograma();
            selectedItemIndex = -1;
            
        }
        //ETA
        if (pacienteTmp != null)
        {
        	current.setPaciente(pacienteTmp);
        	current.setRealizado("0");
        	//if (items == null) 
        	{
                Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
                items = new ListDataModel(getDao().findAllByUser(user));
                Integer i = 0;
                for (Object item : items) {
                    DateFormat dateFormat = null;
                    Date date = ((Odontograma) item).getFecha();
                    dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    ((Odontograma) item).setDateForFilter(dateFormat.format(date));
                    ((Odontograma) item).setPosition(i);
                    i++;

                }
            }
        	((ControlSesion) Context.getBean("SesionBean")).setPacienteTmp(null);
        }
        //ETA
        
        return current;
    }

    public void setSelected(Odontograma o) {
        this.current = o;
    }

    private OdontogramaDao getDao() {
    	if (dao == null) {
            dao = new OdontogramaDao();
        }
        return dao;
    }

    private TratamientoDao getTratamientodao() {
        tratamientodao = new TratamientoDao();

        return tratamientodao;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            final Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getDao().countByUser(user);
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

    public String prepareView() {

        if (getItems().getRowIndex() != -1) {
            current = (Odontograma) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Odontograma) getItems().getRowData();


        }
        return "View";

    }

    public String prepareCreate() {
        current = new Odontograma();
        current.setRealizado("0");
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            Hibernate.initialize(current.getPaciente());
            Hibernate.initialize(current.getMedico());
            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            

            
            getDao().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OdontogramaCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        if (getItems().getRowIndex() != -1) {
            current = (Odontograma) getItems().getRowData();
            Hibernate.initialize(current.getPaciente());
            Hibernate.initialize(current.getMedico());
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Odontograma) getItems().getRowData();
        }
        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getPaciente());
            Hibernate.initialize(current.getMedico());
            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OdontogramaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Odontograma) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Odontograma) getItems().getRowData();
        }

        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String delete(ActionEvent action) {
        if (getItems().getRowIndex() != -1) {
            current = (Odontograma) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Odontograma) getItems().getRowData();
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

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OdontogramaDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        int count = getDao().countByUser(user);
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
            Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().findAllByUser(user));
            Integer i = 0;
            for (Object item : items) {
                DateFormat dateFormat = null;
                Date date = ((Odontograma) item).getFecha();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ((Odontograma) item).setDateForFilter(dateFormat.format(date));
                ((Odontograma) item).setPosition(i);
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione un odontograma");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        List<Odontograma> odontogramas = getDao().findAll();
        for (Odontograma odontograma : odontogramas) {

            Hibernate.initialize(odontograma);

        }
        return JsfUtil.getSelectItems(odontogramas, true, "Selecione un odontograma");
    }

    @FacesConverter(forClass = Odontograma.class)
    public static class OdontogramaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OdontogramaController controller = (OdontogramaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "odontogramaController");
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
            if (object instanceof Odontograma) {
                Odontograma o = (Odontograma) object;
                return getStringKey(o.getOdontogramaid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Odontograma.class.getName());
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
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de odontograma:", font));

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
        Odontograma odontograma = (Odontograma) getDao().find(id, true);
        DateFormat dateFormat = null;
        Date date = odontograma.getFecha();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        odontograma.setDateForFilter(dateFormat.format(date));

        final Document pdf = (Document) document;
        pdf.addTitle("Detalles del odontograma:");
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfDetalleClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Detalles del odontograma:", font));

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
        table.addCell(new Phrase("Paciente:", font1));
        table.addCell(new Phrase(odontograma.getPaciente().getNombre() + " " + odontograma.getPaciente().getApellidopaterno() + " " + odontograma.getPaciente().getApellidomaterno()));
        table.addCell(new Phrase("Médico:", font1));
        table.addCell(new Phrase(odontograma.getMedico().getNombre() + " " + odontograma.getMedico().getApellidopaterno() + " " + odontograma.getMedico().getApellidomaterno()));
       
        table.addCell(new Phrase("Fecha:", font1));
        table.addCell(new Phrase(odontograma.getDateForFilter()));
        table.addCell(new Phrase("Número de piezas:", font1));
        table.addCell(new Phrase(odontograma.getNumerodepieza()));
        table.addCell(new Phrase("Material de relleno:", font1));
        table.addCell(new Phrase(odontograma.getMaterialrelleno()));
        table.addCell(new Phrase("Material de sellado:", font1));
        table.addCell(new Phrase(odontograma.getMaterialsellado()));
        table.addCell(new Phrase("Realizado:", font1));
        table.addCell(new Phrase(odontograma.getRealizado()));
        table.addCell(new Phrase("Presupuesto:", font1));
        table.addCell(new Phrase(odontograma.getPresupuesto()));

        table.addCell(new Phrase("Forma de pago:", font1));
        table.addCell(odontograma.getFormadepago());
        table.addCell(new Phrase("Descripción del tratamiento:", font1));
        table.addCell(new Phrase(odontograma.getDescripciontratamiento()));
        

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
        return count > 0 ? "Ver " + count + " odontograma(s)" : "No hay odontogramas";
    }
}
