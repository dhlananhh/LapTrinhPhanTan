package ex1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.JProgressBar;


public class CopyThread implements Runnable {    
    private String sourceFile;
    private String destinationFile;
    private JProgressBar jProgressBar1;
    

    public JProgressBar getjProgressBar1() {
        return jProgressBar1;
    }

    public void setjProgressBar1(JProgressBar jProgressBar1) {
        this.jProgressBar1 = jProgressBar1;
    }
    
    public String getSourceFile() {
        return sourceFile;
    }

    public String getDestinationFile() {
        return destinationFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void setDestinationFile(String destinationFile) {
        this.destinationFile = destinationFile;
    }

 
    @Override
    public void run() {      
        InputStream inputStream = null;
        OutputStream outputStream = null;
		
    	try{
    		File sourcefile =new File(sourceFile);
            File destinationfile =new File(destinationFile);
            inputStream = new FileInputStream(sourcefile);
            outputStream = new FileOutputStream(destinationfile);
        	
    	    byte[] buffer = new byte[1024];
            long size = sourcefile.length();
            long count=0;
    		
    	    int length;
    	    while ((length = inputStream.read(buffer)) > 0){
    	    	outputStream.write(buffer, 0, length);
                count+=length;
                jProgressBar1.setValue((int) (count*100/size));
                jProgressBar1.setString((int) (count*100/size)+"%");
    	    }

            jProgressBar1.setValue(100);
            jProgressBar1.setString("Copied Successfully");
            
    	    inputStream.close();
    	    outputStream.close();
    	    
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}
