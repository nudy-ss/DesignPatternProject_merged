package manager;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ResourceFileWriter {

  private static final String FILE_NAME = "resources.txt";
  private static final Set<String> existingNames = new HashSet<>();

  static {
    loadExistingNames();
  }

  private static void loadExistingNames() {
    File file = new File(FILE_NAME);
    if (!file.exists()) return;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        if (!line.trim().isEmpty()) {
          String[] parts = line.split(",");
          existingNames.add(parts[1].trim()); // name 저장
        }
      }
    } catch (IOException e) {
      System.out.println("이름 로드 실패: " + e.getMessage());
    }
  }

  public static boolean appendResource(String type, String name, int deposit) {
    // 중복 검사
    if (existingNames.contains(name)) {
      return false;
    }

    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
      bw.write(type + ", " + name + ", " + deposit);
      bw.newLine();
      existingNames.add(name); // 새 이름 추가
      return true;
    } catch (IOException e) {
      System.out.println("파일 저장 실패: " + e.getMessage());
      return false;
    }
  }
}
