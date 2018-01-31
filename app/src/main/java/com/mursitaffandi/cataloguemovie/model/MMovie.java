
package com.mursitaffandi.cataloguemovie.model;

import java.util.List;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MMovie implements Parcelable
{
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    public final static Parcelable.Creator<MMovie> CREATOR = new Creator<MMovie>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MMovie createFromParcel(Parcel in) {
            return new MMovie(in);
        }

        public MMovie[] newArray(int size) {
            return (new MMovie[size]);
        }

    }
    ;

    protected MMovie(Parcel in) {
        in.readList(this.results, (Result.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public MMovie() {
    }

    /**
     * 
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public MMovie(Integer page, Integer totalResults, Integer totalPages, List<Result> results) {
        super();
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(results);
    }

    public int describeContents() {
        return  0;
    }


}
