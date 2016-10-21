package main.service.search;

import main.json.course.search.CourseJson;
import main.json.course.search.CourseSearchPatternJson;

public interface SearchService {

	CourseJson seekCourses(CourseSearchPatternJson searchPattern);

}