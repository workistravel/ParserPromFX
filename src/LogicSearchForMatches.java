import java.util.*;
import java.util.stream.Collectors;


public class LogicSearchForMatches {
    protected List<myRow> searchMatchers(HashMap<String, List> catalog) {
        LogicChangePrice lcp = new LogicChangePrice();


       List<myRow> prom = catalog.remove("PROM");


        for (int p = 0; p < prom.size(); p++) {

            if (entityIsCheckig(prom.get(p).getCode())) {  //проверка нужно ли проверять этот код (скипает других поставщиков)
                continue;
            }

            String code = prefixChecking(prom.get(p).getCode());                // код по прому первый
            String codeProducer = prom.get(p).getCodeProducer();                // код производителя
            List<String> listCodeOE = new ArrayList<>(prom.get(p).getCodeOE()); // коллекция OE кодов



            List<myRow> tempList=new ArrayList<>();                            //коллкция в которой идет поиск
            List<myRow> findList = new ArrayList<>();                          //куда ложим совпадения


            for (String key : catalog.keySet()) {
                tempList.addAll(catalog.get(key));
                    for (int j = 0; j < tempList.size(); j++) {

                        if (codeComparison(code, tempList.get(j).getCodeOE()) ||                   //  первый Прома в списке кодов
                                    codeComparison(codeProducer , tempList.get(j).getCodeOE()) ||  //  Код_товара  в списке кодов
                                        codeComparisons( listCodeOE , tempList.get(j).getCodeOE())) {


                                            String tempBrand = String.valueOf(prom.get(p).getCompatibility().get("Producer"));


                            if ( tempBrand.equalsIgnoreCase(tempList.get(j).getBrand()) ||
                                     prom.get(p).getBrand().equalsIgnoreCase(tempList.get(j).getBrand()) ||
                                         tryFindStringInName(tempBrand, tempList.get(j).getName())) {

                                tempList.get(j).setId(key);      // запись названия каталога где нашли
                                // запись карточки которую нашли в лист
                                if(!tempList.isEmpty()){

                                    findList.add(tempList.get(j));
                                }

                            }
                        }
                    }
                tempList.clear();
            }

            if (findList.isEmpty()) {

                prom.get(p).setPresence("-");
                MyLogger.logger(prom.get(p).getCode() + " " + prom.get(p).getName() + " NOT FOUND ");
                

            }  else {
//----------------------------------------------------------------------------------------------------------------------


                Collections.sort(findList);     //сортируем по цене

                String keyWord = String.valueOf(prom.get(p).getCompatibility().get("Key word"));
                if(!String.valueOf(prom.get(p).getCompatibility().get("Key word")).equalsIgnoreCase("none")){
                    List<myRow> temp =  findList.stream()
                            .filter(t -> tryFindStringInName( keyWord , t.getName()))
                            .collect(Collectors.toCollection(ArrayList::new));
                    findList=temp;
                    Collections.sort(findList);
                }


                int numberList =  isSetNewPrice( prom.get(p) , findList);

                if(numberList!=-1){
                    prom.get(p).setPrice(lcp.logicChangePrice(findList.get(numberList).getPrice(), prom.get(p).getTypeAdvertising()));
                }else{
                    prom.get(p).setPresence("-");
                    MyLogger.logger(prom.get(p).getCode() + " " + prom.get(p).getName() + " NOT FOUND ");
                    findList.clear();
                    continue;
                }


                // меняем производителя из нового найденного поставщика для характеристик
                if (!findList.get(numberList).getBrand().equalsIgnoreCase("NO BRAND")) {
                    prom.get(p).getCompatibility().put("Producer", findList.get(numberList).getBrand());



                }

                //меняем код для найденного элемента и используем префикс из Id
                prom.get(p).setCode(findList.get(numberList).getId() + " " + findList.get(numberList).getCode());



                // prom.get(p).getCompatibility().put("Страна производитель",prom.get(p).getCountry() );



                prom.get(p).setPresence("!");



                findList.clear();
            }
        }
        return prom;
    }

    private int isSetNewPrice(myRow prom, List<myRow> findList) {

        double oldPrice = Double.parseDouble(prom.getPrice());
        double setPrice;
        double riceInPrice;

        LogicChangePrice lcp = new LogicChangePrice();
        for (int i = 0; i < findList.size(); i++) {
            boolean flag= false;
            setPrice = Double.parseDouble(lcp.logicChangePrice(findList.get(i).getPrice(), prom.getTypeAdvertising()));
            if (oldPrice > setPrice) {
                riceInPrice = ((oldPrice * 100) / setPrice) - 100;
                if (riceInPrice > 30) {
                    flag = true;    //новая цена дешевле 30%
                }
            }
            if (oldPrice < setPrice) {
                riceInPrice = ((setPrice * 100) / oldPrice) - 100;
                if (riceInPrice > 40) {
                    flag = true;  //  новая цена дороже 40%

                }
            }

            if(!flag){
                return i;
            }

        }
        MyLogger.logger(prom.getCode() + " " + prom.getName() + "  CHECK PRICE & PRODUCER ");
        return -1;


    }

    private boolean tryFoundFirstWord(String what, String where) {
        if(what!=null  && where!=null) {
            String[] tmp = what.split(" ");
            String word = tmp[0];
            String[] whereArr = where.split(" ");
            for (String s : whereArr) {
                if (s.equalsIgnoreCase(word) ) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean entityIsCheckig(String cod) {
        if (cod != null) {
            if (cod.startsWith("IC") ||
                    cod.startsWith("INC") ||
                    cod.startsWith("GAS") ||
                    cod.startsWith("NO") ||
                    cod.startsWith("NON") ||
                    cod.startsWith("POL") ||
                    cod.startsWith("комплект") ||
                    cod.startsWith("VAR") ||
                    cod.startsWith("PL") ||
                    cod.startsWith("LSA")) {
                return true;
            }
        }
        return false;

    }

    //поиск бренда в названии позиции
    private boolean tryFindStringInName(String nameProm, String nameFromCatalog) {
        if (nameFromCatalog != null && nameProm != null) {
            String[] tmpCatalog = nameFromCatalog.split("[- ]" );

           List<String> tmp =  Arrays.stream(tmpCatalog)
                    .map(s->s.replace(")", ""))
                    .map(s->s.replace("(", ""))
                    .map(s->s.replace("-", " "))
                    .map(s->s.replace("\"", ""))
//                    .map(s->s.replace(".", ","))
                    .collect(Collectors.toList());


            for (String word : tmp) {
                if (nameProm.equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } else {
            return false;
        }
        return false;
    }

    //проверка и удаление префикса если есть
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

    private boolean codeComparison(String code, List<String> list) {

        if(code.equalsIgnoreCase("none")){
            return false;
        }
        if(list.size()<1  || list.get(0).equalsIgnoreCase("none")){
            return false;
        }
        List<String> tmpListForList = new ArrayList<>(list);

        for (String l: tmpListForList) {
           if(l.equalsIgnoreCase(code)){
               return true;
           }
        }

        return false;
    }
    private boolean codeComparisons(List<String> listCodeOE, List<String> codeOE) {
        if(listCodeOE.get(0).equalsIgnoreCase("none")){
            return false;
        }
        for ( String tempProm: listCodeOE ) {
            for(String tempCatalog: codeOE ){
                if (tempProm.equalsIgnoreCase(tempCatalog)){
                    return true;
                }
            }
        }
        return false;
    }

}
