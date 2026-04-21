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

    public String createPreviewPdf(String title, String content, String aiType) throws IOException {

        Path tempPath = Paths.get(tempDir);
        if (!Files.exists(tempPath)) {
            Files.createDirectories(tempPath);
        }

        String filename = System.currentTimeMillis() + "_preview.pdf";
        Path pdfPath = tempPath.resolve(filename);

        try (PDDocument document = new PDDocument()) {

            PDType1Font normalFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font boldFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

            float margin = 50;
            float yStart = 780;
            float yMin = 60;
            float leading = 15;

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(document, page);

            float y = yStart;

            // 🔥 1. TITRE PRINCIPAL
            cs.beginText();
            cs.setFont(boldFont, 18);
            cs.newLineAtOffset(margin, y);
            cs.showText(title);
            cs.endText();

            y -= 30;

            // 🔥 Nettoyage du texte (très important)
            content = cleanText(content);

            String[] lines = content.split("\\R");

            for (String line : lines) {

                line = line.trim();

                if (y < yMin) {
                    cs.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    cs = new PDPageContentStream(document, page);
                    y = yStart;
                }

                if (line.isEmpty()) {
                    y -= leading;
                    continue;
                }

                // TITRES QUESTIONS
                if (line.toLowerCase().startsWith("question")) {
                    y -= 10;
                    y = writeWrappedLine(cs, boldFont, 13, margin, y, line, 75, leading + 2);
                    y -= 5;
                    continue;
                }

                // REPONSES
                if (line.toLowerCase().startsWith("réponse")) {
                    cs.setNonStrokingColor(0, 102, 204);
                    y = writeWrappedLine(cs, boldFont, 11, margin + 10, y, line, 75, leading);
                    cs.setNonStrokingColor(0, 0, 0);
                    continue;
                }

                // CHOIX QCM
                if (line.matches("^[a-dA-D]\\).*")) {
                    y = writeWrappedLine(cs, normalFont, 11, margin + 20, y, line, 70, leading);
                    continue;
                }

                // SEPARATEUR
                if (line.contains("---")) {
                    y -= 8;
                    cs.setStrokingColor(180, 180, 180);
                    cs.moveTo(margin, y);
                    cs.lineTo(300, y);
                    cs.stroke();
                    y -= 15;
                    continue;
                }

                // TEXTE NORMAL
                y = writeWrappedLine(cs, normalFont, 11, margin, y, line, 90, leading);
            }

            cs.close();
            document.save(pdfPath.toFile());
        }

        return pdfPath.toString();
    }

    private String cleanText(String text) {
        return text
                .replace("**", "")
                .replace("###", "")
                .replace("##", "")
                .replace("#", "")
                .replace("\t", " ")
                .replace("Absolument !", "")
                .replace("Absolument!", "")
                .replace("Voici un QCM de 10 questions, niveau universitaire, basé sur le document fourni, avec la bonne réponse indiquée après chaque question.", "")
                .replace("Voici un QCM", "")
                .replace("Voici le résumé", "")
                .replace("Voici un examen", "")
                .trim();
    }
    private void writeLine(PDPageContentStream cs,
                           PDType1Font font,
                           int size,
                           float x,
                           float y,
                           String text) throws IOException {

        cs.beginText();
        cs.setFont(font, size);
        cs.newLineAtOffset(x, y);
        cs.showText(text);
        cs.endText();
    }

    private float writeWrappedLine(PDPageContentStream cs,
                                   PDType1Font font,
                                   int size,
                                   float x,
                                   float y,
                                   String text,
                                   int maxChars,
                                   float lineSpacing) throws IOException {

        while (text.length() > maxChars) {
            int breakIndex = text.lastIndexOf(' ', maxChars);
            if (breakIndex == -1) {
                breakIndex = maxChars;
            }

            String part = text.substring(0, breakIndex).trim();
            writeLine(cs, font, size, x, y, part);

            text = text.substring(breakIndex).trim();
            y -= lineSpacing;
        }

        if (!text.isEmpty()) {
            writeLine(cs, font, size, x, y, text);
            y -= lineSpacing;
        }

        return y;
    }
}
