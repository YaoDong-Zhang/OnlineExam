package online.exam.controller;

import online.exam.pojo.ExamPaperInfo;
import online.exam.pojo.GradeInfo;
import online.exam.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 18:33
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class ExamPaperController {
    @Autowired
    private ExamPaperInfo examPaperInfo;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private GradeInfo grade;

    /**
     * 获取所有试卷
     *
     * @param gradeId
     * @param startPage
     * @param pageShow
     * @return
     */
    @RequestMapping("/examPapers")
    private ModelAndView examPapers(@RequestParam(value = "gradeId", required = false) Integer gradeId,
                                    @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,
                                    @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow) {
        //------------日志
        System.out.println("获取试卷集合  gradeId=" + gradeId + ", startPage=" + startPage + ", pageShow=" + pageShow);
        ModelAndView model = new ModelAndView();
        model.setViewName("/admin/examPapers");
        if (gradeId != null) {
            grade.setGradeId(gradeId);
            examPaperInfo.setGrade(grade);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //计算当前查询起始数据索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("examPaper", examPaperInfo);
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);
        List<ExamPaperInfo> examPaperInfos = examPaperService.findAll(map);
        model.addObject("examPapers", examPaperInfos);

        //获取试卷总量
        int examPaperTotal = examPaperService.examPaperSum();
        //计算总页数
        int pageTotal = 1;
        if (examPaperTotal % pageShow == 0) {
            pageTotal = examPaperTotal / pageShow;
        } else {
            pageTotal = examPaperTotal / pageShow + 1;
        }
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        return model;
    }

    /**
     * 预修改试卷处理
     *
     * @param examPaperId
     * @return
     */
    @RequestMapping("examPaper/{examPaperId}")
    public ModelAndView preUdateExampaper(@PathVariable("examPaperId") Integer examPaperId) {
        //------------日志
        System.out.println("预修改试卷" + examPaperId);
        ModelAndView model = new ModelAndView("/admin/examPaperedit");
        examPaperInfo = examPaperService.findById(examPaperId);
        System.out.println(examPaperInfo);
        model.addObject("examPaper", examPaperInfo);
        return model;
    }

    /**
     * 修改、添加试卷信息
     *
     * @param examPaperId
     * @param isUpdate
     * @param examPaperName
     * @param subjectNum
     * @param examPaperScore
     * @param examPaperTime
     * @param division
     * @param examPaperEasy
     * @param gradeId
     * @return
     */
    @RequestMapping(value = "/examPaper/examPaper", method = RequestMethod.POST)
    public String isUpdateOrAddCourse(
            @RequestParam(value = "examPaperId", required = false) Integer examPaperId,
            @RequestParam(value = "isupdate", required = false) Integer isUpdate,
            @RequestParam(value = "examPaperName", required = false) String examPaperName,
            @RequestParam("subjectNum") Integer subjectNum,
            @RequestParam("examPaperScore") Integer examPaperScore,
            @RequestParam("examPaperTime") Integer examPaperTime,
            @RequestParam("division") Integer division,
            @RequestParam("examPaperEasy") Integer examPaperEasy,
            @RequestParam("gradeId") Integer gradeId) {

        examPaperInfo.setExamPaperId(examPaperId);
        examPaperInfo.setExamPaperName(examPaperName);
        examPaperInfo.setSubjectNum(subjectNum);
        examPaperInfo.setExamPaperScore(examPaperScore);
        examPaperInfo.setExamPaperTime(examPaperTime);
        examPaperInfo.setDivision(division);
        examPaperInfo.setExamPaperEasy(examPaperEasy);
        grade.setGradeId(gradeId);
        examPaperInfo.setGrade(grade);

        if (isUpdate != null) {
            //------------日志
            System.out.println("修改试卷 " + examPaperInfo + " 的信息");
            int row = examPaperService.updateExamPaper(examPaperInfo);
        } else {
            //------------日志
            System.out.println("添加试卷 " + examPaperInfo + " 的信息");
            int row = examPaperService.addExamPaper(examPaperInfo);
        }

        return "redirect:/examPapers";
    }

    @RequestMapping("examPaperdel/{examPaperId}")
    public String delExamPaper(@PathVariable("examPaperId") Integer examPaperId) {
        //------------日志
        System.out.println("删除试卷 " + examPaperId);
        examPaperService.delExamPaper(examPaperId);
        return "redirect:/examPapers";
    }

}
