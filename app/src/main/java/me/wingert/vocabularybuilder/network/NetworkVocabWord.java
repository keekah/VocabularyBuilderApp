package me.wingert.vocabularybuilder.network;

public class NetworkVocabWord {

    private int id;

    private String word;

    private String definition;

    private Integer userId;

    private String addedDateTime;

    private String modifiedDateTime;

    public NetworkVocabWord(int id, String word, String definition, Integer userId, String addedDateTime, String modifiedDateTime) {
        this.id = id;
        this.word = word;
        this.definition = definition;
        this.userId = userId;
        this.addedDateTime = addedDateTime;
        this.modifiedDateTime = modifiedDateTime;
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

    public Integer getUserId() { return userId; }

    public String getAddedDateTime() { return addedDateTime; }

    public String getModifiedDateTime() { return modifiedDateTime; }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setUserId(Integer userId) { this.userId = userId; }

    public void setAddedDateTime(String addedDateTime) { this.addedDateTime = addedDateTime; }

    public void setModifiedDateTime(String modifiedDateTime) { this.modifiedDateTime = modifiedDateTime; }

    public String toString() {
        return id + " " + word + ": " + definition + " " + userId;
    }

}