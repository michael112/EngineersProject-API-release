package main.json.menu;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.NONE)
public abstract class AbstractMenuJson {}