package me.wingert.vocabularybuilder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("id")
    public int id;

    @SerializedName("word")
    public String word;

    @SerializedName("definition")
    public String definition;

    public Post(int id, String word, String definition) {
        this.id = id;
        this.word = word;
        this.definition = definition;
    }

    public Post(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String toString() {
        return "Post(" + id + " " + word + ": " + definition + ")";
    }

}