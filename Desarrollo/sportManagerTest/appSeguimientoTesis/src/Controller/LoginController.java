package Controller;

import Dao.MedicoDao;
import Entity.Medico;
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
    private MedicoDao dao;
    private Medico current;

    public LoginController()
    {
    	//quitar esto
    	this.name = "mario@gmail.com";
    	this.password = "mario";
    	//quitar esto
    }
    
    public MedicoDao getDao() {
        if (dao == null) {
            dao = new MedicoDao();
        }
        return dao;
    }

    public Medico getSelected() {
        if (current == null) {
            current = new Medico();
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
    private Medico validarEntrada(String nombre, String passWord, String ip)
            throws Exception {

        Medico usuario = getDao().validarEntrada(nombre, MD5.crypt(passWord));

        if (usuario == null || usuario.getMedicoid() == 0) {
            throw new Exception("login_invalido");
        }

        return usuario;
    }

    public String validate() {

        try {

            Medico usuario = this.validarEntrada(getName(), getPassword(),
                    Context.getIp());

            if (usuario != null) {

                ((ControlSesion) Context.getBean("SesionBean"))
                        .setUsuario(usuario);

                return "faces/protected/index.xhtml";
            } else {
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
        Medico user = Context.getUser();
        ((ControlSesion) Context.getBean("SesionBean")).setUsuario(null);
        Context.getSession().invalidate();

        return "/faces/login.xhtml";
    }

    public String profile() {

        return "/faces/protected/profile/View.xhtml";
    }

    public String prepareEditProfile() {
        current = (Medico) ((ControlSesion) Context.getBean("SesionBean")).getUsuario();
        return "/faces/protected/profile/Edit.xhtml";
    }

    public String updateProfile() {
        try {
            Medico medById = getDao().find(current.getMedicoid(), false);
            if (current.getContrasena() != null && !current.getContrasena().equals("")) {
                medById.setContrasena(MD5.crypt(current.getContrasena()));
            }
            medById.setNombre(current.getNombre());
            medById.setSegundonombre(current.getSegundonombre());
            medById.setApellidomaterno(current.getApellidomaterno());
            medById.setApellidopaterno(current.getApellidopaterno());
            medById.setCedula(current.getCedula());
            medById.setProfesion(current.getProfesion());
            medById.setLugartrabajo(current.getLugartrabajo());
            medById.setGenero(current.getGenero());
            medById.setEstadocivil(current.getEstadocivil());
            medById.setNumerohijos(current.getNumerohijos());
            medById.setEdad(current.getEdad());
            medById.setFechanacimiento(current.getFechanacimiento());
            medById.setNacionalidad(current.getNacionalidad());
            medById.setProvincia(current.getProvincia());
            medById.setLocalidad(current.getLocalidad());
            medById.setTelefono(current.getTelefono());
            medById.setCelular(current.getCelular());
            medById.setEmail(current.getEmail());
            medById.setCategoria(current.getCategoria());
            medById.setFechaalta(current.getFechaalta());
            medById.setFechabaja(current.getFechabaja());
            medById.setSueldo(current.getSueldo());
            medById.setDireccion(current.getDireccion());
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
        if (getDao().findCedula1(cedula, id)) {
            FacesMessage errorMessages = new FacesMessage("La cedula debe ser única");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
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
        if (getDao().findEmail1(email, id)) {
            FacesMessage errorMessages = new FacesMessage("El email debe ser único");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);

            throw new ValidatorException(errorMessages);
        }
    }

    public void validateCurrentPassword(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        String currentPassword = ((Medico) ((ControlSesion) Context.getBean("SesionBean"))
                .getUsuario()).getContrasena();
        if (currentPassword == null || arg2 == null || !currentPassword.equals(MD5.crypt((String) arg2))) {
            FacesMessage errorMessages = new FacesMessage("La contrase�a actual proporcionada es incorrecta.");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }

    public void validateConfirmPassword(FacesContext arg0, UIComponent arg1, Object arg2)
            throws ValidatorException {

        Map<String, String> params = arg0.getExternalContext().getRequestParameterMap();
        String newPass = params.get("editProfileForm:contrasena");
        if (newPass == null || arg2 == null || !newPass.equals(arg2)) {
            FacesMessage errorMessages = new FacesMessage("La contraseña y su confirmación no coinciden.");
            errorMessages.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(errorMessages);
        }
    }
}
