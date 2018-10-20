package online.exam.controller;

import online.exam.pojo.ExamHistoryInfo;
import online.exam.service.ExamHistoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:51
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class ExamHistoryInfoController {
    @Autowired
    private ExamHistoryInfoService examHistoryInfoService;

    /**
     * 教师查询考试记录
     *
     * @return
     */
    @RequestMapping("/historys")
    public ModelAndView examHistorys() {
        List<ExamHistoryInfo> historys = examHistoryInfoService.findExamHistoryToTeacher();
        ModelAndView model = new ModelAndView("admin/examHistorys");
        //------------日志
        System.out.println("教师 查询历史考试信息 SIZE " + historys.size());

        model.addObject("historys", historys);

        return model;
    }

}
