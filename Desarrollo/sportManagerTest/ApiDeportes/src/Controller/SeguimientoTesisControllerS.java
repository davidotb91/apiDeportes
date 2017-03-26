package Controller;

import Dao.SeguimientoTesisDao;
import Entity.Docente;
import Entity.ParametroDetalle;
import Entity.SeguimientoTesis;
import Util.Context;
import Util.ControlSesion;
import Util.HibernateUtil;
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
import javax.persistence.OneToOne;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.FileUploadEvent;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


//descarga
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;


@ManagedBean(name = "seguimientoTesisControllerS")
@SessionScoped




public class SeguimientoTesisControllerS implements Serializable {

    private SeguimientoTesis current;
    private DataModel items = null;
    private SeguimientoTesisDao dao;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    Docente usuarioActual = null;
    
    //private static String destination="D:\\datos\\";
    //private static String destination= "/resources/datos/";
    private static String destination = "F:\\Mis Proyectos\\2015\\IMUSEGUIMIENTOTESIS\\Solucion\\Clinica\\Seguimiento\\WebContent\\resources\\datos\\";
    
    
    private String path = "";
    

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		this.current.setSegPathDocumento(path);
	}
    

    public SeguimientoTesisControllerS() {
    	usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
    	
    }

    public SeguimientoTesis getSelected() {
        if (current == null) {
            current = new SeguimientoTesis();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SeguimientoTesisDao getDao() {
        if (dao == null) {
            dao = new SeguimientoTesisDao();
        }
        return dao;
    }

    public PaginationHelper getPagination() {
    	//final Docente usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        if (pagination == null) {
            //final Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();        	
        	
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getDao().count(); 
                }
                
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getDao().findAllByUserS(usuarioActual));
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
            current = (SeguimientoTesis) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();
        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));

            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (SeguimientoTesis) getItems().getRowData();
        }
        return "View";
    }

    public String prepareCreate() {
        current = new SeguimientoTesis();
        
        //ParametroDetalle paramDetalle = new ParametroDetalle();
        /*ParametroDetalleController paramDetalleController = new ParametroDetalleController();
        ParametroDetalle paramDetalle = new ParametroDetalle();
        
        paramDetalle = getDao().findParametroDetalle("ING");
        if (paramDetalle != null)
        	current.setParametroDetalle(paramDetalle);*/
        if (usuarioActual == null)
        	usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        current.setDocente(usuarioActual);
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            
            Hibernate.initialize(current.getTema());
            Hibernate.initialize(current.getDocente());
            //current.setMedico(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
        	
        	if (this.getPath().equals("")) {
                //JsfUtil.addErrorMessage("Tiene consultas asociadas, por lo que no se puede eliminar");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Advertencia!", "No ha subido el documento de revisi�n, favor revise."));
                return "";
            }
            
            if (usuarioActual == null)
            	usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
         
            //current.setDocente(((ControlSesion) Context.getBean("SesionBean")).getUsuario());
        	current.setAudUsuCrea(usuarioActual.getDocId());
        	current.setAudUsuModi(usuarioActual.getDocId());
        	//current.setAudUsuCrea(0);
        	//current.setAudUsuModi(0);        	
        	current.setAudFechaCrea(Util.MD5.getFechaActual());
        	current.setAudFechaModi(Util.MD5.getFechaActual());
        	current.setSegPathDocumento(this.getPath());
            getDao().create(current);
            
			////////////////////////////////
			        	
			/*Transaction trns = null;
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
			//Session session = HibernateUtil.buildSessionFactory().getCurrentSession();
			//session.refresh(entity); //ojo
			trns = session.beginTransaction();
			//session.update(usuarioActual);
			session.save(current);
			session.getTransaction().commit();
			} catch (RuntimeException e) {
			if (trns != null) {
			  trns.rollback();
			}
			e.printStackTrace();
			} finally {
			//session.flush();
			//session.close();
			} */       	
			
			////////////////////////////////      
            
            
            
            
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteCreated"));
            return prepareList();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured") + e.getMessage());
            return null;
        }
    }

    public String prepareEdit() {

        if (getItems().getRowIndex() != -1) {
            current = (SeguimientoTesis) getItems().getRowData();
            Hibernate.initialize(current.getDocente());
            Hibernate.initialize(current.getTema());
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (SeguimientoTesis) getItems().getRowData();
            Hibernate.initialize(current.getDocente());
            Hibernate.initialize(current.getTema());


        }

        return "Edit";
    }

    public String update() {
        try {
            //Hibernate.initialize(current.getDocente());
            Hibernate.initialize(current.getTema());
            ////
            //Docente usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            current.setDocente(usuarioActual);
            ////
        	current.setAudUsuModi(usuarioActual.getDocId());
        	//current.setAudUsuModi(0);
        	current.setAudFechaModi(Util.MD5.getFechaActual());
        	//current.setSegPathDocumento(this.getPath());
            
        	
        	getDao().update(current,usuarioActual);
        	////////////////////////////////
        	
        	/*Transaction trns = null;
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            try {
                //Session session = HibernateUtil.buildSessionFactory().getCurrentSession();
            	//session.refresh(entity); //ojo
                trns = session.beginTransaction();
                if (usuarioActual != null)
                	session.update(usuarioActual);
                session.update(current);
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                if (trns != null) {
                    trns.rollback();
                }
                e.printStackTrace();
            } finally {
                //session.flush();
                //session.close();
            } */       	
        	
        	////////////////////////////////            
            
            Hibernate.initialize(current.getDocente());
            Hibernate.initialize(current.getTema());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PacienteUpdated"));
            return "List";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        if (getItems().getRowIndex() != -1) {
            current = (SeguimientoTesis) getItems().getRowData();
            selectedItemIndex = getItems().getRowIndex();

        } else {
            Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Integer position = Integer.parseInt(params.get("action"));
            selectedItemIndex = position;
            getItems().setRowIndex(selectedItemIndex);
            current = (SeguimientoTesis) getItems().getRowData();
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
    	//items = null;
        if (items == null) {
            //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        	//Docente usuarioActual = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
            items = new ListDataModel(getDao().findAllByUserS(usuarioActual));
            Integer i = 0;
            for (Object item : items) {
                /*
            	DateFormat dateFormat = null;
                Date date = ((SeguimientoTesis) item).getAudFechaCrea();
                dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ((SeguimientoTesis) item).setDateForFilter(dateFormat.format(date));
                */
                ((SeguimientoTesis) item).setPosition(i);
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

    /*public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getDao().findAll(), false, "Selecione un Cat�logo");
    }/

    public SelectItem[] getItemsAvailableSelectOne() {
        List<Seguimiento> docentes = getDao().findAll();
        for (Seguimiento Seguimiento : docentes) {
            Hibernate.initialize(docentes);

        }
        return JsfUtil.getSelectItems(docentes, true, "Selecione un Estado");


    }

    /*public SelectItem[] getItemsAvailableSelectManyByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //return JsfUtil.getSelectItems(getDao().findAllByUserS(user), false, "Selecione un paciente");
    	return null;
    }*/

    /*public SelectItem[] getItemsAvailableSelectOneByUser() {
        //Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        //List<Paciente> pacientes = getDao().findAllByUserS(user); 
        List<Seguimiento> temas = getDao().findAllByUserS(); // .findAll(); //ETA
        for (Seguimiento Seguimiento : temas) {
            Hibernate.initialize(Seguimiento);

        }
        return JsfUtil.getSelectItems(temas, true, "Selecione un Estudiante");
    }*/

    
    @FacesConverter(forClass = SeguimientoTesis.class)
    public static class SeguimientoConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SeguimientoTesisControllerS controller = (SeguimientoTesisControllerS) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "seguimientoTesisController");
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
            if (object instanceof SeguimientoTesis) {
            	SeguimientoTesis o = (SeguimientoTesis) object;
                return getStringKey(o.getSegId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SeguimientoTesis.class.getName());
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
        PdfPCell cell = new PdfPCell(new Paragraph("Listado de Seguimientos:", font));

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
        Seguimiento paciente = (Seguimiento) getDao().find(id, true);
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
    
  
    public void handleFileUpload(FileUploadEvent event) {
    	
        try {
        	System.out.println(event.getFile().getFileName());
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            this.setPath(event.getFile().getFileName());

            /*UIComponent x = findComponent("segPathDocumento", FacesContext.getCurrentInstance().getViewRoot());
            Map<String, Object> a = x.getAttributes();
            int b = 0;
            a.put("value","xyz");*/
            FacesMessage msg = new FacesMessage("El archivo ", event.getFile().getFileName() + " fue subido exitosamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        } catch (IOException e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage("El archivo ", event.getFile().getFileName() + " no fue subido exitosamente.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);           
            
        }
    }
    
    private UIComponent findComponent(String id, UIComponent where) 
    {
    	if (where == null) 
    	{
    	   return null;
    	}
    	else if (where.getId().equals(id)) 
    	{
    	   return where;
    	}
    	else 
    	{
    	   List<UIComponent> childrenList = where.getChildren();
    	   if (childrenList == null || childrenList.isEmpty()) 
    	   {
    	      return null;
    	   }
    	   for (UIComponent child : childrenList) 
    	   {
    	      UIComponent result = null;
    	      result = findComponent(id, child);
    	      if(result != null) 
    	      {
    	         return result;
    	      }
    	   }
    	}   
    	  return null;
    }	  
    
    public void copyFile(String fileName, InputStream in) {
        try {
           
	             // write the inputStream to a FileOutputStream
	             OutputStream out = new FileOutputStream(new File(destination + fileName));
	           
	             int read = 0;
	             byte[] bytes = new byte[1024];
	           
	             while ((read = in.read(bytes)) != -1) {
	                 out.write(bytes, 0, read);
	             }
	           
	             in.close();
	             out.flush();
	             out.close();
	           
	             System.out.println("New file created!");
             } catch (IOException e) {
             System.out.println(e.getMessage());
             }
        }

  //descarga
    private StreamedContent file;
    
    public void FileDownloadView() {       
        /*InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(current.getSegPathDocumento());
        file = new DefaultStreamedContent(stream, "image/jpg", current.getSegPathDocumento());*/
        
    	//"D:\\datos\\FORMULARIO_107.pdf"
    	
    	  if (getItems().getRowIndex() != -1) {
              current = (SeguimientoTesis) getItems().getRowData();
              Hibernate.initialize(current.getDocente());
              Hibernate.initialize(current.getTema());
              selectedItemIndex = getItems().getRowIndex();

          } else {
              Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
              Integer position = Integer.parseInt(params.get("action"));
              selectedItemIndex = position;
              getItems().setRowIndex(selectedItemIndex);
              current = (SeguimientoTesis) getItems().getRowData();
              Hibernate.initialize(current.getDocente());
              Hibernate.initialize(current.getTema());


          }

    	
    	String archivo = current.getSegPathDocumento();
        InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("/resources/datos/"+current.getSegPathDocumento());
        file = new DefaultStreamedContent(stream, "application/pdf", "bajado_" + current.getSegPathDocumento());
        
    }
 
    public StreamedContent getFile() {
    	FileDownloadView();
        return file;
    }    
}
