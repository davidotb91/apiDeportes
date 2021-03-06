package Controller;

import Dao.DataReporteEstadisticaDao;
import Dao.ParametroDetalleDao;
import Dao.DocenteDao;
import Dao.TemaDao;
import Entity.ParametroDetalle;
import Entity.Docente;
import Entity.Tema;
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
import java.awt.GradientPaint;
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

import java.io.File;
import java.io.FileInputStream;

import javax.annotation.PostConstruct;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "reporteEstadisticaController")
@SessionScoped
public class ReporteEstadisticaController implements Serializable {

    private Tema current;
    private DataModel items = null;
    private DataModel itemsR = null;
    private TemaDao dao;
    private DataReporteEstadisticaDao daoR;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Docente user = null;
    

    public ReporteEstadisticaController() {
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
    
    private DataReporteEstadisticaDao getDaoR() {
        if (daoR == null) {
        	daoR = new DataReporteEstadisticaDao();
        }
        return daoR;
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
                    return new ListDataModel(getDao().obtenerReporteEstadisticas(null,null,null));
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

    public DataModel getItems() {
        if (items == null) {
            //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().obtenerReporteEstadisticas(null,null,null));
            Integer i = 0;
            for (Object item : items) {
                //DateFormat dateFormat = null;
                //Date date = ((Tema) item).getAudFechaCrea();
                //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                //((Tema) item).setDateForFilter(dateFormat.format(date));
                //((Tema) item).setPosition(i);
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

       
    public void preProcessPDF(Object document) throws IOException, DocumentException {

        final Document pdf = (Document) document;

        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setHtmlStyleClass("pdfClass");
        pdf.open();

        PdfPTable table1 = new PdfPTable(1);
        Font font = FontFactory.getFont("Times-Roma", 16, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Estad�sticas:", font));

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
    
    private StreamedContent graphicText;
    
    private StreamedContent chartBar;
    private StreamedContent chartPie;
    
 
    // @PostConstruct
    public void doCharBar() {
        try {
            //Graphic Text
            /*BufferedImage bufferedImg = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImg.createGraphics();
            g2.drawString("This is a text", 0, 10);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "png", os);
            graphicText = new DefaultStreamedContent(new ByteArrayInputStream(os.toByteArray()), "image/png");*/
 
            //Chart
            //JFreeChart jfreechart = ChartFactory.createPieChart("Maestr�as en curso por estado.", createDataset(), true, true, false);
            
            JFreeChart jfreechart = ChartFactory.createBarChart3D("Maestr�as por Estado - Barras",      // chart title
                    "Category",               // domain axis label
                    "Value",                  // range axis label
                    createDatasetBar(),                  // data
                    PlotOrientation.VERTICAL, // orientation
                    true,                     // include legend
                    true,                     // tooltips
                    false );                    // urls);
            
            
         // set the background color for the chart...
            jfreechart.setBackgroundPaint(Color.white);

            // get a reference to the plot for further customisation...
            CategoryPlot plot = jfreechart.getCategoryPlot();
            plot.setBackgroundPaint(Color.lightGray);
            plot.setDomainGridlinePaint(Color.white);
            plot.setDomainGridlinesVisible(true);
            plot.setRangeGridlinePaint(Color.white);

            // set the range axis to display integers only...
            final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            // disable bar outlines...
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setDrawBarOutline(false);
            
            // set up gradient paints for series...
            GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, Color.blue, 
                0.0f, 0.0f, new Color(0, 0, 64)
            );
            GradientPaint gp1 = new GradientPaint(
                0.0f, 0.0f, Color.green, 
                0.0f, 0.0f, new Color(0, 64, 0)
            );
            GradientPaint gp2 = new GradientPaint(
                0.0f, 0.0f, Color.red, 
                0.0f, 0.0f, new Color(64, 0, 0)
            );
            renderer.setSeriesPaint(0, gp0);
            renderer.setSeriesPaint(1, gp1);
            renderer.setSeriesPaint(2, gp2);

            //CategoryAxis domainAxis = plot.getDomainAxis();
            //domainAxis.setCategoryLabelPositions(
                //CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
            
            File chartFile = new File("dynamichartBar");
            ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 800, 300);
            chartBar = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");   
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public StreamedContent getGraphicText() {
        return graphicText;
    }
         
    public StreamedContent getChartBar() {
    	doCharBar();
        return chartBar;
    }
    
    public StreamedContent getChartPie() {
    	doChartPie3D();
        return chartPie;
    }
    
     
    private CategoryDataset createDatasetBar() {
        

    	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    	List<Object[]> listDatos = getDaoR().obtenerInformacionEstadisticas(null,null,null);
    	for (Object[] datos : listDatos) {
    		dataset.addValue(new Double(datos[1].toString()), datos[0].toString(), "ESTADOS MAESTRIAS");
    		
    	}
        
        return dataset;
        
    }
   	
        private PieDataset createDataset() {
        	
            DefaultPieDataset dataset = new DefaultPieDataset();
        	List<Object[]> listDatos = getDaoR().obtenerInformacionEstadisticas(null,null,null);
        	for (Object[] datos : listDatos) {
        		dataset.setValue( datos[0].toString(), new Double(datos[1].toString()));
        	}    	
        return dataset;
    }
        
        public void doChartPie() {
            try {
                    
                //Chart
                JFreeChart jfreechart = ChartFactory.createPieChart("Maestr�as por Estado - Pie.", createDataset(), true, true, true);  
                
                File chartFile = new File("dynamichartPie");
                ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 400, 400);
                chartPie = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");   
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void doChartPie3D() {
            try {
                    
                //Chart
                final JFreeChart jfreechart = ChartFactory.createPieChart3D(
                        "Maestr�as por Estado - Pie 3D",  // chart title
                        createDataset(),                // data
                        true,                   // include legend
                        true,
                        false
                    );
                
                final PiePlot3D plot = (PiePlot3D) jfreechart.getPlot();
                plot.setStartAngle(290);
                plot.setDirection(Rotation.CLOCKWISE);
                plot.setForegroundAlpha(0.5f);
                plot.setNoDataMessage("No data to display");
                plot.setLabelGenerator(null);
                
                File chartFile = new File("dynamichartPie");
                ChartUtilities.saveChartAsPNG(chartFile, jfreechart, 400, 400);
                chartPie = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");   
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }        
        

}