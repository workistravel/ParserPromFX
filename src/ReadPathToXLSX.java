import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ReadPathToXLSX {
      //проверяем содержание файлов в папке и возвращаем пути к ним
    protected List<String> readFolder (String pathToFolder) {
        File file = new File(pathToFolder);
        File[] files =file.listFiles();
        List<String> listPath = new ArrayList<String>();

        for (File f: files) {
            if(f.toString().endsWith(".DS_Store")){// скипаем системный файл mac
                continue;
            }
            listPath.add(f.toString());

        }

        return listPath;
    }
}

