package online.exam.controller;

import com.google.gson.Gson;
import online.exam.pojo.*;
import online.exam.service.*;
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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/1 17:06
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class StudentController {
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private StudentInfo studentInfo;
    @Autowired
    private ClassInfo classInfo;
    @Autowired
    private GradeInfo grade;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private Gson gson;
    @Autowired
    private ExamSubjectMiddleInfo esm;
    @Autowired
    private ExamSubjectMiddleInfoService examSubjectMiddleInfoService;
    @Autowired
    private ExamPaperInfo examPaper;
    @Autowired
    private ExamChooseInfoService examChooseInfoService;
    @Autowired
    private ExamHistoryInfoService examHistoryInfoService;

    /**
     * 学生考试登录验证
     * <p>
     * 此处验证并不合理 登录验证实现如下：
     * 前台学生登录传入账户，后台根据账户获取学生密码
     * 返回学生密码，前台登录焦点离开密码框使用 JavaScript 判断
     *
     * @param studentAccount 学生登录账户
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/validateLoginStudent", method = RequestMethod.POST)
    public void validateLoginStudent(@RequestParam("studentAccount") String studentAccount,
                                     HttpServletResponse response) throws IOException {
        //------------日志
        System.out.println("学生账户 " + studentAccount + "，尝试登录考试");
        //获取需要登录的学生对象
        StudentInfo student = studentInfoService.findByAccount(studentAccount);
        if (student == null) {
            //------------日志
            System.out.println("登录学生账户 " + studentAccount + " 不存在");
            response.getWriter().print("n");
        } else {
            //------------日志
            System.out.println("登录学生账户 " + studentAccount + " 存在");
            response.getWriter().print(student.getStudentPwd());
        }
    }

    /**
     * 学生登录考试
     *
     * @param student 登录学生
     * @param request
     * @return
     */
    @RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
    public ModelAndView studentLogin(StudentInfo student, HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        StudentInfo loginStudent = studentInfoService.findByAccount(student.getStudentAccount());
        request.getSession().setAttribute("loginStudent", loginStudent);
        System.out.println(loginStudent);
        model.setViewName("reception/suc");
        model.addObject("success", "登录成功");
        //------------日志
        System.out.println("学生账户 " + loginStudent.getStudentAccount() + "，登录考试");
        return model;
    }

    /**
     * 预注册学生处理
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/preAddStu", method = RequestMethod.POST)
    public void preAddStu(HttpServletResponse response) throws IOException {
        List<ClassInfo> classInfos = classInfoService.findAll(new ClassInfo());

        String json = gson.toJson(classInfos);
        response.getWriter().print(json);
    }

    /**
     * 学生注册验证
     *
     * @param studentAccount
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/validateAccount", method = RequestMethod.POST)
    public void validateRegistStudent(@RequestParam("studentAccount") String studentAccount,
                                      HttpServletResponse response) throws IOException {
        StudentInfo studentInfo = studentInfoService.findByAccount(studentAccount);
        if (studentInfo != null) {
            response.getWriter().print("f");
        }
    }

    /**
     * 学生注册
     *
     * @param studentName
     * @param studentAccount
     * @param studentPwd
     * @param classId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/studentReg", method = RequestMethod.POST)
    private void studentReg(@RequestParam("name") String studentName,
                            @RequestParam("account") String studentAccount,
                            @RequestParam("pwd") String studentPwd,
                            @RequestParam("classId") Integer classId, HttpServletResponse response)
            throws IOException {
        studentInfo.setStudentName(studentName);
        studentInfo.setStudentAccount(studentAccount);
        studentInfo.setStudentPwd(studentPwd);
        classInfo.setClassId(classId);
        studentInfo.setClassInfo(classInfo);
        int row = studentInfoService.regist(studentInfo);
        if (row > 0) {
            response.getWriter().print("t");
            //------------日志
            System.out.println("账户 " + studentAccount + "，注册成功");
        }
    }

    /**
     * 学生退出系统,redirect:index.jsp用于重定向，效果相同
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/exit")
    public String exit(HttpSession session) {
        studentInfo = (StudentInfo) session.getAttribute("loginStudent");
        //------------日志
        System.out.println("学生账户 " + studentInfo + ",退出系统");
        session.removeAttribute("loginStudent");
//        return "index";
        return "redirect:index.jsp";
    }

    /**
     * 首页点击头像显示学生信息，注意 @PathVariable
     *
     * @param studentId
     * @return
     */
    @RequestMapping("/self/{studentId}")
    public ModelAndView selfInfo(@PathVariable("studentId") Integer studentId) {
        StudentInfo stu = studentInfoService.findByID(studentId);
        ModelAndView model = new ModelAndView();
        model.setViewName("/reception/self");
        model.addObject("self", stu);
        return model;
    }

    /**
     * 学生修改密码,注意 @PathVariable
     *
     * @param pwd
     * @param studentId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/reset/{pwd}/{studentId}")
    public void isResetPwd(
            @PathVariable("pwd") String pwd,
            @PathVariable("studentId") Integer studentId,
            HttpServletResponse response) throws IOException {
        studentInfo.setStudentId(studentId);
        studentInfo.setStudentPwd(pwd);

        int row = studentInfoService.resetPwd(studentInfo);
        if (row > 0) {
            //------------日志
            System.out.println("学生ID " + studentId + "，修改密码");
            response.getWriter().print("t");
        } else {
            response.getWriter().print("f");
        }
    }

    /**
     * 获取所有学生
     *
     * @param studentId
     * @param classId
     * @param gradeId
     * @param startPage
     * @param pageShow
     * @return
     */
    @RequestMapping("/students")
    public ModelAndView students(@RequestParam(value = "studentId", required = false) Integer studentId,
                                 @RequestParam(value = "classId", required = false) Integer classId,
                                 @RequestParam(value = "gradeId", required = false) Integer gradeId,
                                 @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,
                                 @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow) {
        //------------日志
        System.out.println("获取学生集合  classId=" + classId + ", gradeId=" + gradeId + ", startPage=" + startPage + ", pageShow=" + pageShow);
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/students");

        //查询条件处理
        StudentInfo student = new StudentInfo();
        if (studentId != null)
            student.setStudentId(studentId);
        if (classId != null) {
            classInfo.setClassId(classId);
            student.setClassInfo(classInfo);
        }
        if (gradeId != null) {
            grade.setGradeId(gradeId);
            student.setGrade(grade);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        //计算当前查询起始数据索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("student", student);
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);
        List<StudentInfo> students = studentInfoService.findAll(map);
        model.addObject("students", students);

        //获取学生总量
        int studentTotal = studentInfoService.stuSum();
        //计算总页数
        int pageTotal = 1;
        if (studentTotal % pageShow == 0)
            pageTotal = studentTotal / pageShow;
        else
            pageTotal = studentTotal / pageShow + 1;
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        return model;
    }

    /**
     * 预修改学生处理
     *
     * @param studentId
     * @return
     */
    @RequestMapping("/student/{studentId}")
    public ModelAndView updateStu(@PathVariable("studentId") Integer studentId) {
        //------------日志
        System.out.println("预修改学生:" + studentId);
        ModelAndView model = new ModelAndView("/admin/studentedit");

        studentInfo = studentInfoService.findByID(studentId);
        System.out.println(studentInfo);
        model.addObject("student", studentInfo);
        List<ClassInfo> classInfos = classInfoService.findAll(null);
        model.addObject("classes", classInfos);

        return model;
    }

    /**
     * 修改学生信息
     *
     * @param studentId
     * @param isUpdate
     * @param studentName
     * @param studentAccount
     * @param studentPwd
     * @param classId
     * @return
     */
    @RequestMapping(value = "/student/student", method = RequestMethod.POST)
    public String isUpdateOrAddCourse(
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam(value = "isupdate", required = false) Integer isUpdate,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam("studentAccount") String studentAccount,
            @RequestParam("studentPwd") String studentPwd,
            @RequestParam("classId") Integer classId) {

        studentInfo.setStudentId(studentId);
        studentInfo.setStudentName(studentName);
        studentInfo.setStudentAccount(studentAccount);
        studentInfo.setStudentPwd(studentPwd);
        classInfo.setClassId(classId);
        studentInfo.setClassInfo(classInfo);

        //------------日志
        System.out.println("修改学生 " + studentInfo + " 的信息");
        studentInfoService.updateStu(studentInfo);

        return "redirect:/students";
    }

    /**
     * 删除学生
     *
     * @param studentId
     * @return
     */
    @RequestMapping("/studentdel/{studentId}")
    public String delStu(@PathVariable("studentId") Integer studentId) {
        studentInfoService.delStu(studentId);
        //------------日志
        System.out.println("删除学生" + studentId);
        return "redirect:/students";
    }

    /**
     * 学生回顾试卷  --  后台教师查看也调用此方法
     *
     * @param studentId
     * @param examPaperId
     * @param score
     * @param examPaperName
     * @param studentName   后台教师查看需传入学生姓名
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/review")
    public ModelAndView reViewExam(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam("score") Integer score,
            @RequestParam("examPaperName") String examPaperName,
            @RequestParam(value = "studentName", required = false) String studentName) throws UnsupportedEncodingException {
        ModelAndView model = new ModelAndView();
        if (studentId == null) {
            model.addObject("error", "请先登录后再操作");
            model.setViewName("error");
            return model;
        } else {
            //获取当前试卷的试题集合  -- 前台判断需要
            examPaper.setExamPaperId(examPaperId);
            esm.setExamPaper(examPaper);
            List<ExamSubjectMiddleInfo> esms = examSubjectMiddleInfoService.findSubject(esm);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("studentId", studentId);
            map.put("examPaperId", examPaperId);

            //获取当前回顾试卷 试题、选择答案 信息
            List<ExamChooseInfo> reviews = examChooseInfoService.getChooseInfoWithExamSubject(map);
            //------------日志
            System.out.println("学生 " + studentId + " 回顾试卷 " + examPaperId + " 试题数量 " + reviews.size());
            //设置试卷名称、试卷总分
            model.addObject("examPaperName", examPaperName);
            model.addObject("score", score);

            model.setViewName("reception/review");
            model.addObject("views", reviews);

            model.addObject("esms", esms);
            if (studentName != null) model.addObject("studentName", studentName);

            return model;
        }
    }

    /**
     * 获取学生历史考试记录
     *
     * @param studentId 学生编号
     * @return
     */
    @RequestMapping("/history/{studentId}")
    public ModelAndView getExamHistoryInfo(@PathVariable("studentId") Integer studentId) {
        ModelAndView model = new ModelAndView();

        if (studentId == null) {
            //------------日志
            System.out.println("学生编号 为空");
            model.setViewName("error");
            return model;
        }
        //------------日志
        System.out.println("学生 " + studentId + " 获取考试历史记录");
        //获取历史考试信息记录集合
        List<ExamHistoryPaper> ehps = examHistoryInfoService.findExamHistoryToStudent(studentId);
        model.addObject("ehps", ehps);
        model.setViewName("/reception/examHistory");

        return model;
    }

    /**
     * 学生进入考试
     *
     * @param classId     班级编号
     * @param examPaperId 试卷编号
     * @param studentId   考生编号
     * @param examTime    考试时间
     * @param beginTime   考试开始时间
     * @param gradeId     年级编号
     * @param session
     * @return
     */
    @RequestMapping("/begin")
    public ModelAndView beginExam(
            @RequestParam("classId") Integer classId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam(value = "studentId", required = false) Integer studentId,
            @RequestParam("examTime") Integer examTime,
            @RequestParam("beginTime") String beginTime,
            @RequestParam("gradeId") Integer gradeId,
            HttpSession session) {
        ModelAndView model = new ModelAndView();

        /*
         * 查询该考试当前进入的试卷是否已经在历史记录中存在
         * 如果存在，则不能再次进入考试； 反之进入考试
         */
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", studentId);
        map.put("examPaperId", examPaperId);
        int count = examHistoryInfoService.findHistoryInfoWithIds(map);
        if (session.getAttribute("loginStudent") == null) {
            model.addObject("error", "请先登录后再操作");
            model.setViewName("error");
            return model;
        } else if (count >= 1) {
            model.addObject("error", "你已经考试过了");
            model.setViewName("error");
            return model;
        } else {
            //------------日志
            System.out.println("学生 " + studentId + " 进入考试 班级 " + classId + " 试卷 " + examPaperId);
            model.setViewName("/reception/exam");
            ExamPaperInfo examPaper = new ExamPaperInfo();
            examPaper.setExamPaperId(examPaperId);
            esm.setExamPaper(examPaper);
            //获取试卷 试题集合
            List<ExamSubjectMiddleInfo> esms = examSubjectMiddleInfoService.findSubject(esm);
            //------------日志
            System.out.println("考试试题总量 " + esms.size());

            //获取当前考生在当前试卷中已选答案记录
            Map<String, Object> choosedMap = new HashMap<String, Object>();
            choosedMap.put("studentId", studentId);
            choosedMap.put("examPaperId", examPaperId);
            List<ExamChooseInfo> chooses = examChooseInfoService.getChooseInfoWithSumScore(choosedMap);
            if (chooses == null || chooses.size() == 0) {
                model.addObject("chooses", null);
            } else {
                model.addObject("chooses", chooses);
            }

            model.addObject("esms", esms);
            model.addObject("sumSubject", esms.size());
            model.addObject("examPaperId", examPaperId);
            model.addObject("examTime", examTime);
            model.addObject("beginTime", beginTime);
            model.addObject("classId", classId);
            model.addObject("gradeId", gradeId);

            return model;
        }
    }

    /**
     * 考生提交考试
     *
     * @param studentId
     * @param examPaperId
     * @param classId
     * @param gradeId
     * @return
     */
    @RequestMapping(value = "/submit", method = {RequestMethod.POST, RequestMethod.GET})
    public String examSubmit(
            @RequestParam("studentId") Integer studentId,
            @RequestParam("examPaperId") Integer examPaperId,
            @RequestParam("classId") Integer classId,
            @RequestParam("gradeId") Integer gradeId) {
        //------------日志
        System.out.println("学生 " + studentId + " 提交了试卷 " + examPaperId);

        //获取当前学生当前试卷所选择的全部答案
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("studentId", studentId);
        map.put("examPaperId", examPaperId);
        List<ExamChooseInfo> chooses = examChooseInfoService.getChooseInfoWithSumScore(map);
        //------------日志
        System.out.println("学生 " + studentId + " 共选择了 " + chooses.size() + " 道题");

        //总分记录
        int sumScore = 0;
        for (ExamChooseInfo choose : chooses) {
            SubjectInfo subject = choose.getSubject();
            String chooseResult = choose.getChooseResult();
            String rightResult = subject.getRightResult();

            if (chooseResult.equals(rightResult)) {    //答案正确
                sumScore += subject.getSubjectScore();
                //------------日志
                System.out.println("学生 " + studentId + " 第 " + subject.getSubjectId() + " 选择正确答案 " + chooseResult + " 当前总分 " + sumScore);
            } else {
                //------------日志
                System.out.println("学生 " + studentId + " 第 " + subject.getSubjectId() + " 答案选择错误 " + chooseResult + " 正确答案为 " + rightResult + " 当前总分 " + sumScore);
            }
        }

        /*
         * 首先判断当前记录是否已经添加过
         * 防止当前学生点击提交后，系统倒计时再次进行提交
         */
        int count = examHistoryInfoService.findHistoryInfoWithIds(map);

        if (count == 0) {
            //添加到历史记录
            map.put("examScore", sumScore);
            int row = examHistoryInfoService.addExamHistoryInfo(map);
            //------------日志
            System.out.println("学生 " + studentId + " 提交的试卷 " + examPaperId + " 已成功处理，并添加到历史记录中");
        }

        return "redirect:willexams?gradeId=" + gradeId + "&classId=" + classId + "&studentId=" + studentId;
    }

}
