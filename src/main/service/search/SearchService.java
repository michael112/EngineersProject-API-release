package main.service.search;

import java.util.Set;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;

public interface SearchService {

	Set<CourseSignupJson> seekCourses(CourseSearchPatternJson searchPattern);

}