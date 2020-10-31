import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WriteToXLSX {
    public void writeXLSX   (List<myRow> list){
        if(!list.isEmpty()){
            list.add(0, new myRow());
            SXSSFWorkbook wb = new SXSSFWorkbook(100);

            List<String> listCharacteristics = getCharacters(list);

            Sheet sheet = wb.createSheet();
            for(int rownum=0; rownum<list.size(); rownum++){

                Row row = sheet.createRow(rownum); //создаем строку

                for (int cellnum=0; cellnum<12;cellnum++){
                    Cell cell = row.createCell(cellnum);
                    if(cellnum==0){
                        if(rownum==0){
                            cell.setCellValue("Код_товара");//Код_товара
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getCode());

                        }

                    }

                    if(cellnum==1){
                        if(rownum==0){
                            cell.setCellValue("Название_позиции");//Название_позиции
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getName());
                            continue;
                        }

                    }
                    if(cellnum==2){
                        if(rownum==0){
                            cell.setCellValue("Описание");
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getDescription());
                            continue;
                        }

                    }
                    if(cellnum==3){
                        if(rownum==0){
                            cell.setCellValue("Цена");//Цена
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getPrice());

                        }
                    }
                    if(cellnum==4){
                        if(rownum==0){
                            cell.setCellValue("Валюта");//Валюта
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getCurrency());
                            continue;
                        }
                    }
                    if(cellnum==5){
                        if(rownum==0){
                            cell.setCellValue("Еденица_измерения");//Единица_измерения
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getQuantity());
                            continue;
                        }
                    }
                    if(cellnum==6){
                        if(rownum==0){
                            cell.setCellValue("Наличие");//Наличие
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getPresence());
                            continue;
                        }
                    }
                    if(cellnum==7){
                        if(rownum==0){
                            cell.setCellValue("Уникальный_идентификатор");//Уникальный_идентификатор
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getId());
                            continue;
                        }
                    }
                    if(cellnum==8){
                        if(rownum==0){
                            cell.setCellValue("Производитель");//Производитель
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getBrand());
                            continue;
                        }
                    }
                    if(cellnum==9){
                        if(rownum==0){
                            cell.setCellValue("Страна_производитель");//Страна производитель
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getCountry());
                            continue;
                        }
                    }

                    if(cellnum==10){
                        if(rownum==0){
                            cell.setCellValue("Личные_заметки");//Личные_заметки
                            continue;
                        }else{
                            cell.setCellValue(list.get(rownum).getTypeAdvertising());
                            continue;
                        }
                    }


                  if(cellnum==11){

                       if(rownum==0){
                           int tmpCellumn= cellnum;
                           for (int i = 0; i < listCharacteristics.size(); i++) {
                               row.createCell(tmpCellumn++).setCellValue("Название_Характеристики");
                               row.createCell(tmpCellumn++).setCellValue("Измерение_Характеристики");
                               row.createCell(tmpCellumn++).setCellValue("Значение_Характеристики");

                           }

                        }else{
                           int tmpCell= cellnum;

                           HashMap<String, String> myMap1 = list.get(rownum).getCompatibility(); // наш map

                           for (int i = 0; i < listCharacteristics.size() ; i++) {

                              if(myMap1.containsKey(listCharacteristics.get(i))){
                                  row.createCell(tmpCell++).setCellValue((listCharacteristics.get(i)));
                                  row.createCell(tmpCell++).setCellValue("");
                                  row.createCell(tmpCell++).setCellValue(myMap1.get(listCharacteristics.get(i)));
                              }else {
                                  row.createCell(tmpCell++).setCellValue("");
                                  row.createCell(tmpCell++).setCellValue("");
                                  row.createCell(tmpCell++).setCellValue("");
                              }

                           }
                        }
                  }
                }

            }
            FileOutputStream out;
            try{
                out= new FileOutputStream(Controller.saveRes + Controller.separ +"PromImport.xlsx");
                wb.write(out);
                out.close();
            }catch (
                    IOException e){
                e.printStackTrace();
            }
            wb.dispose();
        }

    }
    protected List<String> getCharacters(List<myRow> list){
            int maxValueForCompatibility = 0;
            int numberPromRow = 0;
            List<String> listCharacteristics;
            for (int l = 1; l < list.size(); l++) {
                int size = list.get(l).getCompatibility().size();

                if (size > maxValueForCompatibility) {
                    maxValueForCompatibility = size;
                    numberPromRow = l;
                }
            }

            HashMap myMap = list.get(numberPromRow).getCompatibility();
            listCharacteristics = new ArrayList(myMap.keySet());
            Collections.sort(listCharacteristics);
            return listCharacteristics;
    }



}
