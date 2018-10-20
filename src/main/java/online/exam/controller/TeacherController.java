package online.exam.controller;

import online.exam.pojo.ClassInfo;
import online.exam.pojo.TeacherInfo;
import online.exam.service.ClassInfoService;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/2 10:26
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class TeacherController {

    @Autowired
    private TeacherInfo teacher;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private ClassInfo classInfo;
    @Autowired
    private ClassInfoService classInfoService;

    /**
     * 老师尝试登陆后台
     *
     * @param teacherAccount
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/validateTeacher", method = RequestMethod.POST)
    public void validateTeacher(@RequestParam("account") String teacherAccount,
                                HttpServletResponse response) throws IOException {
        teacher = teacherInfoService.findByAccount(teacherAccount);
        //------------日志
        System.out.println("教师账户 " + teacherAccount + "，尝试登陆");
        if (teacher != null) {
            response.getWriter().print(teacher.getTeacherPwd());
        } else {
            response.getWriter().print("1");
        }
    }

    /**
     * 教师登陆系统 session记录教师信息
     *
     * @param teacherAccount
     * @param request
     * @return
     */
    @RequestMapping(value = "/teacherlogin", method = RequestMethod.POST)
    public String teacherlogin(@RequestParam("teacherAccount") String teacherAccount, HttpServletRequest request) {
        if (teacherAccount == null) {
            request.setAttribute("error", "登录信息有误");
            return "../error";
        }
        //获取当前登录教师
        TeacherInfo teacherInfo = teacherInfoService.findByAccount(teacherAccount);
        //将当前登录教师 后台权限存入 Session
        request.getSession().setAttribute("adminPower", teacherInfo.getAdminPower());
        request.getSession().setAttribute("loginTeacher", teacherInfo);
        //------------日志
        System.out.println("教师账户 " + teacherAccount + "，登陆后台系统");
        return "redirect:admin/index.jsp";
    }

    /**
     * 教师退出后台系统
     *
     * @param session
     * @return
     */
    @RequestMapping("/exitTeacher")
    public String exitTeacher(HttpSession session) {
        teacher = (TeacherInfo) session.getAttribute("loginTeacher");
        session.removeAttribute("loginTeacher");
        //------------日志
        System.out.println("教师账户 " + teacher.getTeacherAccount() + "，退出后台系统");
        return "redirect:admin/login.jsp";
    }

    /**
     * 分页查询教师集合
     *
     * @param startPage
     * @param pageShow
     * @return
     */
    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ModelAndView getTeachers(
            @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,  //当前页码,默认第一页
            @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow /*每页显示数据量，默认10条*/) {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/teachers");

        List<TeacherInfo> teachers;

        Map<String, Object> map = new HashMap<String, Object>();
        //计算当前查询起始数据索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);
        map.put("teacher", null);
        teachers = teacherInfoService.findAll(map);
        model.addObject("teachers", teachers);

        //获取教师总量
        int teacherTotal = teacherInfoService.teacherSum();
        //计算总页数
        int pageTotal = 1;
        if (teacherTotal % pageShow == 0) {
            pageTotal = teacherTotal / pageShow;
        } else {
            pageTotal = teacherTotal / pageShow + 1;
        }
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        return model;
    }

    /**
     * 预修改教师
     *
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.GET)
    public ModelAndView preUpdateTeacher(@PathVariable("teacherId") Integer teacherId) {
        //------------日志
        System.out.println("预修改教师处理");
        ModelAndView model = new ModelAndView();
        //获取要修改教师
        TeacherInfo teacher = teacherInfoService.findById(teacherId);
        model.setViewName("/admin/teacheredit");
        model.addObject("teacher", teacher);
        return model;
    }

    /**
     * 添加修改教师信息
     *
     * @param teacherId
     * @param isUpdate
     * @param teacherName
     * @param teacherAccount
     * @param teacherPwd
     * @param adminPower
     * @return
     */
    @RequestMapping(value = "/teacher/teacher", method = RequestMethod.POST)
    public String isUpdateOrAddTeacher(@RequestParam(value = "teacherId", required = false) Integer teacherId,
                                       @RequestParam(value = "isupdate", required = false) Integer isUpdate,
                                       @RequestParam("teacherName") String teacherName,
                                       @RequestParam("teacherAccount") String teacherAccount,
                                       @RequestParam("teacherPwd") String teacherPwd,
                                       @RequestParam("adminPower") Integer adminPower) {

        TeacherInfo teacher = new TeacherInfo();
        teacher.setTeacherId(teacherId);
        teacher.setTeacherName(teacherName);
        teacher.setTeacherAccount(teacherAccount);
        teacher.setTeacherPwd(teacherPwd);
        teacher.setAdminPower(adminPower);

        if (isUpdate != null) {  //修改
            //------------日志
            System.out.println("修改教师 " + teacher + " 的信息");
            int row = teacherInfoService.updateTeacher(teacher);
        } else {  //添加
            //------------日志
            System.out.println("添加教师 " + teacher + " 的信息");
            int row = teacherInfoService.addTeacher(teacher);
        }

        return "redirect:/teachers";
    }

    /**
     * 删除教师
     *
     * @param teacherId
     * @return
     */
    @RequestMapping(value = "/teacher/{teacherId}", method = RequestMethod.POST)
    public String isDelTeacher(@PathVariable("teacherId") Integer teacherId) {
        //查询教师下的班级
        classInfo = classInfoService.findByTeacherId(teacherId);
        //班主任设置为空
        if (classInfo != null) {
            //------------日志
            System.out.println("删除 " + classInfo.getClassName() + " 班主任");
            classInfoService.setTeacher(classInfo.getClassId());
        }

        //------------日志
        System.out.println("删除教师 " + teacherId);

        int row = teacherInfoService.delTeacher(teacherId);

        return "redirect:/teachers";
    }

}
