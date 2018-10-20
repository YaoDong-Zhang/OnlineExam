package online.exam.controller;

import online.exam.pojo.CourseInfo;
import online.exam.pojo.ExamPaperInfo;
import online.exam.pojo.GradeInfo;
import online.exam.pojo.SubjectInfo;
import online.exam.service.*;
import online.exam.util.SubjectImportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/7 13:23
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class SubjectController {
    @Autowired
    private SubjectInfo subjectInfo;
    @Autowired
    private SubjectInfoService subjectInfoService;
    @Autowired
    private CourseInfo courseInfo;
    @Autowired
    private GradeInfo gradeInfo;
    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private GradeInfoService gradeInfoService;
    @Autowired
    private ExamPaperService examPaperService;
    @Autowired
    private ExamPaperInfo examPaper;
    @Autowired
    private GradeInfo grade;
    @Autowired
    private ExamSubjectMiddleInfoService esmService;

    /**
     * 获取所有试题
     *
     * @param subjectId
     * @param courseId
     * @param gradeId
     * @param startPage
     * @param pageShow
     * @param handAdd
     * @param examPaperId
     * @param session
     * @return
     */
    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ModelAndView getTeachers(
            @RequestParam(value = "subjectId", required = false) Integer subjectId,
            @RequestParam(value = "courseId", required = false) Integer courseId,
            @RequestParam(value = "gradeId", required = false) Integer gradeId,
            @RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage,
            @RequestParam(value = "pageShow", required = false, defaultValue = "10") Integer pageShow,
            @RequestParam(value = "handAdd", required = false) Integer handAdd,
            @RequestParam(value = "examPaperId", required = false) Integer examPaperId,
            HttpSession session) {
        //------------日志
        System.out.println("查询试题集合");

        ModelAndView model = new ModelAndView();
        model.setViewName("admin/subjects");

        //条件处理
        if (subjectId != null) subjectInfo.setSubjectId(subjectId);
        if (courseId != null) courseInfo.setCourseId(courseId);
        if (gradeId != null) gradeInfo.setGradeId(gradeId);

        Map<String, Object> map = new HashMap<String, Object>();
        //计算当前查询起始数据索引
        int startIndex = (startPage - 1) * pageShow;
        map.put("subject", subjectInfo);
        map.put("startIndex", startIndex);
        map.put("pageShow", pageShow);
        List<SubjectInfo> subjects = subjectInfoService.findAll(map);
        model.addObject("subjects", subjects);

        //获取试题总量
        int subjectTotal = subjectInfoService.subjectSum();
        //计算总页数
        int pageTotal = 1;
        if (subjectTotal % pageShow == 0)
            pageTotal = subjectTotal / pageShow;
        else
            pageTotal = subjectTotal / pageShow + 1;
        model.addObject("pageTotal", pageTotal);
        model.addObject("pageNow", startPage);

        //是否为需要进行手动添加试题到试卷而发起的请求
        if (handAdd != null && handAdd == 1) {
            model.addObject("handAdd", "1");
        }
        //如果是手动添加试题到试卷，则需要返回试卷编号, 且返回当前已经选择试题数量
        if (examPaperId != null) {
            model.addObject("examPaperId", examPaperId);
            List<String> ids = (List<String>) session.getAttribute("ids");
            if (ids == null) {
                model.addObject("choosed", 0);
            } else {
                model.addObject("choosed", ids.size());
            }
        }
        return model;
    }

    /**
     * 添加试题
     *
     * @param subject
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/addSubject", method = RequestMethod.POST)
    public void addSubject(SubjectInfo subject, HttpServletResponse response) throws IOException {
        int row = subjectInfoService.addSubject(subject);

        response.getWriter().print("试题添加成功!");
    }

    /**
     * 预修改试题
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("/subject/{subjectId}")
    public ModelAndView updateSubject(@PathVariable("subjectId") Integer subjectId) {
        //------------日志
        System.out.println("修改试题 " + subjectId + " 的信息(获取试题信息)");

        SubjectInfo subject = subjectInfoService.findById(subjectId);

        ModelAndView model = new ModelAndView("/admin/subject-test");
        model.addObject("subject", subject);

        return model;
    }

    /**
     * 修改试题
     *
     * @param subject
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
    public void updateSubject(SubjectInfo subject, HttpServletResponse response) throws IOException {
        //------------日志
        System.out.println("修改试题 " + subject.getSubjectId() + " 的信息(正式)");

        int row = subjectInfoService.updateSubject(subject);
        if (row > 0) {
            response.getWriter().print("试题修改成功!");
        } else {
            response.getWriter().print("试题修改失败!");
        }
    }

    /**
     * 删除试题
     *
     * @param subjectId
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/delSubject", method = RequestMethod.POST)
    public void delSubject(@RequestParam("subjectId") Integer subjectId,
                           HttpServletResponse response) throws IOException {
        //------------日志
        System.out.println("删除试题 " + subjectId);

        int row = subjectInfoService.delSubject(subjectId);

        if (row > 0) {
            response.getWriter().print("t");
        } else {
            response.getWriter().print("f");
        }
    }

    /**
     * 导入试题
     *
     * @return
     */
    @RequestMapping(value = "/initImport")
    public ModelAndView initImportExcel() {
        //------------日志
        System.out.println("初始化 导入 EXCEL 试题信息");
        ModelAndView model = new ModelAndView("admin/importSubject");
        //获取所有科目
        List<CourseInfo> courses = courseInfoService.findAll(null);
        //获取所有年级
        List<GradeInfo> grades = gradeInfoService.findAll();
        //获取所有试卷名称
        List<ExamPaperInfo> examPapers = examPaperService.findExamPapers();

        model.addObject("courses", courses);
        model.addObject("grades", grades);
        model.addObject("examPapers", examPapers);

        return model;
    }

    /**
     * 试题导入 处理
     *
     * @param request
     * @param importOption
     * @param excel
     */
    @RequestMapping(value = "/dispatcherUpload", method = RequestMethod.POST)
    public ModelAndView dispatcherUpload(HttpServletRequest request,
                                         @RequestParam("division") Integer division,
                                         @RequestParam("courseId") Integer courseId,
                                         @RequestParam("gradeId") Integer gradeId,
                                         @RequestParam("examPaperId") Integer examPaperId,
                                         @RequestParam("importOption") String importOption,
                                         @RequestParam("examPaperEasy") Integer examPaperEasy,
                                         @RequestParam("examPaperName") String examPaperName,
                                         @RequestParam("examPaperTime") Integer examPaperTime,
                                         @RequestParam("inputfile") MultipartFile excel) {
        ModelAndView model = new ModelAndView("reception/suc");
        String savePath = "";

        try {
            /** 保存上传 excel 文件 */
            savePath = saveUploadFile(excel, request.getRealPath("/WEB-INF/upload"));

            /** 解析上传 excel 文件, 得到试题集合 */
            List<SubjectInfo> subjects = SubjectImportUtil.parseSubjectExcel(savePath, courseId, gradeId, division);
            /** 只添加试题 */
            if ("0".equals(importOption)) {
                Map<String, Object> subjectsMap = new HashMap<String, Object>();

                subjectsMap.put("subjects", subjects);

                importSubejctOnly(subjects, subjectsMap);
            }
            /** 添加试题到指定的已有试卷 */
            else if ("1".equals(importOption)) {
                dispatcherExamPaperAndSubject(subjects, examPaperId);
            }
            /** 添加试题到新建试卷 */
            else if ("2".equals(importOption)) {
                /** 创建新试卷 */
                examPaper.setExamPaperName(examPaperName);
                examPaper.setExamPaperEasy(examPaperEasy);
                examPaper.setExamPaperTime(examPaperTime);
                grade.setGradeId(gradeId);
                examPaper.setGrade(grade);
                examPaper.setDivision(division);
                int row = examPaperService.addExamPaper(examPaper);
                examPaper.setExamPaperId(examPaperService.maxPaperId());
                //------------日志
                System.out.println("添加的新试卷 编号 " + examPaper.getExamPaperId());

                dispatcherExamPaperAndSubject(subjects, examPaper.getExamPaperId());
            }

            if (subjects.size() == 0) {
                model.addObject("success", "操作处理失败，共添加 <b style='color:red;'>" + subjects.size() + "</b> 道题, 请检查上传数据正确性!");
            } else {
                model.addObject("success", "操作处理成功，共添加 " + subjects.size() + " 道题");
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.setViewName("error");
            model.addObject("error", "上传失败, 请检查上传数据合理性或联系管理员!");
        } finally {
            /** 删除上传文件 */
            deleteUploadFile(savePath);
        }
        return model;
    }

    /**
     * 保存上传 excel 文件
     *
     * @param file 上传文件
     * @return 保存路径
     */
    private String saveUploadFile(MultipartFile file, String rootPath) {
        String fileName = file.getOriginalFilename();
        //------------日志
        System.out.println("保存上传文件 " + fileName + " 到 " + rootPath);

        try {
            file.transferTo(new File(rootPath + "/" + fileName));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("ill");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("io");
        }

        return rootPath + "/" + fileName;
    }

    /**
     * 只将试题上传到数据库
     *
     * @param subjects
     * @param subjectsMap
     */
    private void importSubejctOnly(List<SubjectInfo> subjects, Map<String, Object> subjectsMap) {
        try {
            if (subjects != null && subjects.size() > 0) {
                //添加试题
                for (SubjectInfo subjectInfo : subjects){
                    subjectInfoService.addSubject(subjectInfo);
                }
//                int row = subjectInfoService.addSubjects(subjectsMap);
                //------------日志
                System.out.println("只将 excel 中的试题添加到数据库成功 SIZE " + subjects.size());
            } else {
                //------------日志
                System.out.println("上传试题文件中不存在试题，或解析失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理 试题 添加到 试卷
     *
     * @param subjects    试题集合
     * @param examPaperId 对应试卷编号
     */
    private void dispatcherExamPaperAndSubject(List<SubjectInfo> subjects, Integer examPaperId) {
        List<Integer> subjectIds = new ArrayList<Integer>();
        //试题总量统计
        int count = 0;
        //试题总分统计
        int score = 0;

        /** 添加试题 */
        for (SubjectInfo subjectInfo : subjects) {
            int row1 = subjectInfoService.addSubject(subjectInfo);
            score += subjectInfo.getSubjectScore();
            subjectIds.add(subjectInfo.getSubjectId());
            count++;
        }
        //------------日志
        System.out.println("添加试题 SIZE " + count);

        /** 添加试题到试卷 */
        Map<String, Object> esmMap = new HashMap<String, Object>();
        esmMap.put("examPaperId", examPaperId);
        esmMap.put("subjectIds", subjectIds);
        esmService.addESM(esmMap);
        //------------日志
        System.out.println("添加试题 SIZE " + count + " SCORE " + score + " 到试卷 " + examPaperId);

        //修改试卷信息
        Map<String, Object> scoreWithNum = new HashMap<String, Object>();
        scoreWithNum.put("subjectNum", count);
        scoreWithNum.put("score", score);
        scoreWithNum.put("examPaperId", examPaperId);
        /** 修改试卷总分 */
        examPaperService.updateExamPaperScore(scoreWithNum);
        /** 修改试卷试题总量 */
        examPaperService.updateExamPaperSubjects(scoreWithNum);
    }

    /**
     * 删除上传文件
     *
     * @param filePath 文件路径
     */
    private void deleteUploadFile(String filePath) {
        File file = new File(filePath);

        if (file.exists()) {
            file.delete();
            //------------日志
            System.out.println("上传文件已被删除 " + filePath);
        }
    }
}
