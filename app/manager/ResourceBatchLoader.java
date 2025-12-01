package manager;

import reservation.ReservationManager;
import ui.Main.LoginPanel;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** 텍스트 파일로부터 자원(강의실/물품) 일괄 등록 */
public class ResourceBatchLoader {

  /**
   * 파일을 읽어 한 줄씩 파싱하여 ReservationManager에 등록한다.
   * 포맷: TYPE, NAME, DEPOSIT
   * 예:   LECTURE, Room-101, 50000
   */
  public static void load(Path filePath, ReservationManager manager) throws IOException {
    try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
      String line;
      int lineNo = 0;
      while ((line = br.readLine()) != null) {
        lineNo++;
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) continue; // 빈 줄/주석 건너뛰기

        String[] parts = line.split(",");
        if (parts.length < 3) {
          System.out.printf("[경고] %d행 형식 오류: %s%n", lineNo, line);
          continue;
        }

        String typeStr = parts[0].trim();
        String name    = parts[1].trim();
        String depStr  = parts[2].trim();

        int deposit;
        try {
          deposit = Integer.parseInt(depStr);
        } catch (NumberFormatException e) {
          System.out.printf("[경고] %d행 보증금 숫자 아님: %s%n", lineNo, depStr);
          continue;
        }

        // 문자열 오버로드 그대로 활용 (내부에서 enum 변환)
        try {

          ResourceType type = ResourceType.valueOf(typeStr.toUpperCase());

          // 관리자 등록 메서드 호출
          LoginPanel.currentAdmin.registerResource(
              manager,
              type,
              name,
              deposit
          );

          System.out.printf("[OK] %d행 등록: %s, %s, %d%n",
              lineNo, typeStr, name, deposit);

        } catch (IllegalArgumentException e) {
          // typeStr이 LECTURE/ITEM이 아닐 때
          System.out.printf("[경고] %d행 TYPE 오류: %s (LECTURE/ITEM만 허용)%n",
              lineNo, typeStr);

        } catch (Exception e) {
          System.out.printf("[오류] %d행 처리 실패: %s%n",
              lineNo, e.getMessage());
        }

      }
    }
  }
}
