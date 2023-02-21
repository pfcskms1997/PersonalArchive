import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.StringTokenizer;

public class ImageCrop {

    public static class Image {
        private BufferedImage buffer;

        public Image(File file) throws IOException {
            buffer = ImageIO.read(file);
        }

        public Image(BufferedImage buffer) {
            this.buffer = buffer;
        }

        public Image resize(int width, int height) {
            BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dest.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.drawImage(buffer, 0, 0, width, height, null);
            g.dispose();
            return new Image(dest);
        }

        public Image resize(int width) {
            int resizedHeight = (width * buffer.getHeight()) / buffer.getWidth();
            return resize(width, resizedHeight);
        }

        public Image crop(int x, int y, int width, int height) {
            BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dest.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.drawImage(buffer, 0, 0, width, height, x, y, x + width, y + height, null);
            g.dispose();
            return new Image(dest);
        }

        public void writeTo(OutputStream stream, String formatName) throws IOException {
            ImageIO.write(buffer, formatName, stream);
        }

        public boolean isSupportedImageFormat() {
            return buffer != null;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("******* Program Start *******");
        System.out.print("디렉터리 경로 입력: ");
        String dir_path = br.readLine();

        System.out.print("lx ly rx ry를 순서대로 입력: ");
        StringTokenizer coords = new StringTokenizer(br.readLine(), " ");
        int lx = Integer.parseInt(coords.nextToken());
        int ly = Integer.parseInt(coords.nextToken());
        int rx = Integer.parseInt(coords.nextToken());
        int ry = Integer.parseInt(coords.nextToken());
        int width = Math.abs(rx - lx);
        int height = Math.abs(ry - ly);

        long startTime = System.currentTimeMillis();

        File dir = new File(dir_path);
        File[] file_list = dir.listFiles();

        File new_dir = new File(dir_path.replace("_org", ""));
        if(!new_dir.exists()) {
            new_dir.mkdirs();
        }

        System.out.println("***** START IMAGE CROP *****");
        for (File file : file_list) {
            Image image = new Image(file);
            if(!image.isSupportedImageFormat()) {
                System.out.println("not supported image type");
                continue;
            }

            Image croppedImage = image.crop(lx, ly, width, height);
            File new_file = new File(file.getAbsolutePath().replace("_org", ""));
            FileOutputStream fos = new FileOutputStream(new_file);
            croppedImage.writeTo(fos, "png");

            System.out.printf("[SUCCESS] %s\n", new_file.getAbsolutePath());
        }

        long endTime = System.currentTimeMillis();
        long executionTime = (endTime - startTime) / 1000;
        System.out.printf("작업 시간: %d(sec)\n", executionTime);
    }
}
