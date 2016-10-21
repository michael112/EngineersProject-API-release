package main.json.placementtests;

import java.util.Set;

import javax.validation.Valid;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

import lombok.EqualsAndHashCode;

import main.constants.validationconstants.ValidationConstants;

@EqualsAndHashCode
public class SolvedPlacementTestJson {

    @NotBlank(message = "solvedplacementtest.id.empty")
    @Pattern(regexp = ValidationConstants.UUID_REGEX, message = "solvedplacementtest.id.invalid")
    @Size(max = 36, message = "solvedplacementtest.id.length")
    @Getter
    @Setter
    private String id;

    @Valid
    @Getter
    @Setter
    private Set<SolvedPlacementTaskJson> tasks;

}
