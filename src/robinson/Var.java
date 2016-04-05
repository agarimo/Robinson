/*
 * The MIT License
 *
 * Copyright 2016 Agarimo.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robinson;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;
import sql.Conexion;
import util.CalculaNif;
import util.Regex;

/**
 *
 * @author Agarimo
 */
public class Var {

    public static Stage stage;
    public static Conexion con;
    public static String dbName = "robinson";

    public static void initVar() {
        initVarDriver();
    }

    private static void initVarDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Var.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        con = new Conexion();
        con.setNombre("robinson");
        con.setDireccion("192.168.6.20");
        con.setPuerto("3306");
        con.setUsuario("robinson");
        con.setPass("R@bins.n1984");
    }
    
    public static String calculaCif(String cif) {
        String aux = cif;

        CalculaNif cn = new CalculaNif();

        if (cn.letrasCif.contains("" + aux.charAt(0))) {
            if (aux.length() == 8) {
                aux = cn.calcular(aux);
            }
        } else if (cn.letrasNie.contains("" + aux.charAt(0))) {
            if (aux.length() <= 8) {
                aux = cn.calcular(aux);
            }
        } else if (aux.length() <= 8) {
            aux = cn.calcular(aux);
        }

        return aux;
    }
    
    public static boolean validarCif(String cif) {
        if (cif.length() < 9) {
            return false;
        } else {
            CalculaNif cn = new CalculaNif();
            return cn.isvalido(cif);
        }
    }

    public static boolean validarTelefono(String telefono) {
        Regex rg = new Regex();
        return !rg.isBuscar("\\D", telefono);
    }
}
