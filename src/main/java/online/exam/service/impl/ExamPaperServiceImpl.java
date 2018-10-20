package online.exam.service.impl;

import online.exam.dao.ExamPaperDao;
import online.exam.pojo.ExamPaperInfo;
import online.exam.service.ExamPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 18:22
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ExamPaperServiceImpl implements ExamPaperService {
    @Autowired
    private ExamPaperDao examPaperDao;

    public List<ExamPaperInfo> findAll(Map<String, Object> map) {
        return examPaperDao.findAll(map);
    }

    public int examPaperSum() {
        return examPaperDao.examPaperSum();
    }

    public int updateExamPaper(ExamPaperInfo examPaperInfo) {
        return examPaperDao.updateExamPaper(examPaperInfo);
    }

    public int addExamPaper(ExamPaperInfo examPaperInfo) {
        return examPaperDao.addExamPaper(examPaperInfo);
    }

    public ExamPaperInfo findById(Integer examPaperId) {
        return examPaperDao.findById(examPaperId);
    }

    public int delExamPaper(Integer examPaperId) {
        return examPaperDao.delExamPaper(examPaperId);
    }

    public int updateExamPaperSubjects(Map<String, Object> map) {
        return examPaperDao.updateExamPaperSubjects(map);
    }

    public int updateExamPaperScore(Map<String, Object> map) {
        return examPaperDao.updateExamPaperScore(map);
    }

    public List<ExamPaperInfo> findExamPapers() {
        return examPaperDao.findExamPapers();
    }

    public int maxPaperId() {
        return examPaperDao.maxPaperId();
    }

}
