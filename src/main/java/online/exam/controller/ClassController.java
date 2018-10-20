package online.exam.controller;

import com.google.gson.Gson;
import online.exam.charts.StudentCount;
import online.exam.pojo.ClassInfo;
import online.exam.pojo.GradeInfo;
import online.exam.pojo.TeacherInfo;
import online.exam.service.ClassInfoService;
import online.exam.service.GradeInfoService;
import online.exam.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 16:33
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class ClassController {
    @Autowired
    private ClassInfo classInfo;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private Gson gson;
    @Autowired
    private GradeInfo gradeInfo;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private TeacherInfo teacherInfo;

    /**
     * 获取所有班级
     *
     * @param gradeId   年级编号
     * @param className 班级名称  可用于模糊查询
     * @param classId   班级编号
     * @return
     */
    @RequestMapping(value = "/classes", method = RequestMethod.GET)
    public ModelAndView getClasses(@RequestParam(value = "gradeId", required = false) Integer gradeId,
                                   @RequestParam(value = "className", required = false) String className,
                                   @RequestParam(value = "classId", required = false) Integer classId) {
        //------------日志
        System.out.println("获取班级集合 条件：gradeId： " + gradeId + ", 班级编号：" + classId + ", 班级：" + className);
        ModelAndView model = new ModelAndView();
        ClassInfo classInfo = new ClassInfo();

        /*处理查询条件*/
        if (gradeId != null) {
            GradeInfo gradeInfo = new GradeInfo();
            gradeInfo.setGradeId(gradeId);
            classInfo.setGrade(gradeInfo);
        }
        if (classId != null)
            classInfo.setClassId(classId);
        if (className != null) {
            if (className.trim() != "")
                classInfo.setClassName(className);
        }

        List<ClassInfo> classes = classInfoService.findAll(classInfo);
        model.setViewName("admin/classes");
        model.addObject("classes", classes);

        return model;
    }

    /**
     * 获取指定年级下的班级
     *
     * @param gradeId  年级编号
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/gradeclass/{gradeId}", method = RequestMethod.GET)
    public void getClassesByGradeId(@PathVariable("gradeId") Integer gradeId,
                                    HttpServletResponse response) throws IOException {
        //------------日志
        System.out.println("获取年级 " + gradeId + " 下的班级集合");

        List<ClassInfo> classes = classInfoService.findByGradeId(gradeId);

        String json = gson.toJson(classes);
        response.getWriter().print(json);
    }

    /**
     * 预添加教师
     *
     * @return
     */
    @RequestMapping("/preAddClass")
    public ModelAndView preAddClass() {
        //------------日志
        System.out.println("预添加班级信息");
        ModelAndView model = new ModelAndView("admin/classedit");
        List<GradeInfo> grades = gradeInfoService.findAll();
        model.addObject("grades", grades);
        //获取不是班主任的教师
        teacherInfo.setIsWork(0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startIndex", null);
        map.put("pageShow", null);
        map.put("teacher", teacherInfo);
        List<TeacherInfo> teachers = teacherInfoService.findAll(map);
        model.addObject("teachers", teachers);
        model.addObject("editClass", new ClassInfo());

        return model;
    }

    /**
     * 添加班级信息
     *
     * @param classInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "/class", method = RequestMethod.POST)
    public String isAddClass(ClassInfo classInfo, HttpServletRequest request) {
        //------------日志
        System.out.println("添加班级信息 " + classInfo);

        //修改教师班主任状态
        String returnMsg = setTeacherWorke(1, classInfo.getTeacher().getTeacherId());
        if (returnMsg != null) {
            request.setAttribute("error", "修改教师班主任状态 对应教师编号有误");
            return "error";
        }

        //添加
        int row = classInfoService.addClass(classInfo);
        if (row < 1) {
            //------------日志
            System.out.println("班级 " + classInfo + " 添加失败");

            request.setAttribute("error", "班级 " + classInfo.getClassName() + " 添加失败，请稍后再试！");
            return "../error";
        }

        return "redirect:/classes";
    }

    /**
     * 预修改教师处理
     *
     * @param id
     * @return
     */
    @RequestMapping("edit/class/{classId}")
    public ModelAndView preEditClass(@PathVariable("classId") Integer id) {
        //------------日志
        System.out.println("预修改班级信息");
        ModelAndView model = new ModelAndView("/admin/classedit");

        classInfo = classInfoService.findById(id);
        model.addObject("editClass", classInfo);
        List<GradeInfo> grades = gradeInfoService.findAll();
        model.addObject("grades", grades);

        //获取不是班主任的教师
        teacherInfo.setIsWork(0);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startIndex", null);
        map.put("pageShow", null);
        map.put("teacher", teacherInfo);
        List<TeacherInfo> teachers = teacherInfoService.findAll(map);

        //添加原本教师进入集合
        TeacherInfo t2 = classInfo.getTeacher();
        if (t2 != null) {
            teachers.add(t2);
        }
        //如果没有可用班主任
        if (teachers.size() == 0 || teachers == null) {
            teacherInfo.setTeacherName("暂无剩余教师");
            teachers.add(teacherInfo);
        }
        model.addObject("teachers", teachers);
        return model;
    }

    /**
     * 修改教师处理
     *
     * @param classInfo
     * @param request
     * @param lastTeacherId
     * @return
     */
    @RequestMapping(value = "edit/class/class", method = RequestMethod.POST)
    public String isUpdateClass(ClassInfo classInfo, HttpServletRequest request,
                                @RequestParam(value = "lastTeacher", required = false) Integer lastTeacherId) {
        //修改上一教师不为班主任状态
        if (lastTeacherId != null) {
            String returnMsg = setTeacherWorke(0, lastTeacherId);
            if (returnMsg != null) {
                request.setAttribute("error", "修改教师班主任状态 对应教师编号有误");
                return "/error";
            }
        }
        //修改当前教师为班主任状态
        String returnMsg = setTeacherWorke(1, classInfo.getTeacher().getTeacherId());
        if (returnMsg != null) {
            request.setAttribute("error", "修改教师班主任状态 对应教师编号有误");
            return "/error";
        }

        //------------日志
        System.out.println("修改班级 " + classInfo);

        int row = classInfoService.updateClass(classInfo);
        if (row < 1) {
            //------------日志
            System.out.println("班级 " + classInfo + " 修改失败");

            request.setAttribute("error", "班级修改失败，请稍后再试！");
            return "../error";
        }

        return "redirect:/classes";
    }

    /**
     * 删除班级
     *
     * @param classId 班级编号
     * @param request
     * @return
     */
    @RequestMapping(value = "/del/class/{classId}", method = RequestMethod.POST)
    public String isDelClass(@PathVariable("classId") Integer classId, HttpServletRequest request) {
        //------------日志
        System.out.println("删除班级 " + classId);

        //将删除班级对于之前班主任改为 非班主任状态
        ClassInfo delClass = classInfoService.findById(classId);
        String returnMsg = setTeacherWorke(0, delClass.getTeacher().getTeacherId());
        if (returnMsg != null) {
            request.setAttribute("error", "修改教师班主任状态 对应教师编号有误");
            return "error";
        }

        //删除
        int row = classInfoService.delClass(classId);
        if (row < 1) {
            //------------日志
            System.out.println("班级 " + classId + " 删除失败");

            request.setAttribute("error", "班级删除失败，请稍后再试！");
            return "../error";
        }

        return "redirect:/classes";
    }

    /**
     * 修改教师班主任状态
     *
     * @param status：是否为班主任
     * @param teacherId
     * @return
     */
    private String setTeacherWorke(int status, Integer teacherId) {
        teacherInfo.setTeacherId(teacherId);
        teacherInfo.setIsWork(status);
        if (teacherInfoService.setTeacherWorke(teacherInfo) > 0) {
            return null;
        }
        return "false";
    }

    /**
     * 图形化处理
     *
     * @param gradeId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/stuCount", method = RequestMethod.GET)
    public void getStudentCountForClass(
            @RequestParam(value = "gradeId", required = false) Integer gradeId,
            HttpServletResponse response) throws IOException {
        Map<String, Object> map = classInfoService.getStudentCountForClass(gradeId);
        String json = StudentCount.createBarJson(map);
        System.out.println("json:" + json);

        response.getWriter().print(json);
    }

}
