package sound;

public class Wave
{

	public static double sine(double waveIndex)
	{
		return Math.sin(waveIndex * 2 * Math.PI);
	}

	public static double saw(double waveIndex)
	{
		return 0.5 - waveIndex;
	}

	public static double square(double waveIndex)
	{
		return Math.round(waveIndex);
	}
	
	public static double mixed( double i )
	{
		return (square(i)/2 + saw(i)/2 + sine(i)) /3;
	}

}
