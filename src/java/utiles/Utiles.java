
package utiles;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ALUMNO
 */
public class Utiles {
    //la cantidad de registros que se mostrar de una sola vez
    public static final int REGISTRO_PAGINA = 10;

    public static String quitarGuiones(String texto){        
        return texto.replace("--","");        
    }
    
    public static String md5(String palabra){
        String palabraMD5 = "";
        
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = md.digest(palabra.getBytes());
            int size = b.length;
            StringBuffer sb = new StringBuffer(size);
            
            for (int i = 0; i<size; i++) {
                int u = b[i] &255;
                if (u<16) {
                    sb.append("0"+Integer.toHexString(u));
                }else{
                    sb.append(Integer.toHexString(u));                
                }
            }
            palabraMD5 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return palabraMD5;
        
    }
}

    
