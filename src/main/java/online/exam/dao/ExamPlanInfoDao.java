package online.exam.dao;

import online.exam.pojo.ExamPlanInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:35
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamPlanInfoDao {
    public List<ExamPlanInfo> findAll(Map<String, Object> map);

    public int addExamPlan(ExamPlanInfo examPlanInfo);

    public int upadteExamPlan(ExamPlanInfo examPlanInfo);

    public int delExamPlan(Integer id);

    public ExamPlanInfo findById(Integer id);

    //查询学生待考信息
    public List<ExamPlanInfo> getStudentWillExam(Map<String, Object> map);

}
