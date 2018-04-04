package sound;

public class Wave {

    public static double sine(double waveIndex) {
        return Math.sin(waveIndex * 2 * Math.PI);
    }

    public static double saw(double waveIndex) {
        return  waveIndex*2 - 1;
    }

    public static double square(double waveIndex) {
        return waveIndex > 0.5 ?  -1 : 1;
    }

    public static double mixed(double i) {
        return (square(i) / 2 + saw(i) / 2 + sine(i)) / 3;
    }

}
