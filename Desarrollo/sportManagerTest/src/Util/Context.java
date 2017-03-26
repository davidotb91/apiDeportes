package Util;

import Entity.Medico;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase utilitaria para el manejo del contexto de JSF
 *
 * @author Fernando
 */
public class Context {

    // not to be instantiated
    private Context() {
    }

    public static String getIp() {

        return ((HttpServletRequest) Context.getFacesContext()
                .getExternalContext().getRequest()).getRemoteAddr();
    }

    public static Medico getUser() {

        return (Medico) ((ControlSesion) Context.getBean("SesionBean"))
                .getUsuario();
    }

    /**
     * Obtiene un bean manejado determinado por su nombre
     *
     * @param name El nombre del bean manejado
     * @return Object
     */
    public static Object getBean(String name) {
        try {
            return (Object) getFacesContext().getApplication().getELResolver()
                    .getValue(getFacesContext().getELContext(), null, name);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtiene la sesion http
     *
     * @param create Especifica si la sesion se crea en caso que no exista
     * @return HttpSession
     */
    public static HttpSession getSession(boolean create) {

        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(create);

    }

    /**
     * Obtiene o crea una sesion http
     *
     * @return HttpSession
     */
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }

    /**
     * Obtiene el FacesContext
     *
     * @return FacesContext
     */
    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    /**
     * Obtiene el ApplicationContext
     *
     * @return FacesContext
     */
    public static Application getApplication() {
        return getFacesContext().getApplication();
    }

    /**
     * Crea un componente en el contexto de la aplicacion
     *
     * @return FacesContext
     */
    public static UIComponent createComponent(String type) {
        return getFacesContext().getApplication().createComponent(type);
    }

    /**
     * Otiene el objeto Response
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return (HttpServletResponse) getFacesContext().getExternalContext()
                .getResponse();
    }

    /**
     * Obtiene el objeto Request
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext()
                .getRequest();
    }

    /**
     * Adiciona una Cookie
     *
     * @param cookie Objeto Cookie a adicionar
     */
    public static void addCookie(Cookie cookie) {
        getResponse().addCookie(cookie);
    }

    /**
     * Adiciona una Cookie
     *
     * @param name Nombre de la cookie
     * @param value Valor de la cookie
     */
    public static void addCookie(String name, String value, int expiry) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expiry);
        cookie.setPath("");
        getResponse().addCookie(cookie);
    }

    /**
     * Obtiene todas las cookies
     *
     * @return Cookie []
     */
    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    /**
     * Obtiene una Cookie especifica
     *
     * @param cookieName Nombre de la cookie
     * @return Cookie
     */
    public static Cookie getCookie(String cookieName) {
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if (cookie.getName().equalsIgnoreCase(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene un parametro del http request
     *
     * @param name Nombre del parametro
     * @return String Valor del parametro
     */
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(name);
    }

    /**
     * Get session attribute
     *
     * @param name the name of the attribute
     * @return Object attribute value
     */
    public static Object getSessionAttribute(String name) {
        return getSession().getAttribute(name);
    }

    public static void setSessionAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    public static void removeSessionAttribute(String name) {
        getSession().removeAttribute(name);
    }

    public static ServletContext getServletContext() {
        return (ServletContext) getFacesContext().getExternalContext()
                .getContext();
    }

    public static String getRealPath() {

        return getServletContext().getRealPath("/");
    }

    public static String getPath() {
        return getServletContext().getContextPath();
    }
}