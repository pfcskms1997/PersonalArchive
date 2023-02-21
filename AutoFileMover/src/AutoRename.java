import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AutoRename {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String dir_path = "";

        System.out.print("디렉터리 경로 입력: ");
        dir_path = br.readLine();

        String[] chunks = dir_path.split("\\\\");
        String dir_name = chunks[chunks.length - 1];

        String language = dir_name.split("_")[0];
        String live_num = dir_name.split("_")[1];
        String base_name = String.format("%s_%s", language, live_num);

        File dir = new File(dir_path);
        File[] file_list = dir.listFiles();

        int new_idx = 1;
        int success_cnt = 0;
        for (File frame : file_list) {
            String file_name = frame.getAbsolutePath();
            String new_name = String.format("%s\\%s-%03d.png", dir_path, base_name, new_idx++);

            File new_file_name = new File(new_name);

            if (!frame.renameTo(new_file_name)) {
                System.out.printf("[FAIL] %s\n", file_name);
            } else {
                success_cnt++;
            }
        }

        System.out.printf("전체 %d개 중 %d개 성공\n", file_list.length, success_cnt);
    }
}
