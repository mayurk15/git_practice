package com.pdfapp.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.pdfapp.utils.DBConnection;

public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("ListFilesServlet");
            return;
        }
        int id = Integer.parseInt(idStr);

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT file_name, file_path FROM pdf_files WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fileName = rs.getString("file_name");
                String filePath = rs.getString("file_path");
                File file = new File(filePath);
                if (!file.exists()) {
                    response.getWriter().write("File not found on server.");
                    return;
                }
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename="" + fileName + """);
                try (FileInputStream in = new FileInputStream(file);
                     OutputStream out = response.getOutputStream()) {
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }

                // increment downloads
                PreparedStatement upd = con.prepareStatement("UPDATE pdf_files SET downloads = downloads + 1 WHERE id=?");
                upd.setInt(1, id);
                upd.executeUpdate();
            } else {
                response.getWriter().write("Invalid file id.");
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
