package main.model.enums;

import javax.persistence.Embeddable;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

import lombok.Getter;

@Embeddable
public class DayOfWeek {

    @Enumerated(EnumType.ORDINAL)
    @Column(name="day", nullable=false)
    private Day day;

    public int getDay() {
        return this.day.getDayID();
    }

    public String getDayName() {
        return this.day.toString();
    }

    public void setDay(String day) {
        this.day = Day.valueOf(day.toUpperCase());
    }

    public void setDay(int index) {
        switch (index % 7) {
            case 0:
                this.day = Day.SUNDAY;
                break;
            case 1:
                this.day = Day.MONDAY;
                break;
            case 2:
                this.day = Day.TUESDAY;
                break;
            case 3:
                this.day = Day.WEDNESDAY;
                break;
            case 4:
                this.day = Day.THURSDAY;
                break;
            case 5:
                this.day = Day.FRIDAY;
                break;
            case 6:
                this.day = Day.SATURDAY;
                break;
        }
    }

    public DayOfWeek() {
    }

    public DayOfWeek(int index) {
        this.setDay(index);
    }

    public DayOfWeek(String day) {
        this.setDay(day);
    }

    enum Day {

        MONDAY(1),
        TUESDAY(2),
        WEDNESDAY(3),
        THURSDAY(4),
        FRIDAY(5),
        SATURDAY(6),
        SUNDAY(7);

        @Getter
        private int dayID;

        Day(int day) {
            this.dayID = day;
        }

    }

}
