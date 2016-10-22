package main.service.search;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;

public interface SearchService {

	CourseSignupJson seekCourses(CourseSearchPatternJson searchPattern);

}