import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.HashMap;

public class NumbersColumn {
    public HashMap<String, Integer> readColumn(Row row0){
        HashMap<String, Integer> mapNumbersColumn= new HashMap<String, Integer>();
        mapNumbersColumn.put("code",              -1);
        mapNumbersColumn.put("codeProducer",      -1);
        mapNumbersColumn.put("codeOE",            -1);  //массив кодов
        mapNumbersColumn.put("brandRow",          -1);
        mapNumbersColumn.put("nameRow",           -1);
        mapNumbersColumn.put("priceRow",          -1);
        mapNumbersColumn.put("presenceRow",       -1);
        mapNumbersColumn.put("typeAdvertisingRow",-1);
        mapNumbersColumn.put("quantityRow",        -1);
        mapNumbersColumn.put("currencyRow",       -1);
        mapNumbersColumn.put("idRow",             -1);
        mapNumbersColumn.put("countryRow",        -1);
        mapNumbersColumn.put("descriptionRow",    -1);
        mapNumbersColumn.put("compatibilityRow",  -1);


        for (int c = 0; c < row0.getLastCellNum(); c++) {
            Cell cellName = row0.getCell(c);
            if(cellName.getStringCellValue().trim().equalsIgnoreCase("Валюта")) {
                mapNumbersColumn.put("currencyRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("Название_Характеристики")) {     //нулевая колонка для характеристик
                mapNumbersColumn.put("compatibilityRow", c );
                break;
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("Единица_измерения")) {
                mapNumbersColumn.put("quantityRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("Описание")) {                    // HTML страница описания
                mapNumbersColumn.put("descriptionRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("Уникальный_идентификатор")) {    // код прома
                mapNumbersColumn.put("idRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("code1") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Код_товара")){
                mapNumbersColumn.put("code", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("code2") ){
                mapNumbersColumn.put("codeProducer", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("code3")){
                mapNumbersColumn.put("codeOE", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("brand") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Производитель")){
                mapNumbersColumn.put("brandRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("Страна_производитель") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("country")){
                mapNumbersColumn.put("countryRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("name")||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Название_позиции")) {
                mapNumbersColumn.put("nameRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("price") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Цена")){
                mapNumbersColumn.put("priceRow", c );
            }else  if(cellName.getStringCellValue().trim().equalsIgnoreCase("presence") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Наличие")){
                mapNumbersColumn.put("presenceRow", c );
            }else if(cellName.getStringCellValue().trim().equalsIgnoreCase("typeAdvertising") ||
                    cellName.getStringCellValue().trim().equalsIgnoreCase("Личные_заметки")){
               mapNumbersColumn.put("typeAdvertisingRow", c );
            }
        }
        return mapNumbersColumn;

    }
}
