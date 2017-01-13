package main.service.controller.admin.type;

import main.json.admin.type.view.CourseTypeListJson;

import main.json.admin.type.CourseTypeNameJson;

import main.json.admin.type.CourseTypeJson;

import main.model.course.CourseType;

public interface AdminTypeService {

    CourseTypeListJson getCourseTypeList();

    void addCourseType(CourseTypeJson courseTypeJson);

    main.json.admin.type.view.multilang.CourseTypeJson getCourseTypeInfo(CourseType courseType);

    void editCourseTypeNames(CourseType courseType, CourseTypeJson courseTypeJson);

    void editSingleCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson);

    void addCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson);

    void removeCourseTypeName(CourseType courseType, String courseTypeNameID);

    void removeCourseType(CourseType courseType);

}
