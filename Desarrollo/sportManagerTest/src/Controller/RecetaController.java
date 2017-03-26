package Controller;

import Dao.RecetaDao;
import Entity.Medico;
import Entity.Receta;
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

@ManagedBean(name = "recetaController")
@SessionScoped
public class RecetaController implements Serializable {

    private Receta current;
    private DataModel items = null;
    private RecetaDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public RecetaController() {
    }

    public Receta getSelected() {
        if (current == null) {
            current = new Receta();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RecetaDao getDao() {
        if (dao == null) {
            dao = new RecetaDao();
        }
        return dao;
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
            current = (Receta) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Receta) getItems().getRowData();


        }
        return "View";
    }

    public String prepareCreate() {
        current = new Receta();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {

            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecetaCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        if (getItems().getRowIndex() != -1) {
            current = (Receta) getItems().getRowData();
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Receta) getItems().getRowData();
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
        }
        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecetaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Receta) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Receta) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("RecetaDeleted"));
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
            items = getPagination().createPageDataModel();
            Integer i = 0;
            for (Object item : items) {
                DateFormat dateFormat = null;
                Date date = ((Receta) item).getFechaprescripcion();
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ((Receta) item).setDateForFilter(dateFormat.format(date));
                ((Receta) item).setPosition(i);
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione una Receta");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getDao().findAll(), true, "Selecione una Receta");
    }

    @FacesConverter(forClass = Receta.class)
    public static class RecetaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RecetaController controller = (RecetaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "recetaController");
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
            if (object instanceof Receta) {
                Receta o = (Receta) object;
                return getStringKey(o.getRecetaid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Receta.class.getName());
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
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de recetas:", font));

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table1.addCell(cell);

        pdf.add(table1);
        PdfPTable pdfTable = new PdfPTable(5);
        float[] widths = new float[]{20f, 20f, 20f, 20f, 20f};
        pdfTable.setWidths(widths);


        pdf.add(pdfTable);

        //final Phrase phrase = new Phrase("Listado de los médicos");

        //pdf.add(phrase);
    }

    public void preProcessDetallePDF(Object document) throws IOException, DocumentException {

        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("action"));
        Receta receta = (Receta) getDao().find(id, true);
        DateFormat dateFormat = null;
        Date date = receta.getFechaprescripcion();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        receta.setDateForFilter(dateFormat.format(date));

        final Document pdf = (Document) document;
        pdf.addTitle("Detalles de la receta:");
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfDetalleClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Detalles de receta:", font));

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
        table.addCell(new Phrase("Número:", font1));
        table.addCell(new Phrase(String.valueOf(receta.getNumero())));

        table.addCell(new Phrase("Paciente:", font1));
        table.addCell(new Phrase(receta.getPaciente().getNombre() + " " + receta.getPaciente().getApellidopaterno() + " " + receta.getPaciente().getApellidomaterno()));
        table.addCell(new Phrase("Médico:", font1));
        table.addCell(new Phrase(receta.getMedico().getNombre() + " " + receta.getMedico().getApellidopaterno() + " " + receta.getMedico().getApellidomaterno()));

        table.addCell(new Phrase("Fecha de prescripción:", font1));
        table.addCell(new Phrase(receta.getDateForFilter()));
        table.addCell(new Phrase("Fármaco:", font1));
        table.addCell(new Phrase(receta.getFarmaco()));
        table.addCell(new Phrase("Envases (unidades):", font1));
        table.addCell(new Phrase(String.valueOf(receta.getNumeroenvases())));

        table.addCell(new Phrase("Duración:", font1));
        table.addCell(new Phrase(receta.getDuraciontratamiento()));
        table.addCell(new Phrase("Posología:", font1));
        table.addCell(new Phrase(receta.getPosologia()));
        table.addCell(new Phrase("Pauta:", font1));
        table.addCell(new Phrase(receta.getPauta()));

        table.addCell(new Phrase("Información adicional:", font1));
        table.addCell(receta.getInformacionadicional());


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
        return count > 0 ? "Ver " + count + " receta(s)" : "No hay recetas";
    }
}
