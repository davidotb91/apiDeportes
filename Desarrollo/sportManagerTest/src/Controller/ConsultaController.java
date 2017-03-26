package Controller;

import Dao.ConsultaDao;
import Dao.DiagnosticoDao;
import Dao.TratamientoDao;
import Entity.Consulta;
import Entity.Medico;
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

@ManagedBean(name = "consultaController")
@SessionScoped
public class ConsultaController implements Serializable {

    private Consulta current;
    private DataModel items = null;
    private ConsultaDao dao;
    private TratamientoDao tratamientodao;
    private DiagnosticoDao diagnosticodao;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ConsultaController() {
    }

    public Consulta getSelected() {
        if (current == null) {
            current = new Consulta();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ConsultaDao getDao() {
        if (dao == null) {
            dao = new ConsultaDao();
        }
        return dao;
    }

    private TratamientoDao getTratamientodao() {
        tratamientodao = new TratamientoDao();

        return tratamientodao;
    }

    private DiagnosticoDao getDiagnosticodao() {
        if (diagnosticodao == null) {
            diagnosticodao = new DiagnosticoDao();
        }
        return diagnosticodao;
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
            current = (Consulta) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Consulta) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new Consulta();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {

            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());

            Hibernate.initialize(current.getTratamiento());
            Hibernate.initialize(current.getDiagnostico());
            getDiagnosticodao().create(current.getDiagnostico());
            getTratamientodao().create(current.getTratamiento());
            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
            getDao().create(current);

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConsultaCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {

        if (getItems().getRowIndex() != -1) {
            current = (Consulta) getItems().getRowData();
            Hibernate.initialize(current.getDiagnostico());
            Hibernate.initialize(current.getTratamiento());
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Consulta) getItems().getRowData();
            Hibernate.initialize(current.getDiagnostico());
            Hibernate.initialize(current.getTratamiento());
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());
        }

        return "Edit";
    }

    public String update() {
        try {
            Hibernate.initialize(current.getDiagnostico());
            Hibernate.initialize(current.getTratamiento());
            Hibernate.initialize(current.getMedico());
            Hibernate.initialize(current.getPaciente());

            current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());

            getDao().update(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConsultaUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (Consulta) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (Consulta) getItems().getRowData();
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
            System.out.println(current.getConsultaid());
            getDao().delete(current);
            getTratamientodao().delete(current.getTratamiento());
            getDiagnosticodao().delete(current.getDiagnostico());

            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ConsultaDeleted"));
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
                Date date = ((Consulta) item).getFechaconsulta();
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ((Consulta) item).setDateForFilter(dateFormat.format(date));
                ((Consulta) item).setPosition(i);
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
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione una");
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getDao().findAll(), true, "Selecione una");




    }

    @FacesConverter(forClass = Consulta.class)
    public static class ConsultaControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConsultaController controller = (ConsultaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "consultaController");
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
            if (object instanceof Consulta) {
                Consulta o = (Consulta) object;
                return getStringKey(o.getConsultaid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Consulta.class.getName());
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
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Consultas:", font));

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
        Consulta consulta = (Consulta) getDao().find(id, true);
        DateFormat dateFormat = null;
        Date date = consulta.getFechaconsulta();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        consulta.setDateForFilter(dateFormat.format(date));

        final Document pdf = (Document) document;
        pdf.addTitle("Detalles del médico:");
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfDetalleClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Detalles de la consulta:", font));

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
        table.addCell(new Phrase(consulta.getPaciente().getNombre() + " " + consulta.getPaciente().getApellidopaterno() + " " + consulta.getPaciente().getApellidomaterno()));
        table.addCell(new Phrase("Médico:", font1));
        table.addCell(new Phrase(consulta.getMedico().getNombre() + " " + consulta.getMedico().getApellidopaterno() + " " + consulta.getMedico().getApellidomaterno()));

        table.addCell(new Phrase("Fecha :", font1));
        table.addCell(new Phrase(consulta.getDateForFilter()));
        table.addCell(new Phrase("Sist:", font1));
        table.addCell(new Phrase(String.valueOf(consulta.getSist())));
        table.addCell(new Phrase("diast:", font1));
        table.addCell(new Phrase(String.valueOf(consulta.getDiast())));
        table.addCell(new Phrase("Pulsaciones:", font1));
        table.addCell(new Phrase(consulta.getPulsaciones() + " ppm"));
        table.addCell(new Phrase("Ritmo respiratorio:", font1));
        table.addCell(new Phrase(consulta.getRitmorespiratorio() + " rpm"));
        table.addCell(new Phrase("Temperatura:", font1));
        table.addCell(new Phrase(consulta.getTemperatura() + " grados"));

        table.addCell(new Phrase("Altura:", font1));
        table.addCell(new Phrase(consulta.getAltura() + " cms"));
        table.addCell(new Phrase("Peso:", font1));
        table.addCell(new Phrase(consulta.getPeso() + " kgrs"));
        table.addCell(new Phrase("IMC:", font1));
        table.addCell(new Phrase(consulta.getImc() + " kg/m^2"));
        table.addCell(new Phrase("Anamnesis:", font1));
        table.addCell(new Phrase(consulta.getAnamnesis()));
        table.addCell(new Phrase("Exploración:", font1));
        table.addCell(new Phrase(consulta.getExploracion()));
        table.addCell(new Phrase("Diagnostico:", font1));
        table.addCell(new Phrase(consulta.getDiagnostico().getTitulo()));
        table.addCell(new Phrase("Tratamiento:", font1));
        table.addCell(new Phrase(consulta.getTratamiento().getTratamiento()));
        table.addCell(new Phrase("Observaciones:", font1));
        table.addCell(new Phrase(consulta.getObservacion()));


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
        return count > 0 ? "Ver " + count + " consulta(s)" : "No hay consultas";
    }
    
    //ETA
    public String irOdontograma()
    {
    	 if (getItems().getRowIndex() != -1) {
             current = (Consulta) getItems().getRowData();
             selectedItemIndex = getItems().getRowIndex();
             
             ((ControlSesion) Context.getBean("SesionBean"))
             .setPacienteTmp(current.getPaciente()); 
             
         } else {
             Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
             Integer position = Integer.parseInt(params.get("action"));

             selectedItemIndex = position;
             getItems().setRowIndex(selectedItemIndex);
             current = (Consulta) getItems().getRowData();
         }
         //return "/faces/protected/odontograma/Create.xhtml";
         try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Clinica/faces/protected/odontograma/Create.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         return "";
    	 
         
    }
    
    public String irInforme()
    {
    	 if (getItems().getRowIndex() != -1) {
             current = (Consulta) getItems().getRowData();
             selectedItemIndex = getItems().getRowIndex();

             ((ControlSesion) Context.getBean("SesionBean"))
             .setPacienteTmp(current.getPaciente());
             
         } else {
             Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
             Integer position = Integer.parseInt(params.get("action"));

             selectedItemIndex = position;
             getItems().setRowIndex(selectedItemIndex);
             current = (Consulta) getItems().getRowData();
         }
    	 
         try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/Clinica//faces/protected/informe/Create.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         return "";
    }
    //ETA    
}
