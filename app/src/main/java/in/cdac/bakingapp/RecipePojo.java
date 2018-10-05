package in.cdac.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

@SuppressWarnings("ALL")
public class RecipePojo implements Parcelable {


    private int id;
    private String name = "";
    private List<IngredientsPojo> ingredientsList;
    private List<StepsPojo> stepsPojoList;
    private int servings;
    private String image;
    private int quantity;
    private String measure;
    private String ingredientValue;
    private int stepId;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipePojo() {

    }

    public RecipePojo(int id, String name, List<IngredientsPojo> ingredientsList, List<StepsPojo> stepsPojoList, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsPojoList = stepsPojoList;
        this.servings = servings;
        this.image = image;
    }

    public RecipePojo(int id, String name, int servings, String image, int quantity, String measure, String ingredientValue, int stepId, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientValue = ingredientValue;
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    private RecipePojo(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientsList = in.createTypedArrayList(IngredientsPojo.CREATOR);
        stepsPojoList = in.createTypedArrayList(StepsPojo.CREATOR);
        servings = in.readInt();
        image = in.readString();
        quantity = in.readInt();
        measure = in.readString();
        ingredientValue = in.readString();
        stepId = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<RecipePojo> CREATOR = new Creator<RecipePojo>() {
        @Override
        public RecipePojo createFromParcel(Parcel in) {
            return new RecipePojo(in);
        }

        @Override
        public RecipePojo[] newArray(int size) {
            return new RecipePojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedList(ingredientsList);
        parcel.writeTypedList(stepsPojoList);
        parcel.writeInt(servings);
        parcel.writeString(image);
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredientValue);
        parcel.writeInt(stepId);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IngredientsPojo> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<IngredientsPojo> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public List<StepsPojo> getStepsPojoList() {
        return stepsPojoList;
    }

    public void setStepsPojoList(List<StepsPojo> stepsPojoList) {
        this.stepsPojoList = stepsPojoList;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientValue() {
        return ingredientValue;
    }

    public void setIngredientValue(String ingredientValue) {
        this.ingredientValue = ingredientValue;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public static Creator<RecipePojo> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "RecipePojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredientsList=" + ingredientsList +
                ", stepsPojoList=" + stepsPojoList +
                ", servings=" + servings +
                ", image='" + image + '\'' +
                ", quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredientValue='" + ingredientValue + '\'' +
                ", stepId=" + stepId +
                ", shortDescription='" + shortDescription + '\'' +
                ", description='" + description + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                '}';
    }
}





