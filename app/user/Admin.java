// src/user/Admin.java
package user;

import manager.ResourceType;
import reservation.ReservationManager;
import manager.ResourceFileWriter;

public class Admin extends User {
  private String adminId;

  public Admin(String studentId, String name, String adminId) {
    super(studentId, name);
    this.adminId = adminId;
  }

  // 문자열 버전(호환)
  public void registerResource(ReservationManager manager, String type, String name, int deposit) {
    manager.registerResource(type, name, deposit);
  }

  // [신규] enum 버전(터미널 선택과 궁합 좋음)
  public boolean registerResource(ReservationManager manager, ResourceType type, String name, int deposit) {
    boolean saved = ResourceFileWriter.appendResource(type.name(), name, deposit);
    if (!saved) {
      System.out.println("[실패] 동일한 이름의 자원이 이미 존재합니다: " + name);
      return false;
    }

    manager.registerResource(String.valueOf(type), name, deposit);
    System.out.println("[성공] 자원 등록 및 파일 저장 완료!");
    return true;
  }

  public void deleteResource(resource.Resource resource) {}
  public void viewAllResources() {}
  public void modifyResource(resource.Resource resource) {}
}
