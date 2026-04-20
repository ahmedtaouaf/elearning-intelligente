package com.example.Elearning.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

@Service
public class PdfPreviewService {

    @Value("${temp.dir}")
    private String tempDir;

    public String createPreviewPdf(String title, String content) throws IOException {
        Path tempPath = Paths.get(tempDir);
        if (!Files.exists(tempPath)) {
            Files.createDirectories(tempPath);
        }

        String filename = System.currentTimeMillis() + "_preview.pdf";
        Path pdfPath = tempPath.resolve(filename);

        try (PDDocument document = new PDDocument()) {
            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            float margin = 50;
            float leading = 16;
            float startY = 780;
            float minY = 60;
            int maxCharsPerLine = 95;

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(document, page);

            float y = startY;

            // titre
            cs.beginText();
            cs.setFont(boldFont, 16);
            cs.newLineAtOffset(margin, y);
            cs.showText(title);
            cs.endText();

            y -= 30;

            String[] lines = content.split("\\R");

            for (String line : lines) {
                String remaining = line == null ? "" : line;

                if (remaining.isBlank()) {
                    y -= leading;
                    if (y < minY) {
                        cs.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        cs = new PDPageContentStream(document, page);
                        y = startY;
                    }
                    continue;
                }

                while (!remaining.isEmpty()) {
                    int end = Math.min(maxCharsPerLine, remaining.length());
                    String part = remaining.substring(0, end);
                    remaining = remaining.substring(end);

                    if (y < minY) {
                        cs.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        cs = new PDPageContentStream(document, page);
                        y = startY;
                    }

                    cs.beginText();
                    cs.setFont(font, 11);
                    cs.newLineAtOffset(margin, y);
                    cs.showText(part.replace("\t", " "));
                    cs.endText();

                    y -= leading;
                }
            }

            cs.close();
            document.save(pdfPath.toFile());
        }

        return pdfPath.toString();
    }
}
