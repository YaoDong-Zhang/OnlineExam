package online.exam.controller;

import online.exam.pojo.CourseInfo;
import online.exam.pojo.GradeInfo;
import online.exam.service.CourseInfoService;
import online.exam.service.GradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 15:49
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class CourseController {
    @Autowired
    private CourseInfo courseInfo;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private GradeInfo gradeInfo;

    /**
     * 获取所有班级
     *
     * @param gradeId
     * @param division
     * @return
     */
    @RequestMapping("/courses")
    public ModelAndView getCourses(@RequestParam(value = "gradeId", required = false) Integer gradeId,
                                   @RequestParam(value = "division", required = false) Integer division) {
        //------------日志
        System.out.println("获取科目集合 年级条件 " + gradeId + " 分科条件 " + division);

        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/courses");

        CourseInfo course = new CourseInfo();
        if (gradeId != null)
            course.getGrade().setGradeId(gradeId);
        if (division != null)
            course.setDivision(division);
        List<CourseInfo> courses = courseInfoService.findAll(course);
        model.addObject("courses", courses);
        return model;
    }

    /**
     * 预添加科目信息
     *
     * @return
     */
    @RequestMapping("/preAddCourse")
    public ModelAndView preAddCourse() {
        //------------日志
        System.out.println("预添加科目信息");
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/courseedit");
        //获取所有年级
        List<GradeInfo> grades = gradeInfoService.findAll();
        model.addObject("grades", grades);
        return model;
    }

    /**
     * 预修改科目信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/course/{courseId}", method = RequestMethod.GET)
    public ModelAndView preUpadteCourse(@PathVariable("courseId") Integer id) {
        //------------日志
        System.out.println("预修改科目信息");
        ModelAndView model = new ModelAndView("/admin/courseedit");

        courseInfo = courseInfoService.findById(id);
        model.addObject("course", courseInfo);
        //获取所有年级
        List<GradeInfo> grades = gradeInfoService.findAll();
        model.addObject("grades", grades);

        return model;
    }

    /**
     * 添加或修改学科
     *
     * @param courseId
     * @param isUpdate
     * @param courseName
     * @param division
     * @param gradeId
     * @return
     */
    @RequestMapping(value = "/course/course", method = RequestMethod.POST)
    public String addAndUpdate(@RequestParam(value = "courseId", required = false) Integer courseId,
                               @RequestParam(value = "isupdate", required = false) Integer isUpdate,
                               @RequestParam("courseName") String courseName,
                               @RequestParam("division") Integer division,
                               @RequestParam("gradeId") Integer gradeId) {
        courseInfo.setCourseName(courseName);
        courseInfo.setDivision(division);
        gradeInfo.setGradeId(gradeId);
        courseInfo.setGrade(gradeInfo);

        if (isUpdate == null) {
            //------------日志
            System.out.println("添加科目信息：" + courseId);
            courseInfoService.AddCourse(courseInfo);
        } else {
            //------------日志
            System.out.println("修改科目信息：" + courseId);
            courseInfo.setCourseId(courseId);
            courseInfoService.updateCourse(courseInfo);
        }

        return "redirect:/courses";
    }

    /**
     * 删除科目信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/course/{courseId}")
    public String delCourse(@PathVariable("courseId") Integer id) {

        courseInfo = courseInfoService.findById(id);
        //------------日志
        System.out.println("删除科目信息：" + courseInfo.getCourseId());
        courseInfoService.delCourse(id);
        return "redirect:/courses";
    }

}
