package ftn.isa.sistemapoteka.qrCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class QrCode {

    public static void generateQRcode(String data, Path path, String charset, Map map, int h, int w) throws WriterException, IOException
    {
//the BitMatrix class represents the 2D matrix of bits
//MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToPath(matrix, path.toString().substring(path.toString().lastIndexOf('.') + 1), path);
    }

    public static String readQRcode(String path, String charset, Map map) throws FileNotFoundException, IOException, NotFoundException
    {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result rslt = new MultiFormatReader().decode(binaryBitmap);
        return rslt.getText();
    }
}
