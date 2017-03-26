package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "fileUploadView")

@SessionScoped
public class FileUploadView implements Serializable {
 
	private String destination="D:\\datos\\";
	
    public void handleFileUpload(FileUploadEvent event) {
        // Do what you want with the file
        
        try {
        	System.out.println(event.getFile().getFileName());
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            FacesMessage msg = new FacesMessage("El archivo ", event.getFile().getFileName() + " fue subido exitosamente.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);            
        } catch (IOException e) {
            e.printStackTrace();
            FacesMessage msg = new FacesMessage("El archivo ", event.getFile().getFileName() + " no fue subido exitosamente.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);           
            
        }
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
}