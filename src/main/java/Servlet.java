import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//TRAEMOS EL PATH
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        //CREAMOS LA SESION PARA PODER ENVIAR LOS PRODUCTOS
        HttpSession session = req.getSession();
        //CREAMOS UNA LISTA PARA EL ARTICULO Y VALOR
        List<String> articulo=(List<String>)session.getAttribute("articulo");
        List<Double> valor = (List<Double>) session.getAttribute("valor");
        List<Integer> cantidad=(List<Integer>)session.getAttribute("cantidad");
        //CREAMOS UN CONDICIONAL PARA VER SI LA LISTA ESTA VACIA
        if (articulo==null||valor==null) {
            //INICIALIZAMOS LA LISTA
            articulo=new ArrayList<>();
            //AQUI SETEAMOS EL ARTICULO CON LLAVE Y SU VALOR
            session.setAttribute("articulo",articulo);
        }
        if (valor==null) {
            valor=new ArrayList<>();
            //AQUI SETEAMOS EL VALOR
            session.setAttribute("valor",valor);
        }
        if(cantidad==null){
        cantidad=new ArrayList<>();
        //SETEAMOS TAMBIEN LA CANTIDAD
            session.setAttribute("cantidad",cantidad);
        }
        // Obtiene los parámetros del nuevo artículo, su valor y la cantidad desde la solicitud
        String articuloNuevo = req.getParameter("articulo");
        double valorNuevo = Double.parseDouble(req.getParameter("valor"));
        int cantidadNueva = Integer.parseInt(req.getParameter("cantidad"));

        // Verifica que el artículo no esté vacío y que el valor no sea cero antes de agregarlo a las listas
        if (articuloNuevo != null && !articuloNuevo.trim().equals("") && valorNuevo != 0 && cantidadNueva > 0) {
            articulo.add(articuloNuevo);
            valor.add(valorNuevo);
            cantidad.add(cantidadNueva);
        }

        // Genera la respuesta HTML
        try (PrintWriter out = resp.getWriter()) {
            out.print("<!DOCTYPE html>");
            out.print("<html>");
            out.print("<head>");
            out.print("<meta charset=\"utf-8\">");
            out.print("<title>Lista de Artículos</title>");

            out.print("<style>");
            out.print("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 20px; }");
            out.print("table { width: 60%; margin: 25px auto; border-collapse: collapse; background-color: #ffffff; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.print("th, td { padding: 15px; text-align: right; border-bottom: 1px solid #dddddd; }");
            out.print("th { background-color: #f2f2f2; font-weight: bold; }");
            out.print("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.print("tr:hover { background-color: #f1f1f1; }");
            out.print("h1 { text-align: center; color: #333333; }");
            out.print("a { display: block; width: 200px; margin: 20px auto; padding: 10px; text-align: center; text-decoration: none; color: white; background-color: #28a745; border-radius: 5px; transition: background-color 0.3s; }");
            out.print("a:hover { background-color: #218838; }");
            out.print("</style>");

            out.print("</head>");
            out.print("<body>");
            out.print("<h1>Lista de Artículos</h1>");
            out.print("<table>");
            out.print("<tr><th>Artículo</th><th>Precio Unitario</th><th>Cantidad</th><th>Subtotal</th></tr>");

            // Inicializa el total para calcular el precio total de los artículos
            double total = 0;
            for (int i = 0; i < articulo.size(); i++) {
                double subtotal = valor.get(i) * cantidad.get(i);
                out.print("<tr><td>" + articulo.get(i) + "</td><td>" + valor.get(i) + "</td><td>" + cantidad.get(i) + "</td><td>" + subtotal + "</td></tr>");
                total += subtotal;
            }

            // Calcula el IVA (15%) y el total con IVA
            double iva = total * 0.15;
            double totalConIva = total + iva;

            // Muestra el total, el IVA y el total con IVA en la tabla
            out.print("<tr><th>Total</th><td colspan='3'>" + String.format("%.2f", total) + "</td></tr>");
            out.print("<tr><th>IVA (15%)</th><td colspan='3'>" + String.format("%.2f", iva) + "</td></tr>");
            out.print("<tr><th>Total con IVA</th><td colspan='3'>" + String.format("%.2f", totalConIva) + "</td></tr>");
            out.print("</table>");

            // Enlace para regresar al inicio
            out.print("<br><br>");
            out.print("<a href='index.html'>IR AL INICIO</a>");
            out.print("</body>");
            out.print("</html>");
        }
    }
}
