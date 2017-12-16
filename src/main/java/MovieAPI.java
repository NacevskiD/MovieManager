import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieAPI {
    String key = getKey();
    String baseURL = "https://api.themoviedb.org/3/search/movie?api_key=%s&query=%s";
    String query;
    String baseDetailURL = "https://api.themoviedb.org/3/movie/%d?api_key=%s&language=en-US";
    ArrayList<Movie> movieList;
    String baseYTurl = "https://api.themoviedb.org/3/movie/%s/videos?api_key=%s&language=en-US";



    MovieAPI(String query){
        this.query = query;
    }

    public ArrayList<Movie> getMovieList() throws Exception{

        String url = String.format(baseURL, key,query);

        System.out.println(query);
        System.out.println(url);

        InputStream stream = new URL(url).openConnection().getInputStream();

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

        System.out.println(responseString);

        // JSON processing here

        JSONObject jsonObject = new JSONObject(responseString);
        int totalResults = jsonObject.getInt("total_results");
        System.out.println(totalResults);
        JSONArray items = jsonObject.getJSONArray("results");
        System.out.println(items);

        int index = 0;

         movieList= new ArrayList<Movie>();

        while (totalResults > index) {
            JSONObject movie = items.getJSONObject(index);
            System.out.println(movie);
            String title = movie.getString("title");
            boolean adult = movie.getBoolean("adult");
            String description = movie.getString("overview");
            int id = movie.getInt("id");
            String date = movie.getString("release_date");
            String image = "";
            try {
                image = imageURL(movie.getString("poster_path"));
            }catch (Exception npe){
                image = "none";
            }


            System.out.println(title);
            System.out.println(description);
            System.out.println(id);
            System.out.println(date);
            System.out.println(image);


            Movie movie1 = new Movie();
            movie1.setTitle(title);
            movie1.setDescription(description);
            movie1.setId(id);
            movie1.setDate(date);
            movie1.setPictureURL(image);
            movie1.setYtLink(ytURL(id));
            List details = new ArrayList() ;
            details = getMovieDetails(id);
            movie1.setImdb(details.get(0).toString());
            movie1.setTagLine(details.get(1).toString());
            movie1.setRating(details.get(2).toString());
            movie1.setVoteCount(details.get(3).toString());
            movie1.setActors(getActors(id));

            movieList.add(movie1);

            index++;
        }

        return movieList;
    }

    List getMovieDetails(int id) throws Exception{
        String url = String.format(baseDetailURL, id,key);

        System.out.println(query);
        System.out.println(url);

        InputStream stream = new URL(url).openConnection().getInputStream();

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

        System.out.println(responseString);

        // JSON processing here

        JSONObject jsonObject = new JSONObject(responseString);


        String imdb = jsonObject.getString("imdb_id");
        String tagline = jsonObject.getString("tagline");
        double rating = jsonObject.getDouble("vote_average");
        int voteCount = jsonObject.getInt("vote_count");

        List details = new ArrayList();
        details.add(imdb);
        details.add(tagline);
        details.add(Double.toString(rating));
        details.add(voteCount);

        String test = details.get(0).toString();

        System.out.println(imdb);
        System.out.println(tagline);
        System.out.println(rating);

        return details;

    }

    String ytURL(int id) throws Exception{

        String ytURL = String.format(baseYTurl,id,key);

        InputStream stream = new URL(ytURL).openConnection().getInputStream();

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

        System.out.println(responseString);

        // JSON processing here

        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray items = jsonObject.getJSONArray("results");
        System.out.println(items);

        int index = 0;


        JSONObject movie = null;
        String youtube = "none";

        if (items.length()>0) {
            movie = items.getJSONObject(index);
            System.out.println(movie);
            String ytID = movie.getString("key");

            youtube = getYTurl(ytID);
        }


        return youtube;


    }

    String imageURL(String path){
        String baseURL = "https://image.tmdb.org/t/p/w500%s";
        String picURL = String.format(baseURL,path);
        return picURL;

    }

    String getYTurl(String id){
        String baseURL = "https://www.youtube.com/watch?v=%s";
        String yt = String.format(baseURL,id);
        return yt;
    }

    ArrayList<String> getActors(int id) throws Exception{
        String baseActorURL = "https://api.themoviedb.org/3/movie/%s/credits?api_key=%s";
        String actorURL = String.format(baseActorURL,id,key);

        InputStream stream = new URL(actorURL).openConnection().getInputStream();
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

        System.out.println(responseString);

        // JSON processing here

        JSONObject jsonObject = new JSONObject(responseString);
        JSONArray items = jsonObject.getJSONArray("cast");
        System.out.println(items);

        ArrayList<String> actors = new <String> ArrayList();



        JSONObject movie = null;

        for (int index = 0;index < 5;index++) {
            String name;
            try {
                movie = items.getJSONObject(index);
                name = movie.getString("name");
            }catch (Exception exe){
                name = "None";
            }



            actors.add(name);
        }


        return actors;


        }


        String getKey() {
            String key = "";
            try{
            FileReader fileReader =
                    new FileReader("src\\main\\java\\key.txt");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            key = bufferedReader.readLine();
            bufferedReader.close();

            }
                    catch (Exception e){
                        System.out.println("File not found");
                        return null;
                    }

                    return key;
        }
    }

    //return title;



