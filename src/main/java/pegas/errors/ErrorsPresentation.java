package pegas.errors;

import org.springframework.validation.Errors;

import java.util.List;

public record ErrorsPresentation(List<String> errorsList) {
}
