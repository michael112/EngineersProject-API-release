package main.json.placementtests;

import java.util.Set;

import javax.validation.Valid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class SolvedPlacementTaskJson {

    @NotBlank(message = "solvedplacementtask.id.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "solvedplacementtask.id.invalid")
    @Max(value = 36, message = "solvedplacementtask.id.length")
    @Getter
    @Setter
    private String id;

    @Valid
    @Getter
    @Setter
    private Set<SolvedPlacementSentenceJson> sentences;

}
