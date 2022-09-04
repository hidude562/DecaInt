class DecaInt32 {
    int rawValue;
    final int PRECISION;
    int displayDecimalLen;
    int decimalPlaces;
    final int TO_DECA; // TODO: better naming

    public DecaInt32(int rawValue, int PRECISION) {
        this.rawValue = rawValue * PRECISION;
        this.PRECISION = PRECISION;
        int precision_len_temp = String.valueOf(this.PRECISION).length();
        this.displayDecimalLen = (precision_len_temp + ((precision_len_temp < 2) ? 1 : 0)) + 1;

        int temp = 1;
        for(int i = 0; i < displayDecimalLen; i++) {
            temp*=10;
        }
        this.decimalPlaces = temp;
        this.TO_DECA = decimalPlaces / PRECISION;
    }
    public void changeDisplayPrecision(int precision) { // TODO:
        this.displayDecimalLen = precision;
        int temp = 1;
        for(int i = 0; i < displayDecimalLen - 1; i++) {
            temp*=10;
        }
        this.decimalPlaces = temp;
    }

    public int convertToThisPrecision(DecaInt32 number) {
        int out = number.rawValue;
        if(number.PRECISION != this.PRECISION) {
            out = (number.rawValue * this.PRECISION) / number.PRECISION;
        }
        return out;
    }

    public void addIntRaw(int number) {
        this.rawValue += number;
    }


    // These are the most maintained functions in the program
    // The behavior of these functions set the precision to be the precision of the first num
    // TODO: Add a precision input for this

    public void addDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        num1.rawValue += convertToThisPrecision(num2);
        this.rawValue = num1.rawValue;
    }
    public void subtractDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        num1.rawValue -= convertToThisPrecision(num2);
        this.rawValue = num1.rawValue;
    }
    public void multiplyDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        num1.rawValue *= convertToThisPrecision(num2);
        num1.rawValue /= (this.PRECISION);
        this.rawValue = num1.rawValue;
    }
    public void divideDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        num1.rawValue = (num1.rawValue * this.PRECISION) / convertToThisPrecision(num2);
        this.rawValue = num1.rawValue;
    }
    public void modDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        num1.rawValue = (num1.rawValue) % convertToThisPrecision(num2);
        this.rawValue = num1.rawValue;
    }


    // Converts the decaInt value to whatever
    public String toString() {
        String decimalPoint = String.format("%0"+ ((this.displayDecimalLen - 1)) + "d", Math.abs((this.rawValue % PRECISION) * this.TO_DECA));
        String decaIntToString = String.valueOf((this.rawValue / this.PRECISION)) + "." + decimalPoint;
        return decaIntToString;
    }
    public int toInt() {
        // Doesn't round.
        int decaIntToInt = this.rawValue / this.PRECISION;
        return decaIntToInt;
    }

    // This is the only time this program uses a floating point number.
    public double toDouble() {
        String decimalPoint = String.format("%0"+ ((this.displayDecimalLen - 1)) + "d", Math.abs((this.rawValue % PRECISION) * this.TO_DECA));
        System.out.println( this.TO_DECA);
        double decaIntToDouble = Double.parseDouble(String.valueOf((this.rawValue / this.PRECISION)) + "." + decimalPoint);
        return decaIntToDouble;
    }

    public int returnRawValue() {
        return this.rawValue;
    }
}
