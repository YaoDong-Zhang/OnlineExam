package online.exam.dao;

import online.exam.pojo.ExamPaperInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 18:20
 * @Author: Mr.Zhang
 * @Description:
 */
@Repository
public interface ExamPaperDao {
    public List<ExamPaperInfo> findAll(Map<String, Object> map);

    public int examPaperSum();

    public int updateExamPaper(ExamPaperInfo examPaperInfo);

    public int addExamPaper(ExamPaperInfo examPaperInfo);

    public ExamPaperInfo findById(Integer examPaperId);

    public int delExamPaper(Integer examPaperId);

    public int updateExamPaperSubjects(Map<String, Object> map);

    public int updateExamPaperScore(Map<String, Object> map);

    public List<ExamPaperInfo> findExamPapers();

    public int maxPaperId();

}
