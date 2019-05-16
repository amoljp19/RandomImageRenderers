package com.softaai.randomimagerenderers.model;

import com.pedrogomez.renderers.AdapteeCollection;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.softaai.randomimagerenderers.R;
import com.softaai.randomimagerenderers.remote.ResponseManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Amol Pawar on 15-05-2019.
 * softAai Apps
 */
public class RandomImageCollectionGenerator {

    public static final Map<String, String> RANDOM_IMAGE_INFO = new HashMap<String, String>();

    private Random random;

    public RandomImageCollectionGenerator() {
        this.random = new Random();
        initializeImageInfo();
    }

    /**
     * Generate a ImageCollection with random data obtained form RANDOM_IMAGE_INFO map. You don't need o
     * create your own AdapteeCollections. Review ListAdapteeCollection if needed.
     *
     * @param randomImageCount size of the collection.
     * @return RandomImageCollection generated.
     */
    public RandomImageCollection generate(final int randomImageCount) {
        List<RandomImageResponse> randomImageResponseList = new LinkedList<RandomImageResponse>();
        for (int i = 0; i < randomImageCount; i++) {
            RandomImageResponse randomImageResponse = generateRandomImageResponse();
            randomImageResponseList.add(randomImageResponse);
        }
        return new RandomImageCollection(randomImageResponseList);
    }

    public AdapteeCollection<RandomImageResponse> generateListAdapteeRandomImageCollection(int randomImageCount) {
        List<RandomImageResponse> randomImageResponseList = new LinkedList<RandomImageResponse>();
        for (int i = 0; i < randomImageCount; i++) {
            RandomImageResponse randomImageResponse = generateRandomImageResponse();
            randomImageResponseList.add(randomImageResponse);
        }
        return new ListAdapteeCollection<RandomImageResponse>(randomImageResponseList);
    }

    /**
     * Initialize RANDOM_IMAGE_INFO data.
     */
    private void initializeImageInfo() {
        RANDOM_IMAGE_INFO.put("The Big Bang Theory", "http://thetvdb.com/banners/_cache/posters/80379-9.jpg");
        RANDOM_IMAGE_INFO.put("Breaking Bad", "http://thetvdb.com/banners/_cache/posters/81189-22.jpg");
        RANDOM_IMAGE_INFO.put("Arrow", "http://thetvdb.com/banners/_cache/posters/257655-15.jpg");
        RANDOM_IMAGE_INFO.put("Game of Thrones", "http://thetvdb.com/banners/_cache/posters/121361-26.jpg");
        RANDOM_IMAGE_INFO.put("Lost", "http://thetvdb.com/banners/_cache/posters/73739-2.jpg");
        RANDOM_IMAGE_INFO.put("How I met your mother", "http://thetvdb.com/banners/_cache/posters/75760-29.jpg");
        RANDOM_IMAGE_INFO.put("Dexter", "http://thetvdb.com/banners/_cache/posters/79349-24.jpg");
        RANDOM_IMAGE_INFO.put("Sleepy Hollow", "http://thetvdb.com/banners/_cache/posters/269578-5.jpg");
        RANDOM_IMAGE_INFO.put("The Vampire Diaries", "http://thetvdb.com/banners/_cache/posters/95491-27.jpg");
        RANDOM_IMAGE_INFO.put("Friends", "http://thetvdb.com/banners/_cache/posters/79168-4.jpg");
        RANDOM_IMAGE_INFO.put("New Girl", "http://thetvdb.com/banners/_cache/posters/248682-9.jpg");
        RANDOM_IMAGE_INFO.put("The Mentalist", "http://thetvdb.com/banners/_cache/posters/82459-1.jpg");
        RANDOM_IMAGE_INFO.put("Sons of Anarchy", "http://thetvdb.com/banners/_cache/posters/82696-1.jpg");
    }




    /**
     * Create a random Image Response.
     *
     * @return random Image Response.
     */
    private RandomImageResponse generateRandomImageResponse() {
        RandomImageResponse randomImageResponse = new RandomImageResponse();
        configureTitleAndThumbnail(randomImageResponse);
        return randomImageResponse;
    }

    private void configureId(final RandomImageResponse randomImageResponse){
        int id = random.nextInt();
        randomImageResponse.setId(String.valueOf(id));
    }




    private void configureTitleAndThumbnail(final RandomImageResponse randomImageResponse) {
        int maxInt = RANDOM_IMAGE_INFO.size();
        int randomIndex = random.nextInt(maxInt);
        String title = getKeyForIndex(randomIndex);
        randomImageResponse.setAuthor(title);
        String thumbnail = getValueForIndex(randomIndex);
        randomImageResponse.setUrl(thumbnail);
    }

    private String getKeyForIndex(int randomIndex) {
        int i = 0;
        for (String index : RANDOM_IMAGE_INFO.keySet()) {
            if (i == randomIndex) {
                return index;
            }
            i++;
        }
        return null;
    }

    private String getValueForIndex(int randomIndex) {
        int i = 0;
        for (String index : RANDOM_IMAGE_INFO.keySet()) {
            if (i == randomIndex) {
                return RANDOM_IMAGE_INFO.get(index);
            }
            i++;
        }
        return "";
    }

}
