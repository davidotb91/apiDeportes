package Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import Entity.Medico;
import Util.Context;
import Util.ControlSesion;

@ManagedBean (name="menuBean")
@RequestScoped

//ETA
public class MenuView {
	
	    private MenuModel model;
	   
	    public MenuView() {
	       
	        Medico user = ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
	    	
	        model = new DefaultMenuModel();

	        Submenu submenu = new Submenu();
	        //submenu.setLabel("Dynamic Submenu 1");

	        MenuItem item = new MenuItem();
	        
	        if (user.getCategoria().getCategoriaid() !=3 )
	        {
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-person");
	        item.setValue("Médico");
	        item.setUrl("/faces/protected/medico/List.xhtml");
	        item.setRendererType("#{SesionBean.usuario.administrador}");
	        submenu.getChildren().add(item);
	        }
	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-person");
	        item.setValue("Paciente");
	        item.setUrl("/faces/protected/paciente/List.xhtml");
	        submenu.getChildren().add(item);
	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-calendar");
	        item.setValue("Citas");
	        item.setUrl("/faces/protected/cita/List.xhtml");
	        submenu.getChildren().add(item);	        

	        if (user.getCategoria().getCategoriaid() !=3 )
	        {	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-diag");
	        item.setValue("Receta");
	        item.setUrl("/faces/protected/receta/List.xhtml");
	        submenu.getChildren().add(item);
	        }
	        
	        if (user.getCategoria().getCategoriaid() !=3 )
	        {
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-contact");
	        item.setValue("Consulta");
	        item.setUrl("/faces/protected/consulta/List.xhtml");
	        submenu.getChildren().add(item);
	        }
	        
	        if (user.getCategoria().getCategoriaid() !=3 )
	        {
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-comment");
	        item.setValue("Tratamiento");
	        item.setUrl("/faces/protected/tratamiento/List.xhtml");
	        submenu.getChildren().add(item);
	        }
	        
	        if (user.getCategoria().getCategoriaid() !=3 )
	        {	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-document");
	        item.setValue("Diagnostico");
	        item.setUrl("/faces/protected/diagnostico/List.xhtml");
	        submenu.getChildren().add(item);
	        }
	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-folder-open");
	        item.setValue("Informe");
	        item.setUrl("/faces/protected/informe/List.xhtml");
	        submenu.getChildren().add(item);
	        
	        if (user.getCategoria().getCategoriaid() !=3 )
	        {	        
	        item = new MenuItem();
	        item.setStyle("color:cornflowerblue");
	        item.setIcon("ui-icon-folder-clipboard");
	        item.setValue("Odontograma");
	        item.setUrl("/faces/protected/odontograma/List.xhtml");
	        submenu.getChildren().add(item);
	        }
	        
	        
	        //submenu.setLabel("Dynamic Submenu 1");

	        /*<p:menuitem style="color:cornflowerblue" icon="ui-icon-person" value="Médico" url="/faces/protected/medico/List.xhtml" rendered="#{SesionBean.usuario.administrador}" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-person"  value="Paciente" url="/faces/protected/paciente/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-calendar" value="Citas" url="/faces/protected/cita/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-signal-diag" value="Receta" url="/faces/protected/receta/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-contact" value="Consulta" url="/faces/protected/consulta/List.xhtml" />
            <p:menuitem style="color:cornflowerblue"  icon="ui-icon-comment" value="Tratamiento" url="/faces/protected/tratamiento/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-document" value="Diagnostico" url="/faces/protected/diagnostico/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-folder-open" value="Informe" url="/faces/protected/informe/List.xhtml" />
            <p:menuitem style="color:cornflowerblue" icon="ui-icon-clipboard" value="Odontograma" url="/faces/protected/odontograma/List.xhtml" />*/	        
	        
	        
	        /*item.setUrl("/faces/protected/medico/List.xhtml");
	        item.setValue("page 1");
	        //item.getAttributes().put("idobject", "1" );
	        item.setIcon(_icon);
	        submenu.getChildren().add(item);*/
	        


	        model.addSubmenu(submenu);
	    }


	    public MenuModel getModel() {
	        return model;
	    }

	    public void setModel(MenuModel m) {
	        model = m;
	    }

}	


