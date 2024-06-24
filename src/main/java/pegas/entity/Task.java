package pegas.entity;

public record Task(Integer id, String details, boolean complete){
    public Task(String details){
        this(5,details,false);
    }
}
