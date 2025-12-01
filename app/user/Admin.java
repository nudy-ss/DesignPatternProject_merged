// src/user/Admin.java
package user;

import Repository.RepositoryManager;
import com.mongodb.client.MongoCollection;
import entity.LectureEntity;
import entity.ReservationEntity;
import entity.ResourceEntity;
import manager.ResourceType;
import reservation.ReservationManager;
import manager.ResourceFileWriter;
import resource.SimpleItem;
import resource.SimpleLectureRoom;

import static entity.db.DBConnection.codecRegistry;
import static entity.db.DBConnection.database;

public class Admin extends User {
  private String adminId;


  public Admin(String studentId, String name, String adminId) {
    super(studentId, name);
    this.adminId = adminId;
  }

  public boolean registerResource(ReservationManager manager, ResourceType type, String name, int deposit) {

    RepositoryManager repositoryManager = RepositoryManager.getInstance();

    switch (type) {
      case LECTURE:
        if (repositoryManager.lectures.findByName(name) != null) {
          return false;
        }
        repositoryManager.lectures.save(new LectureEntity(name, deposit, true));
        manager.addResource(new SimpleLectureRoom(name, deposit));
        return true;

      case ITEM:
        if (repositoryManager.resources.findByName(name) != null) {
          return false;
        }
        repositoryManager.resources.save(new ResourceEntity(name, deposit));
        manager.addResource(new SimpleItem(name, deposit));
        return true;
    }

    return false;
  }



  // [신규] enum 버전(터미널 선택과 궁합 좋음)
  /* public boolean registerResource(ReservationManager manager, ResourceType type, String name, int deposit) {

    boolean saved = ResourceFileWriter.appendResource(type.name(), name, deposit);
    if (!saved) {
      System.out.println("[실패] 동일한 이름의 자원이 이미 존재합니다: " + name);
      return false;
    }

    manager.registerResource(String.valueOf(type), name, deposit);
    System.out.println("[성공] 자원 등록 및 파일 저장 완료!");
    return true;


      RepositoryManager repositoryManager = RepositoryManager.getInstance();
      switch(type){
          case LECTURE:
              System.out.println(type);
              if(repositoryManager.lectures.findByName(name) != null) {
                  repositoryManager.lectures.save(new LectureEntity(name, deposit, true));
                  return true;
              }
            return false;
          case    ITEM:
              System.out.println(type);
              //Mongo DB 저장 코드//
              if(repositoryManager.lectures.findByName(name) != null) {
                  repositoryManager.resources.save(new ResourceEntity(name, deposit));
                    return true;
              }
              return false;
      }

      //*********************수정해야함
      return true;
  }*/


  public void deleteResource(resource.Resource resource) {}
  public void viewAllResources() {

  }
  public void modifyResource(resource.Resource resource) {}


}
