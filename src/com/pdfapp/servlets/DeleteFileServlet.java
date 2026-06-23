package com.pdfapp.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.pdfapp.utils.DBConnection;

public class DeleteFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("ListFilesServlet");
            return;
        }
        int id = Integer.parseInt(idStr);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT file_path FROM pdf_files WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String path = rs.getString("file_path");
                File f = new File(path);
                if (f.exists()) f.delete();
            }
            PreparedStatement del = con.prepareStatement("DELETE FROM pdf_files WHERE id=?");
            del.setInt(1, id);
            del.executeUpdate();
            response.sendRedirect("admin.jsp?deleted=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
