package auth;

import java.util.Set;

/** 간단한 역할 판별 서비스: 학번(studentId)로 Admin 여부 판단 */
public class RoleService {
  // 데모용: 여기 학번들을 관리자 목록으로 취급
  private static final Set<String> ADMIN_IDS = Set.of(
      "99999999"// 필요에 따라 추가
  );

  /** 학번이 관리자 목록에 있으면 true */
  public boolean isAdmin(String studentId) {
    return ADMIN_IDS.contains(studentId);
  }
}
