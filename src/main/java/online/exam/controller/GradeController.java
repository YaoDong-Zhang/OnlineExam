package online.exam.controller;

import online.exam.pojo.GradeInfo;
import online.exam.service.GradeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: OnlineExam
 * @Date: 2018/10/6 12:54
 * @Author: Mr.Zhang
 * @Description:
 */
@Controller
public class GradeController {
    @Autowired
    private GradeInfo gradeInfo;
    @Autowired
    private GradeInfoService gradeInfoService;

    /**
     * 获取所有年级
     *
     * @return
     */
    @RequestMapping("/grades")
    public ModelAndView grades() {
        //------------日志
        System.out.println("获取所有年级");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/grades");
        List<GradeInfo> gradeInfos = gradeInfoService.findAll();
        modelAndView.addObject("grades", gradeInfos);
        return modelAndView;
    }

    /**
     * 预添加年级处理
     *
     * @param map
     * @return
     */
    @RequestMapping("/preAddGrade")
    public String preAddGrade(Map<String, Object> map) {
        //------------日志
        System.out.println("年级添加预处理");
        map.put("grade", new GradeInfo());
        return "admin/gradeedit";
    }

    /**
     * 添加年级信息
     *
     * @param grade
     * @param request
     * @return
     */
    @RequestMapping(value = "/grade", method = RequestMethod.POST)
    public String isAddGrade(GradeInfo grade, HttpServletRequest request) {
        //------------日志
        System.out.println("添加年级 " + grade);

        if (grade.getGradeName() == "") {
            //------------日志
            System.out.println("年级 " + grade + " 为空");
            request.setAttribute("error", "年级添加失败，请稍后再试！");
            return "error";
        }
        int row = gradeInfoService.AddGrade(grade);
        if (row < 1) {
            //------------日志
            System.out.println("年级添加失败");

            request.setAttribute("error", "年级添加失败，请稍后再试！");
            return "error";
        }
        return "redirect:grades";
    }

    /**
     * 预修改年级处理
     *
     * @param gradeId 待修改年级编号
     * @param map
     * @return
     */
    @RequestMapping(value = "/grade/update/{gradeId}", method = RequestMethod.GET)
    public String preUpdateGrade(@PathVariable("gradeId") Integer gradeId,
                                 Map<String, Object> map) {
        //------------日志
        System.out.println("年级修改预处理");
        map.put("grade", gradeInfoService.findById(gradeId));
        return "/admin/gradeedit";
    }

    /**
     * 修改年级信息
     *
     * @param grade
     * @param request
     * @return
     */
    @RequestMapping(value = "/grade/update/grade", method = RequestMethod.POST)
    public String isUpdateGrade(GradeInfo grade, HttpServletRequest request) {
        //------------日志
        System.out.println("修改年级 " + grade + " 的信息");
        int row = gradeInfoService.updateGrade(grade);
        if (row < 1) {
            //------------日志
            System.out.println("年级修改失败");
            request.setAttribute("error", "年级修改失败，请稍后再试！");
            return "/error";
        }
        return "redirect:/grades";
    }

    /**
     * 删除年级
     *
     * @param gradeId
     * @param request
     * @return
     */
    @RequestMapping(value = "grade/del/{gradeId}", method = RequestMethod.POST)
    public String isDelGrade(@PathVariable("gradeId") Integer gradeId, HttpServletRequest request) {
        //------------日志
        System.out.println("删除年级 " + gradeId);

        int row = gradeInfoService.delGrade(gradeId);
        if (row < 1) {
            //------------日志
            System.out.println("年级删除失败");

            request.setAttribute("error", "年级删除失败，请稍后再试！");
            return "/error";
        }

        return "redirect:/grades";
    }
}
