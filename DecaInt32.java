class DecaInt32 {
   int rawValue;
   final int PRECISION;
   final int PRECISION_LEN;
   final int DECIMAL_PLACES;
   final int TO_DECA; // TODO: better naming
   
   
   public DecaInt32(int rawValue, int PRECISION) {
      this.rawValue = rawValue * PRECISION;
      this.PRECISION = PRECISION;
      int precision_len_temp = String.valueOf(this.PRECISION).length();
      this.PRECISION_LEN = precision_len_temp + ((precision_len_temp < 2) ? 1 : 0);
      System.out.println(this.PRECISION_LEN);
      
      int temp = 1;
      for(int i = 0; i < PRECISION_LEN - 1; i++) {
         temp*=10;
      }
      this.DECIMAL_PLACES = temp;
      this.TO_DECA = DECIMAL_PLACES / PRECISION;
   }
   public void addInt(int number) {
      this.rawValue += (number * this.PRECISION);
   }
   public void addIntRaw(int number) {
      this.rawValue += number;
   }
   
   // Converts the decaInt value to whatever
   public String toString() {
      String decimalPoint = String.format("%0"+ ((this.PRECISION_LEN - 1)) + "d", Math.abs((this.rawValue % PRECISION) * this.TO_DECA));
      System.out.println(decimalPoint);
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
      String decimalPoint = String.format("%0"+ ((this.PRECISION_LEN - 1)) + "d", Math.abs((this.rawValue % PRECISION) * this.TO_DECA));
      System.out.println(decimalPoint);
      double decaIntToDouble = Double.parseDouble(String.valueOf((this.rawValue / this.PRECISION)) + "." + decimalPoint);
      return decaIntToDouble;
   }
   
   public int returnRawValue() {
      return this.rawValue;
   }
}
