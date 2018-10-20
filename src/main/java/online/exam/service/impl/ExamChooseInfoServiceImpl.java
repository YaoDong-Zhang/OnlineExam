package online.exam.service.impl;

import online.exam.dao.ExamChooseInfoDao;
import online.exam.pojo.ExamChooseInfo;
import online.exam.service.ExamChooseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/13 20:16
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class ExamChooseInfoServiceImpl implements ExamChooseInfoService {
    @Autowired
    private ExamChooseInfoDao examChooseInfoDao;

    public List<ExamChooseInfo> getChooseInfoWithExamSubject(Map<String, Object> map) {
        return examChooseInfoDao.getChooseInfoWithExamSubject(map);
    }

    public List<ExamChooseInfo> getChooseInfoWithSumScore(Map<String, Object> map) {
        return examChooseInfoDao.getChooseInfoWithSumScore(map);
    }

    public ExamChooseInfo getChooseWithIds(Map<String, Object> map) {
        return examChooseInfoDao.getChooseWithIds(map);
    }

    public int updateChooseWithIds(ExamChooseInfo examChoose) {
        return examChooseInfoDao.updateChooseWithIds(examChoose);
    }

    public int addChoose(Map<String, Object> map) {
        return examChooseInfoDao.addChoose(map);
    }
}
