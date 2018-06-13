package sound;

final public class Wave {

    public static double sine(double waveIndex) {
        double i = Math.toRadians(waveIndex);
        return Math.sin(i);
    }

    public static double saw(double waveIndex) {
        return  waveIndex/180 - 1;
    }

    public static double square(double waveIndex) {
        return waveIndex >  180 ?  -1 : 1;
    }

    public static double mixed(double i) {
        return (square(i) / 2 + saw(i) / 2 + sine(i)) / 3;
    }

}
