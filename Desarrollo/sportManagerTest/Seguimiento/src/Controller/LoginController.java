                                                                                      package Controller;

import Dao.DocenteDao;
import Dao.EstudianteDao;
import Entity.Docente;
import Entity.Estudiante;
import Util.Context;
import Util.ControlSesion;
import Util.JsfUtil;
import Util.MD5;

import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Cookie;

@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginController {

    String name;
    String password;
    boolean appBlocked;
    private DocenteDao dao;
    private EstudianteDao daoE;
    private Docente current;

    public LoginController()
    {
    	//quitar esto
    	//this.name = "azuniga";
    	//this.password = "azuniga1";
    	//quitar esto
    }
    
    public DocenteDao getDao() {
        if (dao == null) {
            dao = new DocenteDao();
        }
        return dao;
    }
    
    public EstudianteDao getDaoE() {
        if (daoE == null) {
        	daoE = new EstudianteDao();
        }
        return daoE;
    }    

    public Docente getSelected() {
        if (current == null) {
            current = new Docente();
        }
        return current;
    }

    public String getName() {
        return (name != null) ? name.trim() : null;
    }

    public void setName(String name) {
        this.name = (name != null) ? name.trim() : null;
    }

    public String getPassword() {
        return (password != null) ? password.trim() : null;
    }

    public void setPassword(String password) {
        this.password = (password != null) ? password.trim() : null;
    }

    public boolean isAppBlocked() {

        appBlocked = ControlSesion.isBloqueada();
        return appBlocked;
    }

    public void setAppBlocked(boolean appBlocked) {
        this.appBlocked = appBlocked;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private Docente validarEntrada(String nombre, String passWord, String ip)
            throws Exception {

        Docente usuario = getDao().validarEntrada(nombre, MD5.crypt(passWord));

        //if (usuario == null || usuario.getDocId() == 0) {
        if (usuario == null) {
            //throw new Exception("login_invalido");
        	return null;
        }

        return usuario;
    }

    private Estudiante validarEntradaE(String nombre, String passWord, String ip)
            throws Exception {

    	Estudiante usuario = getDaoE().validarEntrada(nombre, MD5.crypt(passWord));

        //if (usuario == null || usuario.getDocId() == 0) {
        if (usuario == null) {
            throw new Exception("login_invalido");
        }

        return usuario;
    }
    
    public String validate() {

        try {

            Docente usuario = this.validarEntrada(getName(), getPassword(),Context.getIp());

            if (usuario != null) {

                ((ControlSesion) Context.getBean("SesionBean")).setUsuario(usuario);
                ((ControlSesion) Context.getBean("SesionBean")).isDocente = true;

                return "faces/protected/index.xhtml";
            } else {
            	
            	Estudiante estudiante = this.validarEntradaE(getName(), getPassword(),Context.getIp());
            	
            	if ( estudiante != null )
            	{
            		((ControlSesion) Context.getBean("SesionBean")).setUsuarioE(estudiante);
            		 usuario = this.validarEntrada("admin", "admin",Context.getIp());
            		 
                     if (usuario != null) {

                         ((ControlSesion) Context.getBean("SesionBean")).setUsuario(usuario);
                         ((ControlSesion) Context.getBean("SesionBean")).isDocente = false;
                         return "faces/protected/index.xhtml";
                         
                     }
                     else
                    	 ControlSesion.addIntentosFallidos();
            	}
            	else
            		ControlSesion.addIntentosFallidos();
            }

        } catch (Exception e) {

            String msg = e.getMessage();

            if (msg != null && msg.equals("login_invalido")) {

                String cName = "usr" + name.replace("@", "_").replace(".", "_");

                Cookie c = Context.getCookie(cName);

                if (c == null) {
                    Context.addCookie(cName, Integer.toString(1),
                            ControlSesion.session_expire);
                } else {
                    Integer n = Integer.parseInt(c.getValue()) + 1;

                    c.setValue(n.toString());
                    Context.addCookie(c);
                }
            }

            JsfUtil.addErrorMessage("Datos inválidos. Verifique su dirección de correo o contraseña.");
            ControlSesion.addIntentosFallidos();

            if (isAppBlocked()) {
                JsfUtil.addInfoMessage("Se ha bloqueado el sistema por tres intentos incorrectos de inicio de sesión.");
            }

            return null;
        }

        if (isAppBlocked()) {
            JsfUtil.addInfoMessage("Se ha bloqueado el sistema por tres intentos incorrectos de inicio de sesión.");
        }

        return null;
    }

    public String invalidate() {
        Docente user = Context.getUser();
        ((ControlSesion) Context.getBean("SesionBean")).setUsuario(null);
        Context.getSession().invalidate();

        return "/faces/login.xhtml";
    }

    public String profile() {
    	if ( ((ControlSesion) Context.getBean("SesionBean")).isDocente)
    	{
	        current = (Docente) ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
	        return "/faces/protected/profile/View.xhtml";
    	}
	    else
	    {
	    	 JsfUtil.addErrorMessage("No se puede editar este tipo de usuario.");
	    	 return null;
	    }  
        
    }

    public String prepareEditProfile() {
    	if ( ((ControlSesion) Context.getBean("SesionBean")).isDocente)
    	{
	        current = (Docente) ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
	        return "/faces/protected/profile/Edit.xhtml";
    	}
	    else
	    {
	    	 JsfUtil.addErrorMessage("No se puede editar este tipo de usuario.");
        	return null;
	    }   	
    }

    public String updateProfile() {
        try {
            Docente medById = (Docente) ((ControlSesion) Context.getBean("SesionBean")).getUsuario(); //getDao().find(current.getDocId(), false);
            if (current.getDocPassword()!= null && !current.getDocPassword().equals("")) {
                medById.setDocPassword(MD5.crypt(current.getDocPassword()));
            }
            //medById.setDocApellidos(current.getDocApellidos());
            //medById.setDocCedula(current.getDocCedula());
            medById.setDocCelular(current.getDocCelular());
            medById.setDocDireccion(current.getDocDireccion());
            //medById.setParametroDetalleByPadCodigo(current.getParametroDetalleByPadCodigo());
            //medById.setParametroDetalleByParPadCodigo(current.getParametroDetalleByParPadCodigo());
            medById.setDocLogin(current.getDocLogin());
            //medById.setDocNombres(current.getDocNombres());
            medById.setDocTelefono(current.getDocTelefono());
            
            medById.setAudFechaModi(MD5.getFechaActual());
            medById.setAudUsuModi(current.getDocId());
           
            getDao().update(medById);
            ((ControlSesion) Context.getBean("SesionBean")).setUsuario(medById);
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
            
        }
    }

    public void validateCedula(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {
        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("editProfileForm:medicoid"));
        String cedula = (String) arg2;
        String expreg = "^(?:\\+|-)?\\d+$";
        if (!cedula.matches(expreg)) {
            FacesMessage errorMessages = new FacesMessage("La cedula tiene un formato incorrecto");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);

        }
        /*if (getDao().findCedula1(cedula, id)) {
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser Ãºnica");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }*/
    }

    public void validateEmail(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        Integer id = Integer.parseInt(params.get("editProfileForm:medicoid"));
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

    public void validateCurrentPassword(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        String currentPassword = ((Docente) ((ControlSesion) Context.getBean("SesionBean"))
                .getUsuario()).getDocPassword();
        if (currentPassword == null || arg2 == null || !currentPassword.equals(MD5.crypt((String) arg2))) {
            FacesMessage errorMessages = new FacesMessage("La contraseña actual proporcionada es incorrecta.");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }

    public void validateConfirmPassword(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        String newPass = params.get("editProfileForm:contrasena");
        if (newPass == null || arg2 == null || !newPass.equals(arg2)) {
            FacesMessage errorMessages = new FacesMessage("La contraseÃ±a y su confirmaciÃ³n no coinciden.");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }
}
