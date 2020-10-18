package weather;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
 
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import weather.ipma_client.IpmaCityForecast;
import weather.ipma_client.IpmaService;
import java.io.*;
import java.util.*;

/**
 * demonstrates the use of the IPMA API for weather forecast
 */
public class WeatherStarter {

    private static final int CITY_ID_AVEIRO = 1010500;
    /*
    loggers provide a better alternative to System.out.println
    https://rules.sonarsource.com/java/tag/bad-practice/RSPEC-106
     */
    private static final Logger logger = LogManager.getLogger(WeatherStarter.class.getName());

    public static void  main(String[] args ) throws FileNotFoundException{
        String City = "aveiro";
        if (args.length != 0) {
            City = args[0];
        }
        /*
        get a retrofit instance, loaded with the GSon lib to convert JSON into objects
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.ipma.pt/open-data/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TreeMap<String,String> C_and_I = cityIdsAndNames();
        IpmaService service = retrofit.create(IpmaService.class);
        Call<IpmaCityForecast> callSync = service.getForecastForACity(Integer.parseInt(C_and_I.get(City)));
        try {
            Response<IpmaCityForecast> apiResponse = callSync.execute();
            IpmaCityForecast forecast = apiResponse.body();

            if (forecast != null) {
                TreeMap<String,String> tm = forecast.getData().listIterator().next().getAllData();
                for(Map.Entry<String,String> entry : tm.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    logger.info(key+" "+value);
                }
            } else {
                logger.info( "No results!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    static TreeMap<String,String> cityIdsAndNames() throws FileNotFoundException {
        File file = new File("data.txt");
        TreeMap<String,String> Cities_and_Ids = new TreeMap<String,String>();
        Scanner sc = new Scanner(file);
        sc.useDelimiter(",");
        String id="";
        String city="";
        while(sc.hasNext()){
            String str =sc.next();
            if(str.contains("local")){
                city=str.replace("\"","").split(": ")[1].toLowerCase();
            }
            else if(str.contains("globalIdLocal")){
                id=str.replace("\"","").split(": ")[1];
            }
            Cities_and_Ids.put(city, id);
        }
        return Cities_and_Ids;
    }
}
