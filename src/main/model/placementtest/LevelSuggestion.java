package main.model.placementtest;

import main.model.abstracts.AbstractUuidModel;
import main.model.course.CourseLevel;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="levelSuggestions")
@Access(AccessType.FIELD)
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "levelSuggestionID")) })
public class LevelSuggestion extends AbstractUuidModel {

    @Getter
    @Setter
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="courseLevelID", referencedColumnName="courseLevelID", nullable=false)
    private CourseLevel courseLevel;

    @Getter
    @Setter
    @Column(name="points", nullable=true)
    private double points;

    public LevelSuggestion() {
        super();
    }
    public LevelSuggestion(CourseLevel level, double points) {
        this();
        this.setCourseLevel(level);
        this.setPoints(points);
    }

}
