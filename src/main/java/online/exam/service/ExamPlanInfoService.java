package online.exam.service;

import online.exam.pojo.ExamPlanInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:36
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamPlanInfoService {
    public List<ExamPlanInfo> findAll(Map<String, Object> map);

    public int addExamPlan(ExamPlanInfo examPlanInfo);

    public int upadteExamPlan(ExamPlanInfo examPlanInfo);

    public int delExamPlan(Integer id);

    public ExamPlanInfo findById(Integer id);

    public List<ExamPlanInfo> getStudentWillExam(Map<String, Object> map);
}
