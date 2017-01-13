package main.service.controller.admin.type;

import main.json.admin.type.view.CourseTypeListJson;

import main.json.admin.type.CourseTypeNameJson;

import main.json.admin.type.CourseTypeJson;
import main.json.admin.type.NewCourseTypeJson;
import main.json.admin.type.EditCourseTypeJson;

import main.model.course.CourseType;

public interface AdminTypeService {

    CourseTypeListJson getCourseTypeList();

    void addCourseType(NewCourseTypeJson courseTypeJson);

    CourseTypeJson getCourseTypeInfo(CourseType courseType);

    void editCourseTypeNames(CourseType courseType, EditCourseTypeJson courseTypeJson);

    void editSingleCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson);

    void addCourseTypeName(CourseType courseType, CourseTypeNameJson courseTypeNameJson);

    void removeCourseType(CourseType courseType);

}
