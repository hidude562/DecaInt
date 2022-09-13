package DecaInt.DecaInt32.DecaInt32;

class DecaInt32 {
    int rawValue;
    int precision;
    int displayDecimalLen;
    int decimalPlaces;
    int toDeca; // TODO: better naming
    
    // TODO: Auto precision, ex: 0.25 input = precision 4, mod of input, then 1 / input?

    public DecaInt32(int rawValue, int precision) {
        this.rawValue = rawValue * precision;
        init(precision);
    }
    
    // Also uses a float for this constructor here
    public DecaInt32(double rawValue, int precision) {
        this.rawValue = (int) (rawValue * precision);
        init(precision);
    }
    
    public void init(int precision) {
        this.precision = precision;
        int precision_len_temp = String.valueOf(this.precision).length();
        this.displayDecimalLen = (precision_len_temp + ((precision_len_temp < 2) ? 1 : 0)) + 1;

        int temp = 1;
        for(int i = 0; i < displayDecimalLen; i++) {
            temp*=10;
        }
        this.decimalPlaces = temp;
        this.toDeca = decimalPlaces / precision;
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
        
        if(number.precision != this.precision) {
            out = (long) number.rawValue * this.precision / number.precision;
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
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) + convertToThisPrecision(num2);
        return newValue;
    }
    public DecaInt32 subtractDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) - convertToThisPrecision(num2);
        return newValue;

    }
    
    // For the division / multiplication, you have to temporarily store a 64 bit int to not lose data
    public DecaInt32 multiplyDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * convertToThisPrecision(num2)) / (this.precision));
        return newValue;
    }
    public DecaInt32 divideDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * this.precision) / convertToThisPrecision(num2));
        return newValue;
    }
    public DecaInt32 modDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) % convertToThisPrecision(num2);
        return newValue;
    }


    // Converts the decaInt value to whatever
    public String toString() {
        String decimalPoint = String.format("%0"+ ((this.displayDecimalLen - 1)) + "d", Math.abs((this.rawValue % precision) * this.toDeca));
        String decaIntToString = String.valueOf((this.rawValue / this.precision)) + "." + decimalPoint;
        return decaIntToString;
    }
    public int toInt() {
        // Doesn't round.
        int decaIntToInt = this.rawValue / this.precision;
        return decaIntToInt;
    }

    // This is the only time this program uses a floating point number.
    public double toDouble() {
        String decimalPoint = String.format("%0"+ ((this.displayDecimalLen - 1)) + "d", Math.abs((this.rawValue % precision) * this.toDeca));
        System.out.println( this.toDeca);
        double decaIntToDouble = Double.parseDouble(String.valueOf((this.rawValue / this.precision)) + "." + decimalPoint);
        return decaIntToDouble;
    }

    public int returnRawValue() {
        return this.rawValue;
    }
}
