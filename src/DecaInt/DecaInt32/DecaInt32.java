// TODO: Test package to ensure it works
//package DecaInt.DecaInt32.DecaInt32;

class DecaInt32 {

    // TODO: Make unnecessary public variables/methods private/protected

    // NOTE: rawValue may be set to private in the future so you may want to refrain from using it
    // Same goes for displayDecimalLen and decimalPlaces (which probably will be removed in the future anyway
    public int rawValue;
    public int precision;
    public int displayDecimalLen;
    private int decimalPlaces;

    // TODO: Auto precision for double initilization, ex: 0.25 input = precision 4, mod of input, then 1 / input?

    public DecaInt32(int rawValue, int precision) {
        this.rawValue = rawValue * precision;
        init(precision);
    }

    // Also uses a float for this constructor here
    public DecaInt32(double rawValue, int precision) {
        this.rawValue = (int) (rawValue * precision);
        init(precision);
    }
    public DecaInt32(DecaInt32 num1) {
        // Pretty much just copy the contents of num1 to the current class
        this.rawValue = num1.rawValue;
        this.precision = num1.precision;
        this.displayDecimalLen = num1.displayDecimalLen;
        this.decimalPlaces = num1.decimalPlaces;
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
    }

    public void changePrecision(int precision) {
        this.rawValue = (int) (((((long) precision * 2147483647) / this.precision) * this.rawValue + 1073741824) / 2147483647);
        this.precision = precision;
    }

    public void changeDisplayPrecision(int precision) { // TODO:
        this.displayDecimalLen = precision;
        int temp = 1;
        for(int i = 0; i < displayDecimalLen - 1; i++)
            temp*=10;

        this.decimalPlaces = temp;
    }
    // Returns the raw value of the number as the object's precision.
    private int convertToThisPrecision(DecaInt32 number) {
        // This is stored as twice the data width as to not overflow the value
        long out = number.rawValue;

        if (number.precision != this.precision) {
            // WARNING! This is untested in extreme cases were number is astronomically high
            out = (int) (((((long) this.precision * 2147483647) / number.precision) * number.rawValue + 1073741824) / 2147483647);
        }
        return (int) out;
    }



    public void addIntRaw(int number) {
        this.rawValue += number;
    }
   
    // The diference between this and just doing "=" is that this doensn't affect num1 at all
    public void setTo(DecaInt32 num1) {
        this.rawValue = convertToThisPrecision(num1);
    }
    public void setTo(int num1) {
        this.rawValue = this.precision * num1;
    }

    // Return some constants in this precision
    // TODO: Fix some precision conversion errors that occur with this.
    public DecaInt32 PI() {
        DecaInt32 PI = (new DecaInt32(0, 50000000));
        PI.rawValue = 157079632;
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.setTo(PI);
        return newValue;
    }
    public DecaInt32 E() {
        DecaInt32 E = (new DecaInt32(0, 50000000));
        E.rawValue = 135914091;
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.setTo(E);
        return newValue;
    }

    // TODO: add method that returns max and min value for number

    // These are the most maintained functions in the program
    // The behavior of these functions set the precision to be the precision of the object

    public DecaInt32 addDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) + convertToThisPrecision(num2);
        return newValue;
    }
    public DecaInt32 addDecaInt32(DecaInt32 num2) {
        return addDecaInt32(this, num2);
    }

    public DecaInt32 subtractDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) - convertToThisPrecision(num2);
        return newValue;
    }
    public DecaInt32 subtractDecaInt32(DecaInt32 num2) {
        return subtractDecaInt32(this, num2);
    }

    // For the division / multiplication, you have to temporarily store a 64 bit int to not lose data
    public DecaInt32 multiplyDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * convertToThisPrecision(num2)) / (this.precision));
        return newValue;
    }
    public DecaInt32 multiplyDecaInt32(DecaInt32 num2) {
        return multiplyDecaInt32(this, num2);
    }

    public DecaInt32 divideDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = (int) (((long) convertToThisPrecision(num1) * this.precision) / convertToThisPrecision(num2));
        return newValue;
    }
    public DecaInt32 divideDecaInt32(DecaInt32 num2) {
        return divideDecaInt32(this, num2);
    }

    public DecaInt32 modDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        newValue.rawValue = convertToThisPrecision(num1) % convertToThisPrecision(num2);
        return newValue;
    }
    public DecaInt32 modDecaInt32(DecaInt32 num2) {
        return modDecaInt32(this, num2);
    }

    public DecaInt32 sqrtDecaInt32(DecaInt32 num1) {

        // Credit to https://math.stackexchange.com/questions/265690/continued-fraction-of-a-square-root for the maths help
        // This uses continued fractions to approximate the sqrt function

        DecaInt32 newValue = new DecaInt32(this.divideDecaInt32(num1, new DecaInt32(2, 1)));

        // 'a' is the number being added to the denominator
        DecaInt32 a = new DecaInt32(0, this.precision);

        while(!(newValue.equals(a))) {
            a.setTo(newValue);
            newValue.setTo(newValue.divideDecaInt32(newValue.addDecaInt32(newValue.divideDecaInt32(num1, a), a), new DecaInt32(2, 1)));
        }
        return newValue;
    }
    public DecaInt32 sqrtDecaInt32() {
        return sqrtDecaInt32(this);
    }

    public DecaInt32 lnDecaInt32(DecaInt32 num1) {
        // Retrieve E
        final DecaInt32 E = E();
        final DecaInt32 ONE_OVER_E = E.divideDecaInt32(new DecaInt32(1, this.precision), E);
        
        // Constants for diverging num1 to be as close to one as possible
        final DecaInt32 ln2 = new DecaInt32(0, 1073741823);
        ln2.rawValue = 744261116;
        
        final DecaInt32 sqrt2 = new DecaInt32(0, 1073741823);
        sqrt2.rawValue = 1518500246;
        
        final DecaInt32 lnSqrt2 = new DecaInt32(0, 1073741823);
        lnSqrt2.rawValue = 372130558;
        
        final DecaInt32 cubeRoot2 = new DecaInt32(0, 1073741823);
        cubeRoot2.rawValue = 1352829914;
        
        
        DecaInt32 newValue = new DecaInt32(0, this.precision);
        DecaInt32 x = new DecaInt32(num1);

        // According to math, ln(20) is equal to ln(5) + ln(4)
        // Also the natural log of a multiple of e will always be integer
        // We can use this to skip alot of extra computation.
        // Computational complexity: abs(ceil(ln(n) - 1)) + 3
        
        // Can calculate about 6 decimal points accurately
        
        if(x.greaterThan(ONE_OVER_E)) {
            while(x.greaterThan(E)) {
                x.setTo(x.divideDecaInt32(x, E));
                newValue.setTo(newValue.addDecaInt32(new DecaInt32(1, 1)));
            }
        } else {
            while(x.lessThan(ONE_OVER_E)) {
                x.setTo(x.multiplyDecaInt32(x, E));
                newValue.setTo(newValue.subtractDecaInt32(new DecaInt32(1, 1)));
            }
        }
        
        // Finetune the number to be as close to one as possible (for a better approximation without doing much work)
        
        if(x.greaterThan(sqrt2)) {
            x = x.divideDecaInt32(toDecaInt32(2));
            newValue = newValue.addDecaInt32(ln2);
        } else if(x.lessThan(x.divideDecaInt32(toDecaInt32(1), sqrt2))) {
          	x = x.multiplyDecaInt32(toDecaInt32(2));
            newValue = newValue.subtractDecaInt32(ln2);
        }
        
        if(x.greaterThan(cubeRoot2)) {
            x = x.divideDecaInt32(sqrt2);
            newValue = newValue.addDecaInt32(lnSqrt2);
        } else if(x.lessThan(x.divideDecaInt32(toDecaInt32(1), cubeRoot2))) {
          	x = x.multiplyDecaInt32(sqrt2);
            newValue = newValue.subtractDecaInt32(lnSqrt2);
        }
         
        // This looks a *bit* sloppy but it reads:
        // (3 * x * x - 3) / (x * x + 4 * x + 1)

        // It is the Pade approximation for a log function
        newValue.setTo(newValue.addDecaInt32(newValue, (x.multiplyDecaInt32(x).multiplyDecaInt32(new DecaInt32(3, 1)).subtractDecaInt32(new DecaInt32(3, 1))).divideDecaInt32((x.multiplyDecaInt32(x).addDecaInt32(x.multiplyDecaInt32(new DecaInt32(4, 1))).addDecaInt32(new DecaInt32(1, 1))))));
        return newValue;
    }
    public DecaInt32 lnDecaInt32() {
        return lnDecaInt32(this);
    }

    public DecaInt32 ePowerDecaInt32(DecaInt32 num1) {
        // TODO: Chnage based off of precision
        int maxIter = num1.toInt() + 6;
        DecaInt32 newValue = toDecaInt32(maxIter * 4 + 6);
        newValue.changePrecision(this.precision);

        // (iter * 4 + 6) + num1 * num1 / newValue;
        for(int iter = maxIter - 1; iter > -1; iter--) {
            newValue.setTo(this.addDecaInt32(toDecaInt32((iter * 4) + 6), this.multiplyDecaInt32(num1, num1).divideDecaInt32(newValue)));
        }
        // 1 + (2 * num1) / ((2 - num1) + ((num1 * num1) / newValue));
        newValue.setTo(this.addDecaInt32(toDecaInt32(1), this.multiplyDecaInt32(num1, toDecaInt32(2)).divideDecaInt32((this.subtractDecaInt32(toDecaInt32(2), num1)).addDecaInt32(this.multiplyDecaInt32(num1, num1).divideDecaInt32(newValue)))));
        return newValue;
    }

    public DecaInt32 ePowerDecaInt32() {
        return ePowerDecaInt32(this);
    }

    public DecaInt32 powDecaInt32(DecaInt32 num1, DecaInt32 num2) {
        // num1 ^ num2 where num1 and num2 can be any real number, even decimals (finally!)
        return this.ePowerDecaInt32(this.multiplyDecaInt32(num2, this.lnDecaInt32(num1)));
    }

    public DecaInt32 powDecaInt32(DecaInt32 num2) {
        return powDecaInt32(this, num2);
    }

    public static DecaInt32 toDecaInt32(int num1) {
        return new DecaInt32(num1, 1);
    }

    // Converts the decaInt value to whatever
    public String toString() {
        String decimalPoint = String.format("%0"+ ((this.displayDecimalLen - 1)) + "d", Math.abs(((long) this.rawValue % precision * decimalPlaces) / this.precision));
        String decaIntToString = String.valueOf((this.rawValue / this.precision)) + "." + decimalPoint;
        return decaIntToString;
    }

    // TODO: Make the toInt, and other stuff in a 'utils' class or just format it differently or something

    public int toInt() {
        // Rounds to nearest value.
        int decaIntToInt = (this.rawValue + (this.precision / 2)) / this.precision;
        return decaIntToInt;
    }

    // This is the only time this program uses a floating point number.
    public double toDouble() {
        double decaIntToDouble = (double) this.rawValue / this.precision;
        return decaIntToDouble;
    }

    public boolean equals(DecaInt32 num1) {
        // Converts it to the first precision
        // Example, (3.33 ->-> 3.00) == 3.00
        return this.rawValue == this.convertToThisPrecision(num1);
    }
    public boolean greaterThan(DecaInt32 num1) {
        return this.rawValue > this.convertToThisPrecision(num1);
    }
    public boolean lessThan(DecaInt32 num1) {
        return this.rawValue < this.convertToThisPrecision(num1);
    }
}
