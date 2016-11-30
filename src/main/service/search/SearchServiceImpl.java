package main.service.search;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.util.locale.LocaleCodeProvider;

import main.service.crud.course.course.CourseCrudService;

import main.service.controller.AbstractService;

import main.json.course.CourseSignupJson;
import main.json.course.search.CourseSearchPatternJson;
import main.json.course.search.CourseDayJson;
import main.json.course.search.CourseHourJson;
import main.json.course.CourseUserJson;

import main.model.user.User;
import main.model.course.Course;

@Service("searchService")
public class SearchServiceImpl extends AbstractService implements SearchService {

	private CourseCrudService courseCrudService;

	public Set<CourseSignupJson> seekCourses(CourseSearchPatternJson searchPattern) {
		Set<Course> courses = this.courseCrudService.findCoursesByQuery(buildCourseSearchQuery(searchPattern));
		try {
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
		catch( NullPointerException ex ) {
			throw new NoResultException("course.search.results.empty");
		}
	}

	private String buildCourseSearchQuery(CourseSearchPatternJson searchPattern) {
		boolean isFirst = true;
		String query = "from Course c, CourseDay d ";
		if( searchPattern.getLanguage() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( language.id = " + searchPattern.getLanguage() + " ) ";
		}
		if( searchPattern.getCourseType() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( courseType.id = " + searchPattern.getCourseType() + " ) ";
		}
		if( searchPattern.getCourseLevel() != null ) {
			if( isFirst ) {
				query += "where (";
				isFirst = false;
			}
			else {
				query += "and ";
			}
			query += "( courseLevel.id = " + searchPattern.getCourseLevel() + " ) ";
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
			query += "( ( d.hourFrom.hour = " + courseHourJson.getHour() + " ) and ( d.hourFrom.minute = " + courseHourJson.getMinute() + " ) and ( d.hourFrom member of c.courseDays ) ) ";
		}
		query += ")";
		return query;
	}

	@Autowired
	public SearchServiceImpl(LocaleCodeProvider localeCodeProvider, CourseCrudService courseCrudService) {
		super(localeCodeProvider);
		this.courseCrudService = courseCrudService;
	}

}