package online.exam.service.impl;

import online.exam.dao.ExamPlanInfoDao;
import online.exam.pojo.ExamPlanInfo;
import online.exam.service.ExamPlanInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:36
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ExamPlanInfoServiceImpl implements ExamPlanInfoService {
    @Autowired
    private ExamPlanInfoDao examPlanInfoDao;

    public List<ExamPlanInfo> findAll(Map<String, Object> map) {
        return examPlanInfoDao.findAll(map);
    }

    public int addExamPlan(ExamPlanInfo examPlanInfo) {
        return examPlanInfoDao.addExamPlan(examPlanInfo);
    }

    public int upadteExamPlan(ExamPlanInfo examPlanInfo) {
        return examPlanInfoDao.upadteExamPlan(examPlanInfo);
    }

    public int delExamPlan(Integer id) {
        return examPlanInfoDao.delExamPlan(id);
    }

    public ExamPlanInfo findById(Integer id) {
        return examPlanInfoDao.findById(id);
    }

    public List<ExamPlanInfo> getStudentWillExam(Map<String, Object> map) {
        return examPlanInfoDao.getStudentWillExam(map);
    }
}
