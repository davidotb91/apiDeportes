package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author leonel
 */
public class MD5 {

    private static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }
    
    public static Date getFechaActual()
    {
    	
    	//Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        Integer anio = fecha.get(Calendar.YEAR);
        Integer mes = fecha.get(Calendar.MONTH);
        Integer dia = fecha.get(Calendar.DAY_OF_MONTH);
        /*int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);*/
        
        String mesS = "";
        mes++;
        if (mes <=9 )
        	mesS = "0" + mes.toString();
        else
        	mesS = mes.toString();
        
        String diaS = "";
        if (dia <=9 )
        	diaS = "0" + dia.toString();
        else
        	diaS = dia.toString();
        
        //String fechaS = anio + "-" + mesS + "-" + diaS;
        String fechaS = diaS  + "/" + mesS + "/" + anio ;
        //Date fechaD = Date.parse(fechaS); 
        Date fechaD = new Date(fechaS);
        
        System.out.println("Fecha Actual: " + anio + "-" + mesS + "-" + diaS);      
        
		return fechaD;
    }

    private static int num_provincias = 24;
	public static Boolean validarCedula(String cedula){
	    //verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
	    int prov = Integer.parseInt(cedula.substring(0, 2));
	
	    if (!((prov > 0) && (prov <= num_provincias))) {
	    	//addError("La cédula ingresada no es válida");
	    	System.out.println("Error: cedula ingresada mal");
	        return false;
	    }
	
	    //verifica que el último dígito de la cédula sea válido
	    int[] d = new int[10];
	    //Asignamos el string a un array
	    for (int i = 0; i < d.length; i++) {
	        d[i] = Integer.parseInt(cedula.charAt(i) + "");
	    }
	
	    int imp = 0;
	    int par = 0;
	
	    //sumamos los duplos de posición impar
	    for (int i = 0; i < d.length; i += 2) {
	        d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
	        imp += d[i];
	    }
	
	    //sumamos los digitos de posición par
	    for (int i = 1; i < (d.length - 1); i += 2) {
	        par += d[i];
	    }
	
	    //Sumamos los dos resultados
	    int suma = imp + par;
	    
	    //Restamos de la decena superior
	    int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) +
	            "0") - suma;
	    
	    //Si es diez el décimo dígito es cero        
	    d10 = (d10 == 10) ? 0 : d10;
	
	    //si el décimo dígito calculado es igual al digitado la cédula es correcta
	    if (d10 == d[9]) {
	    	return true;
	    }else {
	    	return false;
	    }
	} //fin validar cèdula
    
}
