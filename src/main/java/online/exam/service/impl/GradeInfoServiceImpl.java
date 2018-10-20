package online.exam.service.impl;

import online.exam.dao.GradeInfoDao;
import online.exam.pojo.GradeInfo;
import online.exam.service.GradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 12:52
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class GradeInfoServiceImpl implements GradeInfoService {
    @Autowired
    private GradeInfoDao gradeInfoDao;

    public List<GradeInfo> findAll() {
        return gradeInfoDao.findAll();
    }

    public int AddGrade(GradeInfo gradeInfo) {
        return gradeInfoDao.AddGrade(gradeInfo);
    }

    public int updateGrade(GradeInfo gradeInfo) {
        return gradeInfoDao.updateGrade(gradeInfo);
    }

    public GradeInfo findById(Integer id) {
        return gradeInfoDao.findById(id);
    }

    public int delGrade(Integer id) {
        return gradeInfoDao.delGrade(id);
    }
}
