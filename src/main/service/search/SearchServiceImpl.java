package main.service.search;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.course.course.CourseCrudService;
import main.service.crud.user.user.UserCrudService;

import main.service.controller.AbstractService;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;
import main.json.course.search.CourseDayJson;
import main.json.course.search.CourseHourJson;
import main.json.course.CourseUserJson;
import main.json.user.UserSearchPatternJson;

import main.model.user.User;
import main.model.course.Course;

@Service("searchService")
public class SearchServiceImpl extends AbstractService implements SearchService {

	private CourseCrudService courseCrudService;
	private UserCrudService userCrudService;

	public Set<CourseSignupJson> seekCourses(CourseSearchPatternJson searchPattern) {
		Set<Course> courses = this.courseCrudService.findCoursesByQuery(buildCourseSearchQuery(searchPattern));
		if( ( courses != null ) && ( courses.size() > 0 ) ) {
			Set<CourseSignupJson> result = new HashSet<>();
			for( Course course : courses ) {
				CourseSignupJson courseSignupJson = new CourseSignupJson(course.getId(), course.getLanguage().getId(), course.getLanguage().getLanguageName(this.localeCodeProvider.getLocaleCode()), course.getCourseLevel().getName(), course.getCourseType().getId(), course.getCourseType().getCourseTypeName(this.localeCodeProvider.getLocaleCode()), course.getPrice());
				for( User teacher : course.getTeachers() ) {
					courseSignupJson.addTeacher(new CourseUserJson(teacher.getId(), teacher.getFullName()));
				}
				result.add(courseSignupJson);
			}
			return result;
		}
		else throw new NoResultException("course.search.results.empty");
	}

	public Set<CourseUserJson> seekUsers(UserSearchPatternJson searchPattern) {
		Set<User> users = this.userCrudService.findUsersByQuery("from User u where lower(concat(u.firstName, ' ', u.lastName)) like lower('%" + searchPattern.getPattern() + "%')");
		if( ( users != null ) && ( users.size() > 0 ) ) {
			Set<CourseUserJson> result = new HashSet<>();
			for( User user : users ) {
				CourseUserJson userJson = new CourseUserJson(user.getId(), user.getFullName());
				result.add(userJson);
			}
			return result;
		}
		else throw new NoResultException("user.search.results.empty");
	}

	private String buildCourseSearchQuery(CourseSearchPatternJson searchPattern) {
		boolean isFirst = true;
		String query = "select c from Course c join c.courseDays d ";
		if( searchPattern.getLanguage() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( c.language.id = \'" + searchPattern.getLanguage() + "\' ) ";
		}
		if( searchPattern.getCourseType() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( c.courseType.id = \'" + searchPattern.getCourseType() + "\' ) ";
		}
		if( searchPattern.getCourseLevel() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( c.courseLevel.id = \'" + searchPattern.getCourseLevel() + "\' ) ";
		}
		boolean isFirstOfCourseDay = true;
		String courseDayQuery = "";
		for(CourseDayJson courseDayJson : searchPattern.getCourseDays()) {
			if( isFirst ) {
				courseDayQuery += "where ( (";
				isFirst = false;
				isFirstOfCourseDay = false;
			}
			else if( isFirstOfCourseDay ) {
				courseDayQuery += "and ( ";
				isFirstOfCourseDay = false;
			}
			else {
				courseDayQuery += "or ";
			}
			courseDayQuery += "( d.day.day = " + ( ( courseDayJson.getDay() - 1 ) % 7 ) + " ) ";
		}
		courseDayQuery += ") ";
		query += courseDayQuery;
		boolean isFirstOfCourseHour = true;
		String courseHourQuery = "";
		for( CourseHourJson courseHourJson : searchPattern.getCourseHours() ) {
			if( isFirst ) {
				courseHourQuery += "where ( (";
				isFirst = false;
				isFirstOfCourseHour = false;
			}
			else if( isFirstOfCourseHour ) {
				courseHourQuery += "and ( ";
				isFirstOfCourseHour = false;
			}
			else {
				courseHourQuery += "or ";
			}
			courseHourQuery += "( d.hourFrom.time = \'" + courseHourJson.getHour() + ':' + courseHourJson.getMinute() + "\' ) ";
		}
		courseHourQuery += ") ";
		query += courseHourQuery;
		query += ")";
		return query;
	}

	@Autowired
	public SearchServiceImpl(LocaleCodeProvider localeCodeProvider, CourseCrudService courseCrudService, UserCrudService userCrudService) {
		super(localeCodeProvider);
		this.courseCrudService = courseCrudService;
		this.userCrudService = userCrudService;
	}

}