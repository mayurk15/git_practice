package com.pdfapp.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.pdfapp.utils.DBConnection;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("role"))) {
            response.sendRedirect("index.jsp");
            return;
        }

        Part filePart = request.getPart("pdfFile");
        String fileName = PathUtil.getFileName(filePart);
        if (fileName == null || !fileName.toLowerCase().endsWith(".pdf")) {
            response.sendRedirect("upload.jsp?error=1");
            return;
        }

        String uploadPath = getServletContext().getRealPath("/") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String savedPath = uploadPath + File.separator + System.currentTimeMillis() + "_" + fileName;
        try (InputStream in = filePart.getInputStream()) {
            Files.copy(in, new File(savedPath).toPath());
        }

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO pdf_files (file_name, file_path) VALUES (?, ?)");
            ps.setString(1, fileName);
            ps.setString(2, savedPath);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new ServletException(e);
        }

        response.sendRedirect("admin.jsp?uploaded=1");
    }
}
