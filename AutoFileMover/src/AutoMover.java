import java.io.*;

public class AutoMover {

    public static String base_path = "C:\\Users\\pfcsk\\Desktop\\ebook";
    public static String category_dir;
    public static String dir_name;
    public static String base_file_name;
    public static int last_num;
    public static String ext = ".png";

    public static void main(String[] args) throws Exception {
        inputBaseInfo();
        moveFiles();
    }

    public static void inputBaseInfo() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("*** INPUT FOR ORIGIN TARGET ***");

        System.out.print("카테고리(언어/프레임워크) 디렉터리 이름 입력: ");
        category_dir = br.readLine();

        System.out.print("디렉터리 이름 입력: ");
        dir_name = br.readLine();

        System.out.print("파일 기본 이름 입력: ");
        base_file_name = br.readLine();

        System.out.print("마지막 파일 번호 입력: ");
        last_num = Integer.parseInt(br.readLine());

        br.close();
    }

    public static void moveFiles() throws IOException {
        int total_page = 0;

        // 1. 타겟 디렉터리가 존재하지 않을 경우 생성
        File target_directory = new File(String.format("%s\\%s\\%s", base_path, category_dir, dir_name));
        if (!target_directory.exists()) {
            target_directory.mkdirs();
        }

        // 2. 프레임 수(29)만큼 건너뛰며 파일 복사
        FileInputStream fis = null;
        FileOutputStream fos = null;
        for (int i = 5; i <= last_num; i += 10) {
            File original_file = new File(String.format("%s_pre\\%s\\%s\\%s-%05d%s", base_path, category_dir, dir_name, base_file_name, i, ext));
            File dest_file = new File(String.format("%s\\%s\\%s\\%s-%05d%s", base_path, category_dir, dir_name, base_file_name, i, ext));

            try {
                fis = new FileInputStream(original_file);
                fos = new FileOutputStream(dest_file);

                int nRealByte = 0;
                while ((nRealByte = fis.read()) != -1) {
                    fos.write(nRealByte);
                }
            } catch (Exception e) {
                System.out.printf("[FAIL] %s\n", original_file.getName());
            }
            System.out.printf("[SUCCESS] %s\n", dest_file.getName());
            total_page++;
        }

        fis.close();
        fos.close();

        System.out.printf("총 [%d] pages 복사 완료", total_page);
    }
}
