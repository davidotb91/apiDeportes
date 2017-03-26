package Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import Entity.Usuario;
import Util.Context;
import Util.ControlSesion;

@ManagedBean (name="menuBean")
@RequestScoped

//ETA
public class MenuView {
	
	    private MenuModel model;
	   
	    public MenuView() {
	       
	        Usuario user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
	        
	    	
	        model = new DefaultMenuModel();

	        Submenu submenu = new Submenu();
	        //submenu.setLabel("Dynamic Submenu 1");

	        MenuItem item = new MenuItem();
	        
	       // if (user.getUsuaLogin().equals("admin") && userE == null) //administrador
	        if (user.getUsuaLogin().equals("admin"))
	        {	
	        	item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Jugador");
		        item.setUrl("/faces/protected/jugador/List.xhtml");
		        submenu.getChildren().add(item);
		        

		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Catálogos");
		        item.setUrl("/faces/protected/parametrodetalle/List.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Estudiante");
		        item.setUrl("/faces/protected/estudiante/List.xhtml");
		        submenu.getChildren().add(item);	 
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Usuario");
		        item.setUrl("/faces/protected/Usuario/List.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Estadísticas");
		        item.setUrl("/faces/protected/tema/ReportStatistics.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Seguimientos");
		        item.setUrl("/faces/protected/seguimiento/ReportAbstract.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Estudiantes");
		        item.setUrl("/faces/protected/seguimiento/ReportStudents.xhtml");
		        submenu.getChildren().add(item);			        
	        }
	     
	        else //tutores
	        {
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Tema");
		        item.setUrl("/faces/protected/tema/List.xhtml");
		        submenu.getChildren().add(item);	        
		        
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-person");
		        item.setValue("Seguimiento");
		        item.setUrl("/faces/protected/seguimiento/List.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Estadísticas");
		        item.setUrl("/faces/protected/tema/ReportStatistics.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Seguimientos");
		        item.setUrl("/faces/protected/seguimiento/ReportAbstract.xhtml");
		        submenu.getChildren().add(item);
		        
		        item = new MenuItem();
		        item.setStyle("color:cornflowerblue");
		        item.setIcon("ui-icon-script");
		        item.setValue("Estudiantes");
		        item.setUrl("/faces/protected/seguimiento/ReportStudents.xhtml");
		        submenu.getChildren().add(item);		        
	        }
	        
	      

	        model.addSubmenu(submenu);
	    }


	    public MenuModel getModel() {
	        return model;
	    }

	    public void setModel(MenuModel m) {
	        model = m;
	    }

}	


