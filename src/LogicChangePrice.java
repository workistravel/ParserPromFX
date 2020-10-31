public class LogicChangePrice {
    protected String logicChangePrice(String price, String typeAdvertising) {
        double markup;


        Double priceIn = Double.valueOf(price);

        if (typeAdvertising.equalsIgnoreCase("CPA")) {
            markup = 1.07;
        } else if (typeAdvertising.equalsIgnoreCase("CPC")) {
            markup = 1.04;
        } else if (typeAdvertising.equalsIgnoreCase("70%")) {
            return String.valueOf(Math.round((priceIn * 1.7)));
        } else if (typeAdvertising.equalsIgnoreCase("90%")) {
            return String.valueOf(Math.round((priceIn * 1.9)));
        } else if (typeAdvertising.equalsIgnoreCase("50%")) {
            return String.valueOf(Math.round((priceIn * 1.5)));
        }else if (typeAdvertising.equalsIgnoreCase("100%")) {
            return String.valueOf(Math.round((priceIn * 2)));
        }else {
            markup = 1.0001;
        }

        if (priceIn <= 20)
            return String.valueOf(Math.round((priceIn * 2) * markup));
        if (priceIn <= 40)
            return String.valueOf(Math.round((priceIn * 1.8) * markup));
        if (priceIn <= 80)
            return String.valueOf(Math.round((priceIn * 1.75) * markup));
        if (priceIn <= 150)
            return String.valueOf(Math.round((priceIn * 1.6) * markup));
        if (priceIn <= 250)
            return String.valueOf(Math.round((priceIn * 1.47) * markup));
        if (priceIn <= 350)
            return String.valueOf(Math.round((priceIn * 1.37) * markup));
        if (priceIn <= 550)
            return String.valueOf(Math.round((priceIn * 1.29) * markup));
        if (priceIn <= 750)
            return String.valueOf(Math.round((priceIn * 1.25) * markup));
        if (priceIn <= 850)
            return String.valueOf(Math.round((priceIn * 1.23) * markup));
        if (priceIn <= 1050)
            return String.valueOf(Math.round((priceIn * 1.21) * markup));
        if (priceIn <= 1250)
            return String.valueOf(Math.round((priceIn * 1.20) * markup));
        if (priceIn <= 1550)
            return String.valueOf(Math.round((priceIn * 1.18) * markup));
        if (priceIn <= 2750)
            return String.valueOf(Math.round((priceIn * 1.16) * markup));
        if (priceIn <= 3250)
            return String.valueOf(Math.round((priceIn * 1.14) * markup));
        if (priceIn <= 4000)
            return String.valueOf(Math.round((priceIn * 1.12) * markup));
        if (priceIn < 5000)
            return String.valueOf(Math.round((priceIn + 400) * markup));
        if (priceIn <= 6000)
            return String.valueOf(Math.round((priceIn + 500) * markup));
        if (priceIn <= 7000)
            return String.valueOf(Math.round((priceIn * 650) * markup));
        if (priceIn > 7000)
            return String.valueOf(Math.round((priceIn * 1.07) * markup));
        else {
            return String.valueOf(priceIn);


        }
    }
}
