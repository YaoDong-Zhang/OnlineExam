package online.exam.service.impl;

import online.exam.dao.SubjectInfoDao;
import online.exam.pojo.SubjectInfo;
import online.exam.service.SubjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/7 13:05
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {
    @Autowired
    private SubjectInfoDao subjectInfoDao;

    public List<SubjectInfo> findAll(Map<String, Object> map) {
        return subjectInfoDao.findAll(map);
    }

    public int subjectSum() {
        return subjectInfoDao.subjectSum();
    }

    public SubjectInfo findById(Integer id) {
        return subjectInfoDao.findById(id);
    }

    public int addSubject(SubjectInfo subject) {
        return subjectInfoDao.addSubject(subject);
    }

    public int addSubjects(Map<String, Object> map) {
        return subjectInfoDao.addSubjects(map);
    }

    public int updateSubject(SubjectInfo subjectInfo) {
        return subjectInfoDao.updateSubject(subjectInfo);
    }

    public int delSubject(Integer id) {
        return subjectInfoDao.delSubject(id);
    }
}
