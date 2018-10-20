package online.exam.service;

import online.exam.pojo.SubjectInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/7 13:04
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface SubjectInfoService {
    public List<SubjectInfo> findAll(Map<String, Object> map);

    public int subjectSum();

    public SubjectInfo findById(Integer id);

    public int addSubject(SubjectInfo subject);

    public int addSubjects(Map<String, Object> map);

    public int updateSubject(SubjectInfo subjectInfo);

    public int delSubject(Integer id);

}
