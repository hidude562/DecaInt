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
   
   public void addInt(int number) {
      this.rawValue += (number * this.PRECISION);
   }
   public void addIntRaw(int number) {
      this.rawValue += number;
   }
   
   // These are the most maintained functions in the program
   public void addDecaInt32(DecaInt32 num2) {
      this.rawValue += convertToThisPrecision(num2);
   }
   public void subtractDecaInt32(DecaInt32 num2) {
      this.rawValue -= convertToThisPrecision(num2);
   }
   public void multiplyDecaInt32(DecaInt32 num2) {
      this.rawValue *= convertToThisPrecision(num2);
      this.rawValue /= (this.PRECISION);
   }
   public void divideDecaInt32(DecaInt32 num2) {
      this.rawValue = (this.rawValue * this.PRECISION) / convertToThisPrecision(num2);
   }
   public void modDecaInt32(DecaInt32 num2) {
      this.rawValue = (this.rawValue) % convertToThisPrecision(num2);
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
