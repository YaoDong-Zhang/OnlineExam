package online.exam.dao;

import online.exam.pojo.CourseInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 12:42
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface CourseInfoDao {
    public List<CourseInfo> findAll(CourseInfo courseInfo);

    public CourseInfo findById(Integer id);

    public int AddCourse(CourseInfo courseInfo);

    public int updateCourse(CourseInfo courseInfo);

    public int delCourse(Integer id);
}
