import java.util.HashMap;
import java.util.List;


public class ParseFolder {

   public HashMap<String, List> parceFolder (String path){

        ReadPathToXLSX rp = new ReadPathToXLSX();

        List<myRow> prom, nextCatalog;

        HashMap<String, List> catalog = new HashMap<>();
        List<String> pathToCatalog = rp.readFolder(path);

        for (String s : pathToCatalog) {
            List<String> folders = rp.readFolder(s);
            if (s.endsWith("catalog") && folders.size() == 0) {
                MyLogger.logger(s + " EMPTY!!!");
                break;
            }
            if (s.endsWith("prom") && folders.size() == 0) {
                MyLogger.logger(s + " EMPTY!!!");
                break;

            }

            for (int j = 0; j < folders.size(); j++) {
                ParseToList ptl = new ParseToList();
                if (folders.get(j).contains("prom")) {
                    prom = ptl.parceFileXLSX(folders.get(j));
                    catalog.put("PROM", prom);


                }
                if (folders.get(j).contains("catalog")) {
                    String[]str= folders.get(j).split("catalog");
                    String temp=str[1].substring(1,4);

                    nextCatalog = ptl.parceFileXLSX(folders.get(j));
                    catalog.put(temp,nextCatalog);

                }

            }

        }

      return catalog;
    }


}
