import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class myRow implements Comparable<myRow> {
    private String code;
    private String codeProducer;
    private List<String> codeOE;
    private String quantity;
    private String id;
    private HashMap compatibility;
    private String description;
    private String currency;
    private String brand;
    private String country;
    private String name;
    private String price;
    private String presence;
    private String typeAdvertising;

    public myRow() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public HashMap getCompatibility() {
        return compatibility;
    }

    public void setCompatibility(HashMap compatibility) {
        this.compatibility = compatibility;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCodeProducer() {
        return codeProducer;
    }

    public void setCodeProducer(String codeProducer) {
        this.codeProducer = codeProducer;
    }

    public List<String> getCodeOE() {
        return codeOE;
    }

    public void setCodeOE(List<String> codeOE) {
        this.codeOE = codeOE;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public String getTypeAdvertising() {
        return typeAdvertising;
    }

    public void setTypeAdvertising(String typeAdvertising) {
        this.typeAdvertising = typeAdvertising;
    }


    @Override
    public String toString() {
        return "myRow{" +
                "code1='" + code + '\'' +
                "codeProducer='" + codeProducer + '\'' +
                ", price='" + price + '\'' +
                ", country='" + country + '\'' +
                ", brand='" + brand + '\'' +
                ", id='" + id + '\'' +
                ", compatibility=" + compatibility +
                ", name='" + name + '\'' +



                '}';
    }

    public String toStringOE(){
        StringBuffer str= new StringBuffer();
        for (String s: codeOE) {
            str.append(s+"; ");
        }

        return str.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        myRow myRow = (myRow) o;
        return Objects.equals(code, myRow.code) &&
                Objects.equals(brand, myRow.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, brand);
    }

    @Override
    public int compareTo(myRow o) {
        if(this.getPrice()==o.getPrice()){
            return 0;
        }else  if(Double.parseDouble(this.getPrice())< Double.parseDouble(o.getPrice())){
               return -1;
            }else {
            return 1;
        }

    }

 
}
