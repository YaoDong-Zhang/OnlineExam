package online.exam.service;

import online.exam.pojo.ExamHistoryInfo;
import online.exam.pojo.ExamHistoryPaper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/14 18:03
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamHistoryInfoService {
    public List<ExamHistoryInfo> findExamHistoryToTeacher();

    public List<ExamHistoryPaper> findExamHistoryToStudent(Integer studentId);

    public int addExamHistoryInfo(Map<String, Object> map);

    public int findHistoryInfoWithIds(Map<String, Object> map);
}
