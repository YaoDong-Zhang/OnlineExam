package online.exam.service.impl;

import online.exam.dao.ExamHistoryInfoDao;
import online.exam.pojo.ExamHistoryInfo;
import online.exam.pojo.ExamHistoryPaper;
import online.exam.service.ExamHistoryInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 19:49
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ExamHistoryInfoServiceImpl implements ExamHistoryInfoService {
    @Autowired
    private ExamHistoryInfoDao examHistoryInfoDao;

    public List<ExamHistoryInfo> findExamHistoryToTeacher() {
        return examHistoryInfoDao.findExamHistoryToTeacher();
    }

    public List<ExamHistoryPaper> findExamHistoryToStudent(Integer studentId) {
        return examHistoryInfoDao.findExamHistoryToStudent(studentId);
    }

    public int addExamHistoryInfo(Map<String, Object> map) {
        return examHistoryInfoDao.addExamHistory(map);
    }

    public int findHistoryInfoWithIds(Map<String, Object> map) {
        return examHistoryInfoDao.findHistoryInfoWithIds(map);
    }
}
