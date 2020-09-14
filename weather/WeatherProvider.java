package weather;

public class WeatherProvider
{
    private static WeatherProvider weatherProvider = new WeatherProvider();
    private static String[] weather = {
            "SUN",
            "SNOW",
            "RAIN",
            "FOG"
    };

    private WeatherProvider()
    {
    }

    public static WeatherProvider getProvider()
    {
        return WeatherProvider.weatherProvider;
    }

    private int convertByteToInt(byte[] b)
    {
        int value = 0;
        for(int i = 0; i < b.length; i++)
            value = (value << 8) | b[i];
        return value;
    }
    public String getCurrentWeather(Coordinates coordinates)
    {
        int seed = coordinates.getLongitude() + coordinates.getLatitude() + coordinates.getHeight();
        if (coordinates.getHeight() > 80){
            return weather[0];
        }
        else if (coordinates.getHeight() > 60){
            return weather[seed % 2];
        }
        else if (coordinates.getHeight() > 30){
            return weather[seed % 3];
        }
        else {
            return weather[seed % 4];
        }
    }
}