
package bench.cpu;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The GaussLegendre class provides a method to approximate the mathematical constant Pi
 * using the Gauss-Legendre algorithm with a specified precision.
 * */

class GaussLegendre {

    /**
     * The number of decimal places to use in the approximation.
     */
    static int precision;

    /**
     * Returns the approximation of the mathematical constant Pi using the Gauss-Legendre algorithm with the specified precision.
     *
     * @return the approximation of Pi with the specified precision.
     */
    public static BigDecimal approxPi() {

        BigDecimal[] ar = new BigDecimal[precision];
        BigDecimal[] br = new BigDecimal[precision];
        BigDecimal[] tr = new BigDecimal[precision];
        BigDecimal[] pr = new BigDecimal[precision];

        for (int i = 1; i < precision; i++) {
            // initializing first value of all arrays
            ar[0] = BigDecimal.ONE;
            br[0] = BigDecimal.ONE.divide(BigDecimal.valueOf(Math.sqrt(2)), precision, RoundingMode.HALF_UP);
            tr[0] = BigDecimal.ONE.divide(BigDecimal.valueOf(4), precision, BigDecimal.ROUND_HALF_UP);
            pr[0] = BigDecimal.ONE;

            ar[i] = (ar[i - 1].add(br[i - 1])).divide(BigDecimal.valueOf(2), precision, BigDecimal.ROUND_HALF_UP);

            br[i] = BigDecimal.valueOf(Math.sqrt(ar[i - 1].multiply(br[i - 1]).doubleValue()));

            tr[i] = tr[i - 1].subtract(pr[i - 1].multiply(ar[i - 1].subtract(ar[i])).multiply(ar[i - 1].subtract(ar[i])))
                    .setScale(precision, BigDecimal.ROUND_HALF_UP);

            pr[i] = pr[i - 1].multiply(BigDecimal.valueOf(2));
        }

        return (ar[precision - 1].add(br[precision - 1])).pow(2).divide(tr[precision - 1].multiply(BigDecimal.valueOf(4)), precision,
                RoundingMode.HALF_UP);
    }

    /**
     * Sets the precision for the approximation of Pi.
     *
     * @param precision the number of decimal places to use in the approximation.
     */
    public void setPrecision(int precision) {
        GaussLegendre.precision = precision;
    }

}