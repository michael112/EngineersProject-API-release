package main.json.placementtests;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class SolvedPlacementSentenceJson {

    @NotBlank(message = "solvedplacementsentence.id.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "solvedplacementsentence.id.invalid")
    @Size(max = 36, message = "solvedplacementsentence.id.length")
    @Getter
    @Setter
    private String id;

    @NotBlank(message = "solvedplacementanswer.empty")
    @Size(max = 1, message = "solvedplacementanswer.length")
    @Getter
    @Setter
    private String answer;

}
