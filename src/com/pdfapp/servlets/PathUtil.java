package com.pdfapp.servlets;

import javax.servlet.http.Part;

public class PathUtil {
    public static String getFileName(Part part) {
        if (part == null) return null;
        String cd = part.getHeader("content-disposition");
        if (cd == null) return null;
        for (String token : cd.split(";")) {
            token = token.trim();
            if (token.startsWith("filename")) {
                String fileName = token.substring(token.indexOf('=') + 1).trim().replace(""", "");
                return fileName;
            }
        }
        return null;
    }
}
