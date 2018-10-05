package in.cdac.bakingapp;

import android.graphics.Bitmap;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("ALL")
public class QueryUtils {


    private static String TAG = QueryUtils.class.getName();
    private static HttpURLConnection connection = null;
    private static InputStream inputStream = null;

    private QueryUtils() {
    }
    public static List<RecipePojo> fetchDataFromQueryUtils(String request) {
        List<RecipePojo> recipePojos = new ArrayList<>();

        try {
            URL url = createURL(request);
            String response = makeConnection(url);
            recipePojos = extractJsonNewsFeedString(response);

        } catch (Exception e) {

            e.getMessage();
        }
        return recipePojos;
    }
    private static URL createURL(String requsetUrl) {
        URL url = null;
        try {
            url = new URL(requsetUrl);

        } catch (MalformedURLException e) {

            e.printStackTrace();
        }
        return url;
    }
    private static String makeConnection(URL url) {
        String jsonRes = "";
        if (url == null) {
            return jsonRes;
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {

                Log.e(TAG, "connection ok");
                inputStream = connection.getInputStream();
                jsonRes = readFromInputStream(inputStream);

            } else {
                Log.e(TAG, "connection==" + connection.getResponseMessage());
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.e(TAG, "jjjjjjjjj" + jsonRes);
        return jsonRes;
    }
    private static String readFromInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

            if (inputStream != null) {

                inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();

                while (line != null) {

                    stringBuilder.append(line);
                    line = reader.readLine();
                }
                Log.e(TAG, "********888" + stringBuilder.toString());
                return stringBuilder.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
        return stringBuilder.toString();
    }
    private static List<RecipePojo> extractJsonNewsFeedString(String jsonResponse) throws JSONException {
        Bitmap bitmapExtracted = null;
        List<RecipePojo> recipePojosList = new ArrayList<>();
        List<IngredientsPojo> ingredientsPojoList = new ArrayList<>();
        List<StepsPojo> stepsPojoList = new ArrayList<>();
        RecipePojo pojo = new RecipePojo();
        IngredientsPojo ingredientsPojo = new IngredientsPojo();
        StepsPojo stepsPojo = new StepsPojo();
        int id = 0;
        String name = "";
        String ingredients;
        int quantity = 0;
        String measure = "";
        String ingredientValue = null;
        String steps;
        int stepID = 0;
        String shortDes = "";
        String des = "";
        String videoURL = "";
        String thumbnailURL = "";
        int servings = 0;
        String image = null;

        if (jsonResponse == null) {

            return null;
        }
        Log.e(TAG, "jsonResponse " + id + name);

        JSONArray array = new JSONArray(jsonResponse);

        for (int i = 0; i < array.length(); i++) {
            JSONObject rootjsonobj = array.getJSONObject(i);

            if (rootjsonobj.has("id")) {
                id = rootjsonobj.optInt("id");

            }

            if (rootjsonobj.has("name")) {
                name = rootjsonobj.optString("name");
            }

            if (rootjsonobj.has("servings")) {
                servings = rootjsonobj.optInt("servings");
            }

            if (rootjsonobj.has("image")) {
                image = rootjsonobj.optString("image");
            }

            JSONArray jsonArray = rootjsonobj.getJSONArray("ingredients");

            for (int y = 0; y < jsonArray.length(); y++) {
                JSONObject obj = jsonArray.getJSONObject(y);

                if (obj.has("quantity")) {
                    quantity = obj.optInt("quantity");
                }

                if (obj.has("measure")) {
                    measure = obj.optString("measure");
                }

                if (obj.has("ingredient")) {
                    ingredientValue = obj.optString("ingredient");
                }

                ingredientsPojo = new IngredientsPojo(quantity, measure, ingredientValue);
                ingredientsPojoList.add(ingredientsPojo);
            }
            JSONArray jsonArrayStep = rootjsonobj.getJSONArray("steps");

            for (int y = 0; y < jsonArrayStep.length(); y++) {
                JSONObject objStep = jsonArrayStep.getJSONObject(y);

                if (objStep.has("id")) {
                    stepID = objStep.optInt("id");
                }

                if (objStep.has("shortDescription")) {
                    shortDes = objStep.optString("shortDescription");
                }

                if (objStep.has("description")) {
                    des = objStep.optString("description");
                }

                if (objStep.has("videoURL")) {
                    videoURL = objStep.optString("videoURL");
                }

                if (objStep.has("thumbnailURL")) {
                    thumbnailURL = objStep.optString("thumbnailURL");
                }

                stepsPojo = new StepsPojo(stepID, shortDes, des, videoURL, thumbnailURL);
                stepsPojoList.add(stepsPojo);
            }
            pojo = new RecipePojo(id, name, ingredientsPojoList, stepsPojoList, servings, image);
            recipePojosList.add(pojo);
        }
        Log.e("RecipePojo123List", "======" + ingredientsPojoList);
        return recipePojosList;
    }
}



