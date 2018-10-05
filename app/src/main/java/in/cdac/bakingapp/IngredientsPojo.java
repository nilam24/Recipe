package in.cdac.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientsPojo implements Parcelable {

    private int quantity;
    private String measure;
    private String ingredientValue;

    public IngredientsPojo() {
    }

    public IngredientsPojo(int quantity, String measure, String ingredientValue) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientValue = ingredientValue;
    }

    private IngredientsPojo(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredientValue = in.readString();
    }

    public static final Creator<IngredientsPojo> CREATOR = new Creator<IngredientsPojo>() {
        @Override
        public IngredientsPojo createFromParcel(Parcel in) {
            return new IngredientsPojo(in);
        }

        @Override
        public IngredientsPojo[] newArray(int size) {
            return new IngredientsPojo[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    @SuppressWarnings("unused")
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    @SuppressWarnings("unused")
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientValue() {
        return ingredientValue;
    }

    @SuppressWarnings("unused")
    public void setIngredientValue(String ingredientValue) {
        this.ingredientValue = ingredientValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredientValue);
    }

    @Override
    public String toString() {
        return "IngredientsPojo{" +
                "quantity=" + quantity +
                ", measure='" + measure + '\'' +
                ", ingredientValue='" + ingredientValue + '\'' +
                '}';
    }
}
