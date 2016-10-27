package com.jcrew.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ScreenshotGenerator {

    private static Logger logger = LoggerFactory.getLogger(ScreenshotGenerator.class);

    public static byte[] getScreenshot(WebDriver driver) {
        PropertyReader reader = PropertyReader.getPropertyReader();
        String browser = reader.getProperty("browser");

        byte[] screenshot = new byte[0];

        if (browser.equalsIgnoreCase("chrome")) {
            AShot shot = new AShot();
            shot = shot.shootingStrategy(ShootingStrategies.viewportPasting(100));

            Screenshot screen = shot.takeScreenshot(driver);
            BufferedImage originalImage = screen.getImage();

            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(originalImage, "png", baos);
                baos.flush();
                screenshot = baos.toByteArray();

            } catch (IOException noScreehshot) {
                logger.error("Not able to take screenshot", noScreehshot);
            }

        } else {
            screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }

        return screenshot;
    }

}