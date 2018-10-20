package online.exam.service.impl;

import online.exam.dao.ExamSubjectMiddleInfoDao;
import online.exam.pojo.ExamSubjectMiddleInfo;
import online.exam.service.ExamSubjectMiddleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 14:56
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ExamSubjectMiddleInfoServiceImpl implements ExamSubjectMiddleInfoService {
    @Autowired
    private ExamSubjectMiddleInfoDao subjectMiddleInfoDao;

    public List<ExamSubjectMiddleInfo> findSubject(ExamSubjectMiddleInfo esm) {
        return subjectMiddleInfoDao.findSubject(esm);
    }

    public int addESM(Map<String, Object> map) {
        return subjectMiddleInfoDao.addESM(map);
    }

    public int removeSubjectWithExamPaper(Map<String, Object> map) {
        return subjectMiddleInfoDao.removeSubjectWithExamPaper(map);
    }

    public Integer getEsmByExamIdWithSubjectId(ExamSubjectMiddleInfo esm) {
        return subjectMiddleInfoDao.getEsmByExamIdWithSubjectId(esm);
    }
}
