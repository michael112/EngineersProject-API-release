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
		String query = "from Course c, CourseDay d, main.model.course.MyHour h ";
		if( searchPattern.getLanguage() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( language.id = \'" + searchPattern.getLanguage() + "\' ) ";
		}
		if( searchPattern.getCourseType() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( courseType.id = \'" + searchPattern.getCourseType() + "\' ) ";
		}
		if( searchPattern.getCourseLevel() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( courseLevel.id = \'" + searchPattern.getCourseLevel() + "\' ) ";
		}
		for(CourseDayJson courseDayJson : searchPattern.getCourseDays()) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( ( d.day.day = " + ( courseDayJson.getDay() % 7 ) + " ) and ( d.day.day member of c.courseDays ) ) ";
		}
		for( CourseHourJson courseHourJson : searchPattern.getCourseHours() ) {
			if( isFirst ) {
				query += "where ( ";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			// query += "( ( d.hourFrom.hour = " + courseHourJson.getHour() + " ) and ( d.hourFrom.minute = " + courseHourJson.getMinute() + " ) and ( d.hourFrom member of c.courseDays ) ) ";
			query += "( ( h.hour = " + courseHourJson.getHour() + " ) and ( h.minute = " + courseHourJson.getMinute() + " ) and ( h.hour member of d.hourFrom ) and ( h.minute member of d.hourFrom ) and ( d.hourFrom member of c.courseDays ) ) ";
		}
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