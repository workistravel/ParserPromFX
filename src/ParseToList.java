import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class ParseToList {
     protected List<myRow> parceFileXLSX (String filePath){
        List<myRow> rows = new ArrayList<>();
        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);
            int rowStart = sheet.getFirstRowNum();
            int rowEnd = sheet.getLastRowNum() + 1;
            Row row0 = sheet.getRow(0);

            HashMap<String, Integer> mapNumbersColumn;   //список колонок названий
            NumbersColumn nc = new NumbersColumn();
            mapNumbersColumn = nc.readColumn(row0);


            for (int i = rowStart + 1; i < rowEnd; i++) {
                Row row = sheet.getRow(i);
                myRow myRow = new myRow();
                String answer = "";
                HashMap<String, String> characteristicsMap;
                List<String> tmpForCodeOE = new ArrayList<>();


//--------------------------------------------------------------
//CODE колонка с первым кодом присутствует всегда


                answer = getStringCell(row, "code", mapNumbersColumn);

                if (answer.equalsIgnoreCase("NO VALUE")) {
                    continue;
                } else {
                    myRow.setCode(answer);

                }
//---------------------------------------------------------------------
// характеристики


                characteristicsMap= getMapCharacteristics(row, "compatibilityRow", mapNumbersColumn);


//codeProducer есть в прайсах как одиночный код и вторым вариантом берем оригинал кода производителя "Код запчасти" из прома


                if(mapNumbersColumn.get("codeProducer")!=-1){
                    answer = getStringCell(row, "codeProducer", mapNumbersColumn);

                    if (!answer.equalsIgnoreCase("NO VALUE")) {
                        myRow.setCodeProducer(vazChecking(answer));
                    }else{
                        myRow.setCodeProducer("NO VALUE");
                    }
                }else if (characteristicsMap.containsKey("Код запчасти") && !characteristicsMap.containsKey("EMPTY")) {
                        myRow.setCodeProducer(characteristicsMap.get("Код запчасти"));
                 }else {
                    myRow.setCodeProducer("NO VALUE");
                }




//----------------------------------------------------------------
//codeOE


                if (mapNumbersColumn.get("codeOE") != -1) {
                    answer = getStringCell(row, "codeOE", mapNumbersColumn);
                    if (!answer.equalsIgnoreCase("NO VALUE")) {
                        tmpForCodeOE=separateCode(answer);
                        tmpForCodeOE.add(0,myRow.getCode());

                            if(!myRow.getCodeProducer().equalsIgnoreCase("NO VALUE")){
                                tmpForCodeOE.add(1, myRow.getCodeProducer());
                            }
                    }
                } else if(characteristicsMap.containsKey("Код OE")){
                        tmpForCodeOE=separateCode(characteristicsMap.get("Код OE"));
                    }
                    if(tmpForCodeOE.size()>0){
                        myRow.setCodeOE(tmpForCodeOE);
                    }else{
                        tmpForCodeOE.add(0,myRow.getCode());
                        if(!myRow.getCodeProducer().equalsIgnoreCase("NO VALUE")){
                            tmpForCodeOE.add(1, myRow.getCodeProducer());
                        }
                        myRow.setCodeOE(tmpForCodeOE);
                    }




//---------------------------------------------------------------------
//ID
                answer = getStringCell(row, "idRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setId("NO ID");
                }else {
                    myRow.setId(answer);
                }

//---------------------------------------------------------------------
// QUANTITY количество
            answer = getStringCell(row, "quantityRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setQuantity("NO QUANTITY");
                }else {
                    myRow.setQuantity(answer);
                }

//---------------------------------------------------------------------
//BRAND


                answer = getStringCell(row, "brandRow", mapNumbersColumn);

                        if(!answer.equalsIgnoreCase("NO VALUE")) {
                             answer=changeNameBrand(answer);
                             myRow.setBrand(answer);
                        }else {
                            myRow.setBrand("NO BRAND");
                        }
              if(myRow.getBrand().equalsIgnoreCase("NO BRAND")) {
                  if (characteristicsMap.containsKey("Producer") && !characteristicsMap.containsKey("EMPTY")) {
                      myRow.setBrand(characteristicsMap.get("Producer"));
                  }
              }
              if(!characteristicsMap.containsKey("EMPTY") && !characteristicsMap.containsKey("Producer")){
                  characteristicsMap.put("Producer", answer);
              }

//---------------------------------------------------------------------
//description
                answer = getStringCell(row, "descriptionRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setDescription("NO DESCRIPTION");
                }else{
                    myRow.setDescription(answer);
                }

//---------------------------------------------------------------------
// country
                answer = getStringCell(row, "countryRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setCountry("NO COUNTRY");
                }else{
                    myRow.setCountry(answer);
                }

                if(!characteristicsMap.containsKey("Country") && !characteristicsMap.containsKey("EMPTY")){
                       characteristicsMap.put("Country", myRow.getCountry());
                }


//---------------------------------------------------------------------
// CURRENCY тип валюты

                answer = getStringCell(row, "currencyRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setCurrency("NO CURRENCY");
                }else{
                    myRow.setCurrency(answer);
                }

//---------------------------------------------------------------------
//NAME
                answer = getStringCell(row, "nameRow", mapNumbersColumn);
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setName("NO NAME");
                }else{
                    myRow.setName(answer);
                }


//---------------------------------------------------------------------
//PRICE
                answer = getStringCell(row, "priceRow", mapNumbersColumn);
                double priceNumeric;
                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setPrice("NO PRICE");
                }else{
                    if(filePath.contains("ROS" ) ||filePath.contains("ZAH")){
                                  priceNumeric= Double.parseDouble(answer);
                                  priceNumeric=priceNumeric/1.03;        // снижение цены для определенных прайсов
                                  answer= String.valueOf(priceNumeric);
                                  myRow.setPrice(answer);

                    } myRow.setPrice(answer);
                }

//---------------------------------------------------------------------
//PRESENCE
                answer = getStringCell(row, "presenceRow", mapNumbersColumn);

                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setPresence("!");
                }else {
                    myRow.setPresence(answer);
                }

//---------------------------------------------------------------------
//ADVERTISING
                answer = getStringCell(row, "typeAdvertisingRow", mapNumbersColumn);

                if(answer.equalsIgnoreCase("NO VALUE")){
                    myRow.setTypeAdvertising("1");
                }else {
                    myRow.setTypeAdvertising(answer);
                }

// финализируем
                myRow.setCompatibility(characteristicsMap);
                rows.add(myRow);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }





    private String getStringCell(Row row, String nameKey, HashMap<String, Integer> map){

        String answer = "NO VALUE";
        if (map.get(nameKey) == -1) {
            return answer = "NO VALUE";
        }
            if (row.getCell(map.get(nameKey)) == null) {
                return answer;
            }
            if (row.getCell(map.get(nameKey)).getCellTypeEnum() != CellType.NUMERIC) {
                if (row.getCell(map.get(nameKey)).getStringCellValue().trim().isEmpty()) { // пропускаем если клетка содержит только пробел
                    return "NO VALUE";
                }
            } else {
                if (row.getCell(map.get(nameKey)).getNumericCellValue() == ' ') {
                    return "NO VALUE";
                }
            }
            switch (row.getCell(map.get(nameKey)).getCellTypeEnum()){
                case NUMERIC:
                    answer = String.valueOf(Long.valueOf((long) row.getCell(map.get(nameKey)).getNumericCellValue()));
                    break;

                case STRING:
                    answer = row.getCell(map.get(nameKey)).getStringCellValue().replaceAll("[\\s\\u00A0]+$", "").trim();
                     break;
            }
            if (answer.equals("")) {
                return "NO VALUE";
            }
            return answer;
        }
    private HashMap<String, String> getMapCharacteristics (Row row, String nameKey, HashMap<String, Integer> map) {
            HashMap<String, String> answerMap = new HashMap<String, String>();
            if (map.get(nameKey) == -1) {
                answerMap.put("EMPTY", "EMPTY");
                return answerMap;
            }
            if (row.getCell(map.get(nameKey)) == null) {
                answerMap.put("EMPTY", "EMPTY");
                return answerMap;
            } else {

                int tempCompatibilityRow = map.get(nameKey);

                while (row.getCell(tempCompatibilityRow) != null) {
                    String value = "";

                    String key = row.getCell(tempCompatibilityRow).getStringCellValue().replaceAll("[\\s\\u00A0]+$", "").trim();
                    if(key==""){
                        break;
                    }
                    switch (row.getCell(tempCompatibilityRow + 2).getCellTypeEnum()) {

                        case NUMERIC:
                            value = String.valueOf( Long.valueOf( (long) row.getCell( tempCompatibilityRow + 2 ).getNumericCellValue() ) );
                            break;
                        case STRING:
                            value = row.getCell(tempCompatibilityRow + 2).getStringCellValue().replaceAll("[\\s\\u00A0]+$", "").trim();
                    }
                    if (key.equalsIgnoreCase("Совместимость с:")) {
                        key = "Совместимость";
                    }
                    if (key.equalsIgnoreCase("Producer")) {
                        if(value.equalsIgnoreCase("GENERAL MOTORS")){
                            value="GM";
                        }
                    }
                    if(key.equalsIgnoreCase("Producer")){
                        if(value.equalsIgnoreCase("Luzar-fgn")) {
                            value="Luzar";
                        }
                    }
                    if(key.equalsIgnoreCase( "Producer" )){
                         if(value.equalsIgnoreCase("ВИС"  )
                               || value.equalsIgnoreCase("ВАЗ")
                                 || value.equalsIgnoreCase("ДААЗ")
                                   || value.equalsIgnoreCase("VIS")
                                      || value.equalsIgnoreCase("АвтоВАЗ (ОАТ, ВИС)")
                                         || value.equalsIgnoreCase("Димитровградский автоагрегатный завод")
                                            || value.equalsIgnoreCase("АвтоВАЗ (ОАТ, ДААЗ)")){
                           value="АвтоВАЗ";

                       }
                    }

                    answerMap.put(key, value);
                    tempCompatibilityRow += 3;
                    if (row.getCell(tempCompatibilityRow) == null) {
                        break;
                    }

                }
                return answerMap;
            }

        }
    private List<String> separateCode(String str) {
        List<String> separatedList = new ArrayList<String >();
             str= str.replaceAll("\\\\", ";");
            String[] strCodes = str.split(";|,|/");
            for (String s : strCodes) {
                if (s.length() < 4) {
                    continue;
                }
                s = s.trim();
                if(!separatedList.isEmpty()){
                    if(separatedList.contains(s)){
                        continue;
                    }
                }
                s=vazChecking(s);
                s=prefixChecking(s);
                separatedList.add(s);
                if(separatedList.size()==30){
                    break;
                }

            }
            if(separatedList.isEmpty()){
                separatedList.add("NO CODE");
            }
            return separatedList;


    }
    private String prefixChecking(String str) {
        String[] listStrTriple = {"ROS", "ZAD", "ZAH", "ATR"};
        String[] listStrDouble = {"IC", "ZA"};
        String tmp = "";
        for (String list : listStrTriple) {
            if (str.startsWith(list)) {
                tmp = str.substring(3).trim();
                break;
            }

        }
        if (tmp.equals("")) {
            for (String list : listStrDouble) {
                if (str.startsWith(list)) {
                    tmp = str.substring(2).trim();
                    break;
                }

            }
        }

        if (!tmp.equals("")) {
            str = prefixChecking(tmp);
            return str;
        }
        return str;
    }
    private String vazChecking(String code) { // проверка кода на предмет что он ВАЗ-овский
        if (code == null || code.length()<12) {
            return code;
        }
        String temp = "";
        if (code.contains("-")) {
            String[] mas = code.split("-");
            mas[1]= mas[1].replace("/", "");
            if (mas[0].length() <= 5 && mas[0].length() > 3 && mas[0].matches("\\d+") && mas[1].matches("\\d+")) {

                int lengSecondPice = mas[1].length();
                if (lengSecondPice < 7) {
                    lengSecondPice = mas[1].length();
                } else {
                    lengSecondPice = 7;
                }
                temp = mas[0] + "-" + mas[1].substring(0, lengSecondPice);
                if(mas.length==3  ){
                   if( mas[2].matches("\\d+")){
                       if(mas[2].length()>1){
                           temp = temp + "-" + mas[2].substring(0, 2);
                       }

                   }
                }
                ;
            }
        }
        if (temp.isEmpty()) {
            return code;
        }
        return temp;
    }
    private String changeNameBrand(String oldBrand){

        if(oldBrand.equalsIgnoreCase("ВИС") ){
            return "АвтоВАЗ";
        }
        if(oldBrand.equalsIgnoreCase("ВАЗ") ){
            return "АвтоВАЗ";
        }
        if( oldBrand.equalsIgnoreCase("VIS")){
            return "АвтоВАЗ";
        }
        if( oldBrand.equalsIgnoreCase("ДААЗ")){
            return "АвтоВАЗ";
        }
        if(oldBrand.equalsIgnoreCase("ПАО \"БРТ\"")){
            return "БРТ";
        }
        if(oldBrand.equalsIgnoreCase("Luzar-fgn")){
            return "Luzar";
        }
        if (oldBrand.equalsIgnoreCase("DK") || oldBrand.equalsIgnoreCase("Дорожная Карта")) {
            return "ДК";
        }
        if (oldBrand.equalsIgnoreCase("Victor Reinz")) {
            return "VICTOR-REINZ";
        }else
        if (oldBrand.equalsIgnoreCase("K&S")) {
            return "KS";
        }
        if (oldBrand.equalsIgnoreCase("Лузар")) {
            return "Luzar";
        }
        if (oldBrand.equalsIgnoreCase("АвтоВАЗ (ОАТ, ДААЗ)")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("АвтоВАЗ (ОАТ, ДЗА)")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("АвтоВАЗ (ОАТ, ДААЗ Штамп)")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("АвтоВАЗ (Лада-Имидж)")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("АвтоВАЗ (ОАТ, ВИС)")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("Димитровградский автоагрегатный завод")) {
            return "АвтоВАЗ";
        }
        if (oldBrand.equalsIgnoreCase("Iran radiator CO")) {
            return "Иран";
        }
        if (oldBrand.equalsIgnoreCase("Мастер М")) {
            return "Мастер-М";
        }
        if (oldBrand.equalsIgnoreCase("Автопартнёр")) {
            return "Автопартнер";
        }
        if (oldBrand.equalsIgnoreCase("GENERAL MOTORS")) {
            return "GM";
        }



        return oldBrand;
    }

}
