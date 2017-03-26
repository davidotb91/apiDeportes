package Util;

import Entity.Docente;
import Entity.Estudiante;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Cookie;




@ManagedBean(name = "SesionBean")
@SessionScoped
public class ControlSesion implements Serializable {

    private Docente usuario;
    private Estudiante usuarioE;
    private boolean logueado;
    private static final String cookie_bloqueo = "cblk";
    private static final String cookie_intentos = "ctry";
    public static final int session_expire = 1800;
    public boolean isDocente = true;


    public boolean isLogueado() {
        // setLogueado(false);
        logueado = usuario != null;
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = usuario != null;
    }

    public Docente getUsuario() {
        return usuario;
    }

    public void setUsuario(Docente usuario) {
        logueado = usuario != null;
        this.usuario = usuario;
    }
    
    public void setUsuarioE(Estudiante usuario) {
        this.usuarioE = usuario;
    }

    public Estudiante getUsuarioE() {
        return usuarioE;
    }    
    
    public String getNombreUsuarioConectado()
    {
    	if (this.isDocente)
    		return "Bienvenido " + this.usuario.getDocNombres() + " " + this.usuario.getDocApellidos(); 
    	else			
    		return "Bienvenido " + this.usuarioE.getEstApellidos() + " " + this.usuarioE.getEstNombres();
    	
    }
    
    

    /* * * * * * * * * * * * * * * * * */
    public static int getIntentosFallidos() {

        // System.out.println("leyendo cookie " + cookie_intentos);

        Cookie myCookie = Context.getCookie(cookie_intentos);

        if (myCookie != null) {

            // System.out.println("con valor: " + myCookie.getValue());

            return Integer.parseInt(myCookie.getValue());
        }

        return 0;
    }

    public static void setIntentosFallidos(int intentosFallidos) {

        // System.out.println("poniendo cookie " + cookie_intentos);

        Context.addCookie(cookie_intentos, Integer.toString(intentosFallidos),
                session_expire);
        setBloqueada(intentosFallidos >= 5);
        // this.intentosFallidos = intentosFallidos;
    }

    public static int addIntentosFallidos() {

        // System.out.println("adicionando " + cookie_intentos);

        setIntentosFallidos(getIntentosFallidos() + 1);
        return getIntentosFallidos();
    }

    public static boolean isBloqueada() {

        // System.out.println("leyendo cookie " + cookie_bloqueo);

        Cookie c = Context.getCookie(cookie_bloqueo);

        if (c != null) {

            return c.getValue().equalsIgnoreCase("si");
        }

        return false;
    }

    public static void setBloqueada(boolean bloqueada) {

        // System.out.println("poniendo cookie " + cookie_bloqueo);

        Context.addCookie(cookie_bloqueo, (bloqueada) ? "si" : "no",
                (bloqueada) ? session_expire : -1);

        // this.bloqueada = bloqueada;
    }
}