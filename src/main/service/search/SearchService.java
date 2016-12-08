package main.service.search;

import java.util.Set;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;
import main.json.course.CourseUserJson;
import main.json.user.UserSearchPatternJson;

public interface SearchService {

	Set<CourseSignupJson> seekCourses(CourseSearchPatternJson searchPattern);

	Set<CourseUserJson> seekUsers(UserSearchPatternJson searchPattern);

}