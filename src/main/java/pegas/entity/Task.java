package pegas.entity;

import java.util.UUID;

public record Task(UUID id, String details, boolean complete){
    public Task(String details){
        this(UUID.randomUUID(),details,false);
    }
}
