import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.*;



public class MovieAPI {
    private String baseURL;

    MovieAPI(){
        baseURL = "http://www.theimdbapi.org/api/";
    }

    void getMovie(String query) throws Exception{
        String url = baseURL + "find/movie?title=" + query;


        System.out.println(query);
        System.out.println(url);

        URLConnection urlReader = new URL(url).openConnection();
        urlReader.setRequestProperty("User-Agent", "Mozilla/5.0");
        InputStream stream = urlReader.getInputStream();

        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        // Read stream into String. Use StringBuilder to put multiple lines together.
        // Read lines in a loop until the end of the stream.
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }

        //and turn the StringBuilder into a String.
        String responseString = builder.toString();


        System.out.println();


        // JSON processing here
        System.out.println(responseString);

        //JSONObject jsonObject = new JSONObject(responseString.substring(responseString.indexOf('{')));
        JSONObject jsonObject = new JSONObject(responseString);

        System.out.println(jsonObject);

        JSONObject metadata = jsonObject.getJSONObject("metadata");
        String name = jsonObject.getString("title");
        System.out.println(metadata);
        System.out.println(name);
        String year = jsonObject.getString("year");
        System.out.println(year);
        String content_rating = jsonObject.getString("content_rating");
        System.out.println(content_rating);

       // JSONArray items = jsonObject.getJSONArray();

    }



    }


