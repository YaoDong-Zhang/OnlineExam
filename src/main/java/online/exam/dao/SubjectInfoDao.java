package online.exam.dao;

import online.exam.pojo.SubjectInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/7 13:03
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface SubjectInfoDao {
    public List<SubjectInfo> findAll(Map<String, Object> map);

    public int subjectSum();

    public SubjectInfo findById(Integer id);

    public int addSubject(SubjectInfo subject);

    //批量添加试题
    public int addSubjects(Map<String, Object> map);

    public int updateSubject(SubjectInfo subjectInfo);

    public int delSubject(Integer id);
}
