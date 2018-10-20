package online.exam.controller;

import online.exam.service.ExamPaperService;
import online.exam.service.StudentInfoService;
import online.exam.service.SubjectInfoService;
import online.exam.service.TeacherInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: OnlineExam
 * @Date: 2018/10/15 10:35
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class AdminHomeHandler {
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private SubjectInfoService subjectInfoService;
    @Autowired
    private TeacherInfoService teacherInfoService;
    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping("/homeInfo")
    public void homeInfo(HttpServletResponse response) throws IOException {
        //------------日志
        System.out.println("加载后台首页相关数据");

        int examPaperTotal = examPaperService.examPaperSum();
        int subjectTotal = subjectInfoService.subjectSum();
        int teacherTotal = teacherInfoService.teacherSum();
        int studentTotal = studentInfoService.stuSum();

        String json = "{\"examPaperTotal\":"+examPaperTotal+", " +
                "\"subjectTotal\":"+subjectTotal+", " +
                "\"teacherTotal\":"+teacherTotal+", " +
                "\"studentTotal\":"+studentTotal+"}";

        response.getWriter().print(json);
    }
}
