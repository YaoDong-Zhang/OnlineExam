package online.exam.controller;

import com.google.gson.Gson;
import online.exam.charts.StudentExamInfoCharts;
import online.exam.pojo.ClassInfo;
import online.exam.pojo.StudentExamInfo;
import online.exam.pojo.StudentInfo;
import online.exam.service.ClassInfoService;
import online.exam.service.StudentExamInfoService;
import online.exam.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 10:54
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class StudentExamInfoController {
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private Gson gson;
    @Autowired
    private StudentExamInfoService studentExamInfoService;

    /**
     * 获取班级中的所有学生
     * @param teacherId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/stus")
    public void getStudentsByClassId(@RequestParam("tid") Integer teacherId, HttpServletResponse response) throws IOException {
        if (teacherId == null) {
            response.getWriter().print("TID-NULL");
        } else {
            //获取当前班主任对应的班级
            ClassInfo classInfo = classInfoService.findByTeacherId(teacherId);
            //获取所有学生信息
            List<StudentInfo> stus = studentInfoService.findByClassId(classInfo.getClassId());

            response.getWriter().print(gson.toJson(stus));
        }
    }
    /**
     * 班级下所有学生考试平均分等信息 图表 Json 生成
     * @param teacherId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/avgcounts")
    public void getAllStudentAvgScoreCount(@RequestParam("tid") Integer teacherId, HttpServletResponse response) throws IOException {
        if (teacherId == null) {
            response.getWriter().print("TID-NULL");
        } else {
            //获取当前班主任对应的班级
            ClassInfo classInfo = classInfoService.findByTeacherId(teacherId);
            //获取所有学生信息 平均分等信息
            List<StudentExamInfo> stuExamInfos = studentExamInfoService.getAllStudentAvgScoreCount(classInfo.getClassId());

            response.getWriter().print(StudentExamInfoCharts.createAvgCountLineJson(stuExamInfos));
        }
    }

    @RequestMapping("/stuexam")
    public void getStudentExamInfoById(@RequestParam("stuId") Integer studentId, HttpServletResponse response) throws IOException {
        //获取学生考试信息
        List<StudentExamInfo> stuExamInfos = studentExamInfoService.getStudentExamInfo(studentId);

        response.getWriter().print(StudentExamInfoCharts.createStudentExamLineJson(stuExamInfos));
    }

    /**
     * 所有学生考试信息 图表 Json 字符串生成
     * @param teacherId
     * @param response
     * @throws IOException
     */
    @RequestMapping("/examCount")
    public void getStudentExamCount(@RequestParam("tid") Integer teacherId, HttpServletResponse response) throws IOException {
        if (teacherId == null) {
            response.getWriter().print("TID-NULL");
        } else {
            //获取当前班主任对应的班级
            ClassInfo classInfo = classInfoService.findByTeacherId(teacherId);
            //获取学生考试信息
            List<StudentExamInfo> stuExamInfos = studentExamInfoService.getStudentExamCountByClassId(classInfo.getClassId());

            response.getWriter().print(StudentExamInfoCharts.createExamCountBarJson(stuExamInfos));
        }
    }

}
