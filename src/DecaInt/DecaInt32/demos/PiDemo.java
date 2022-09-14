package DecaInt.DecaInt32.demos.PiDemo;

import DecaInt.DecaInt32.DecaInt32;

class PiDemo {
    public static void main(String[] args) {
      // This is a demo using DecaInt32, it uses the Leibniz formula to calculate Pi
      
      DecaInt32 piEstimate = new DecaInt32(1, 1000);
      
      
      // The amount of iterations for the algorithm to go through
      int totalIter = 5000;
      
      for(int iter = 0; iter < totalIter; iter++) {
         // Create a fraction
         DecaInt32 numerator = new DecaInt32(1, 1);
         DecaInt32 denominator = new DecaInt32((iter * 2) + 3, (iter * 2) + 3);
         
         if(iter % 2 == 0) {
            piEstimate = piEstimate.subtractDecaInt32(piEstimate, denominator.divideDecaInt32(numerator, denominator));
         } else {
            piEstimate = piEstimate.addDecaInt32(piEstimate, denominator.divideDecaInt32(numerator, denominator));
         }
      }
      
      piEstimate = piEstimate.multiplyDecaInt32(piEstimate, new DecaInt32(4, 1)); // Multiply by 4
      
      System.out.println("Estimate of PI: " + piEstimate);
    }
}
