package online.exam.dao;

import online.exam.pojo.ExamHistoryInfo;
import online.exam.pojo.ExamHistoryPaper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:48
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamHistoryInfoDao {
    public List<ExamHistoryInfo> findExamHistoryToTeacher();

    public List<ExamHistoryPaper> findExamHistoryToStudent(Integer studentId);

    public int addExamHistory(Map<String, Object> map);

    public int findHistoryInfoWithIds(Map<String, Object> map);
}
