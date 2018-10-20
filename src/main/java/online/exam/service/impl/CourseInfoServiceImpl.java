package online.exam.service.impl;

import online.exam.dao.CourseInfoDao;
import online.exam.pojo.CourseInfo;
import online.exam.service.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 13:09
 * @Author: Mr.Zhang
 * @Description:
 */
@Service
public class CourseInfoServiceImpl implements CourseInfoService {
    @Autowired
    private CourseInfoDao courseInfoDao;

    public List<CourseInfo> findAll(CourseInfo courseInfo) {
        return courseInfoDao.findAll(courseInfo);
    }

    public CourseInfo findById(Integer id) {
        return courseInfoDao.findById(id);
    }

    public int AddCourse(CourseInfo courseInfo) {
        return courseInfoDao.AddCourse(courseInfo);
    }

    public int updateCourse(CourseInfo courseInfo) {
        return courseInfoDao.updateCourse(courseInfo);
    }

    public int delCourse(Integer id) {
        return courseInfoDao.delCourse(id);
    }
}
