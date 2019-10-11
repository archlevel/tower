package com.tower.service.common.exception;

import jp.sourceforge.qrcode.data.QRCodeImage;
import junit.framework.TestCase;

import org.junit.Test;

import com.tower.service.util.HttpUtil;

import jp.sourceforge.qrcode.QRCodeDecoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by kevin on 15/1/4.
 */
public class SpIdTest  extends TestCase {
	
	@Test
    public void  testGetSpId(){
    	HttpUtil.doGet(null,null);	
    }

    @Test
    public void testQRCode(){
	    try {
            QRCodeDecoder decoder = new QRCodeDecoder();
            BufferedImage image = ImageIO.read(new File("/Users/alex.zhu/project/qrcode-master/res/430x430.png"));
            String decodedString = new String(decoder.decode(new J2SEImage(image)));
            System.out.println(decodedString);
        }
	    catch(Exception ex){
	        ex.printStackTrace();
        }
    }

    class J2SEImage implements QRCodeImage {
        BufferedImage image;

        public J2SEImage(BufferedImage source) {
            this.image = source;
        }

        public int getWidth() {
            return image.getWidth();
        }

        public int getHeight() {
            return image.getHeight();
        }

        public int getPixel(int x, int y) {
            return image.getRGB(x ,y);

        }
    }
}
