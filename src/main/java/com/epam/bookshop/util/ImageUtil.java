package com.epam.bookshop.util;

import javax.servlet.http.Part;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class ImageUtil {

    private ImageUtil() {
    }

    public static String imageToBase(InputStream imageBinary) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while (true) {
            try {
                if ((bytesRead = imageBinary.read(buffer)) == -1) break;
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static InputStream baseToImage(String bookImage) {
        byte[] image = Base64.getDecoder().decode(bookImage);
        return new ByteArrayInputStream(image);
    }

    public static boolean isImageFormatValid(Part part) {
        if (part.getName().equalsIgnoreCase(BOOK_IMAGE)) {
            return part.getSize() > MIN_IMAGE_SIZE && part.getSize() < MAX_IMAGE_SIZE && (
                    part.getContentType().equalsIgnoreCase(CONTENT_TYPE_PNG) ||
                            part.getContentType().equalsIgnoreCase(CONTENT_TYPE_JPG) ||
                            part.getContentType().equalsIgnoreCase(CONTENT_TYPE_JPEG));
        }
        return false;
    }

}
