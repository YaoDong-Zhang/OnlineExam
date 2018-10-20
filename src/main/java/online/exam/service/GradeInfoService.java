package online.exam.service;

import online.exam.pojo.GradeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 12:51
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface GradeInfoService {
    public List<GradeInfo> findAll();

    public int AddGrade(GradeInfo gradeInfo);

    public int updateGrade(GradeInfo gradeInfo);

    public GradeInfo findById(Integer id);

    public int delGrade(Integer id);
}
