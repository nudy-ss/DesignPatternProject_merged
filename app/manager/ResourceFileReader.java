package manager;

import resource.ReservableResource;
import resource.RentableResource;
import resource.SimpleLectureRoom;
import resource.SimpleItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ResourceFileReader {

  private static final String FILE_NAME = "resources.txt";

  public static List<Object> loadResources() {
    List<Object> list = new ArrayList<>();

    File file = new File(FILE_NAME);
    if (!file.exists()) return list;

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;

      while ((line = br.readLine()) != null) {
        if (line.trim().isEmpty()) continue;

        String[] parts = line.split(",");
        if (parts.length < 3) continue;

        String type = parts[0].trim();
        String name = parts[1].trim();
        int deposit = Integer.parseInt(parts[2].trim());

        if (type.equalsIgnoreCase("LECTURE")) {
          list.add(new SimpleLectureRoom(name, deposit));
        } else if (type.equalsIgnoreCase("ITEM")) {
          list.add(new SimpleItem(name, deposit));
        }
      }

    } catch (IOException e) {
      System.out.println("파일 로드 실패: " + e.getMessage());
    }

    return list;
  }
}

