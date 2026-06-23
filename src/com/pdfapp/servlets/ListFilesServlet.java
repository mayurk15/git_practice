package com.pdfapp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.pdfapp.utils.DBConnection;

public class ListFilesServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, file_name, upload_time, downloads FROM pdf_files ORDER BY upload_time DESC");
            ResultSet rs = ps.executeQuery();
            List<PDFItem> items = new ArrayList<>();
            while (rs.next()) {
                items.add(new PDFItem(rs.getInt("id"), rs.getString("file_name"), rs.getTimestamp("upload_time"), rs.getInt("downloads")));
            }
            request.setAttribute("items", items);
            request.getRequestDispatcher("list.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

class PDFItem {
    public int id;
    public String name;
    public java.sql.Timestamp uploaded;
    public int downloads;
    public PDFItem(int id, String name, java.sql.Timestamp uploaded, int downloads) {
        this.id = id; this.name = name; this.uploaded = uploaded; this.downloads = downloads;
    }
}
