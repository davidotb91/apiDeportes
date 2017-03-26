package Util;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Entity.Usuario;
public class AuthorizationFilter implements Filter {

    FilterConfig config = null;
    ServletContext context = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---> Iniciando filtro de autorizaci�n");
        config = filterConfig;
        context = config.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException {

        try {

            HttpServletRequest rq = (HttpServletRequest) request;
            HttpServletResponse rs = (HttpServletResponse) response;
            HttpSession session = rq.getSession();

            // Obtener el objeto (de usuario) guardado en la sesion
            ControlSesion sesionBean = (ControlSesion) session
                    .getAttribute("SesionBean");

            if (sesionBean != null) {

                Usuario user = sesionBean.getUsuario();

                if (user != null) {
                    
                    // Esta autenticado validamos si tiene permisos
                    boolean tienePermiso = validaPermisos(rq.getPathInfo(),
                            user);

                    if (tienePermiso) /* esta autenticado */ {
                        filterChain.doFilter(request, response);
                    } else {
                        rs.sendRedirect(rq.getContextPath()
                                + "/faces/login.xhtml");
                    }
                } else {
                    /* no esta autenticado */
                    rs.sendRedirect(rq.getContextPath()
                                + "/faces/login.xhtml");
                }
            } else {
                rs.sendRedirect(rq.getContextPath()
                                + "/faces/login.xhtml");
            }
        } catch (Exception e) {
            System.out.println("Error en AuthorizacionFilter: "
                    + e.getMessage());
        }
    }

    private boolean validaPermisos(String path, Usuario user) {

        if ((path.equals("/protected/Usuario/List.xhtml") || path
                .equals("/protected/Usuario/Create.xhtml") || path
                .equals("/protected/Usuario/Edit.xhtml") || path
                .equals("/protected/Usuario/View.xhtml"))
                //&& !user.isAdministrador()
                ) {
            return false;
        }

        return true;
    }

    @Override
    public void destroy() {
    }
}
