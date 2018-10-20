package online.exam.controller;

import online.exam.pojo.ClassInfo;
import online.exam.pojo.CourseInfo;
import online.exam.pojo.ExamPaperInfo;
import online.exam.pojo.ExamPlanInfo;
import online.exam.service.ClassInfoService;
import online.exam.service.CourseInfoService;
import online.exam.service.ExamPaperService;
import online.exam.service.ExamPlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:37
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class ExamPlanInfoController {
    @Autowired
    private ExamPlanInfoService examPlanInfoService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private ExamPaperService examPaperService;

    /**
     * 获取所有待考记录
     *
     * @return
     */
    @RequestMapping("/examPlans")
    public ModelAndView getExamPlans() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/examPlans");
        //------------日志
        System.out.println("获取待考考试信息");

        List<ExamPlanInfo> examPlans = examPlanInfoService.findAll(null);
        model.addObject("examPlans", examPlans);

        return model;
    }

    /**
     * 预添加
     *
     * @return
     */
    @RequestMapping("/preAddep")
    public ModelAndView preAddep() {
        ModelAndView model = new ModelAndView();
        model.setViewName("admin/examPlanedit");

        //获取所有班级信息
        List<ClassInfo> classes = classInfoService.findAll(null);
        model.addObject("classes", classes);
        //获取所有科目信息
        List<CourseInfo> courses = courseInfoService.findAll(null);
        model.addObject("courses", courses);
        //获取所有的试卷信息 -- 纯净的
        List<ExamPaperInfo> examPapers = examPaperService.findExamPapers();
        model.addObject("examPapers", examPapers);

        return model;
    }

    /**
     * 添加待考信息
     *
     * @param examPlan 考试安排记录信息
     * @return
     */
    @RequestMapping(value = "examPlan", method = RequestMethod.POST)
    public String isAddExamPlan(ExamPlanInfo examPlan) {
        //------------日志
        System.out.println("添加待考记录：" + examPlan);
        examPlanInfoService.addExamPlan(examPlan);

        return "redirect:examPlans";
    }

    /**
     * 预修改
     *
     * @param examPlanId 考试安排(待考)编号
     * @return
     */
    @RequestMapping(value = "/preUpdateep/{examPlanId}", method = RequestMethod.GET)
    public ModelAndView preUpdateep(@PathVariable("examPlanId") Integer examPlanId) {
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/examPlanedit");

        //获取所有班级信息
        List<ClassInfo> classes = classInfoService.findAll(null);
        model.addObject("classes", classes);
        //获取所有科目信息
        List<CourseInfo> courses = courseInfoService.findAll(null);
        model.addObject("courses", courses);
        //获取所有的试卷信息 -- 纯净的(简单的)
        List<ExamPaperInfo> examPapers = examPaperService.findExamPapers();
        model.addObject("examPapers", examPapers);
        //获取当前修改对象
        ExamPlanInfo examPlanWithUpdate = examPlanInfoService.findById(examPlanId);
        //------------日志
        System.out.println("获取要修改的待考记录：" + examPlanWithUpdate);
        model.addObject("examPlan", examPlanWithUpdate);

        return model;
    }

    /**
     * 修改待考信息
     *
     * @param examPlan 待考记录
     * @return
     */
    @RequestMapping(value = "preUpdateep/examPlan", method = RequestMethod.POST)
    public String isUpdateExamPlan(ExamPlanInfo examPlan) {
        //------------日志
        System.out.println("修改待考记录：" + examPlan);
        examPlanInfoService.upadteExamPlan(examPlan);

        return "redirect:../examPlans";
    }

    /**
     * 教师移除考试安排记录
     *
     * @param examPlanId
     * @return
     */
    @RequestMapping(value = "/del/{examPlanId}")
    public String isDelExamPlan(
            @PathVariable("examPlanId") Integer examPlanId) {
        //------------日志
        System.out.println("教师 移除考试安排 " + examPlanId);

        int row = examPlanInfoService.delExamPlan(examPlanId);

        return "redirect:../examPlans";
    }

    /**
     * 查询学生待考信息
     *
     * @param classId   学生所在班级编号
     * @param gradeId   学生所在年级百年好
     * @param studentId 学生编号
     * @return
     */
    @RequestMapping("/willexams")
    public ModelAndView getStudentWillExam(
            @RequestParam("classId") Integer classId,
            @RequestParam("gradeId") Integer gradeId,
            @RequestParam(value = "studentId", required = false) Integer studentId) {
        //------------日志
        System.out.println("查询学生 " + studentId + "(NULL-未指定)待考信息 班级：" + classId + ", 年级：" + gradeId);
        ModelAndView model = new ModelAndView();
        model.setViewName("/reception/examCenter");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classId", classId);
        map.put("gradeId", gradeId);

        List<ExamPlanInfo> examPlans = examPlanInfoService.getStudentWillExam(map);
        System.out.println(examPlans);

        model.addObject("examPlans", examPlans);
        model.addObject("gradeId", gradeId);

        return model;
    }

    /**
     * 定时刷新考试安排记录，将过期考试移除
     * 周一至周五 每隔15分钟刷新一次
     */
    @Scheduled(cron = "0 */15 * * * MON-FRI")
    public void refreshExamPlan() {
        List<ExamPlanInfo> examPlans = examPlanInfoService.findAll(null);
        //------------日志
        System.out.println("刷新待考记录, SIZE " + examPlans.size());
        if (examPlans.size() > 0) {
            for (ExamPlanInfo examPlanInfo : examPlans) {
                String beginTime = examPlanInfo.getBeginTime();
                int examPaperTime = examPlanInfo.getExamPaper().getExamPaperTime();
                /** 验证是否可移除 */
                if (validateExamPaerBeOverdue(beginTime, examPaperTime)) {
                    //------------日志
                    System.out.println("待考试卷 " + examPlanInfo.getExamPaper().getExamPaperId() + " 已经过期，即将移除");
                    //移除过期考试安排
                    int row = examPlanInfoService.delExamPlan(examPlanInfo.getExamPlanId());
                } else {
                    //------------日志
                    System.out.println("待考试卷 " + examPlanInfo.getExamPaper().getExamPaperId() + " 暂未过期，无法移除");
                    continue;
                }
            }
        }
    }

    /**
     * 验证试卷是否过期
     *
     * @param beginTime 考试开始时间
     * @param examTime  考试时间
     * @return
     */
    private boolean validateExamPaerBeOverdue(String beginTime, int examTime) {
        boolean flag = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date beginTimeDate = sdf.parse(beginTime);
            Long beginTimeTime = beginTimeDate.getTime();

            /** 转换考试时间为毫秒单位 */
            int examTimeSecond = examTime * 60 * 1000;

            Date nowDate = new Date();
            Long nowDateTime = nowDate.getTime();

            /** 当前时间超过了 考试结束时间，即为过期记录 */
            if (nowDateTime > (beginTimeTime + examTimeSecond)) {
                flag = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return flag;
    }

}
