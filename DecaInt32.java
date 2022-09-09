class DecaInt32 {
    int rawValue;
    final int PRECISION;
    int displayDecimalLen;
    int decimalPlaces;
    final int TO_DECA; // TODO: better naming
    
    // TODO: Auto precision, ex: 0.25 input = precision 4, mod of input, then 1 / input?

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

    // Returns the raw value of the number as the object's precision.
    public int convertToThisPrecision(DecaInt32 number) {
        // This is stored as twice the data width as to not overflow the value
        long out = number.rawValue;
        
        if(number.PRECISION != this.PRECISION) {
            out = (long) number.rawValue * this.PRECISION / number.PRECISION;
        }
        return (int) out;
    }

    public void addIntRaw(int number) {
        this.rawValue += number;
    }


    // These are the most maintained functions in the program
    // The behavior of these functions set the precision to be the precision of the object
    // TODO: Add a precision input for this

    public DecaInt32 addDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.PRECISION);
        newValue.rawValue = convertToThisPrecision(num1) + convertToThisPrecision(num2);
        return newValue;
    }
    public DecaInt32 subtractDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.PRECISION);
        newValue.rawValue = convertToThisPrecision(num1) - convertToThisPrecision(num2);
        return newValue;

    }
    
    // For the division / multiplication, you have to temporarily store a 64 bit int to not lose data
    public DecaInt32 multiplyDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.PRECISION);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * convertToThisPrecision(num2)) / (this.PRECISION));
        return newValue;
    }
    public DecaInt32 divideDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.PRECISION);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * this.PRECISION) / convertToThisPrecision(num2));
        return newValue;
    }
    public DecaInt32 modDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.PRECISION);
        newValue.rawValue = convertToThisPrecision(num1) % convertToThisPrecision(num2);
        return newValue;
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
