package tn.esprit.powerHr.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IconGenerator {
    public static void main(String[] args) {
        generateIcon("E", "enterprise.png", Color.decode("#3498db"));
        generateIcon("D", "department.png", Color.decode("#2ecc71"));
    }

    private static void generateIcon(String text, String fileName, Color color) {
        int width = 64;
        int height = 64;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        
        // Enable antialiasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw circle background
        g2d.setColor(color);
        g2d.fillOval(4, 4, width-8, height-8);
        
        // Draw text
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 32));
        FontMetrics fm = g2d.getFontMetrics();
        int textX = (width - fm.stringWidth(text)) / 2;
        int textY = ((height - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, textX, textY);
        
        g2d.dispose();
        
        try {
            File outputDir = new File("src/main/resources/images");
            outputDir.mkdirs();
            ImageIO.write(image, "PNG", new File(outputDir, fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 