package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import entity.PageResult;
import entity.QueryPageBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/11 16:19
 * @see
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${out_put_path}")
    private String outPurPath;

    /**
     * 添加套餐
     *
     * @param checkGroupIds
     * @param setmeal
     */
    @Transactional
    @Override
    public void addSetmeal(Integer[] checkGroupIds, Setmeal setmeal) {
        setmealDao.addSetmeal(setmeal);
        setSetmealAndCheckGroup(checkGroupIds, setmeal.getId());

        generateMobileStaticHtml(setmeal.getId());
    }

    /**
     * 生成静态页面的方法
     */
    private void generateMobileStaticHtml(Integer setmealId) {
        List<Setmeal> setmeal = setmealDao.getSetmeal();
        //生成套餐列表页面
        generateMobileSetmealListHtml(setmeal);
        //生成套餐详情页面
        generateMobileSetmealDetailHtml(setmeal);
    }

    /**
     * 提供生成套餐详情页面的模板集数据
     *
     * @param setmeals
     */
    private void generateMobileSetmealDetailHtml(List<Setmeal> setmeals) {
        if (setmeals != null && setmeals.size() > 0) {
            for (Setmeal setmeal : setmeals) {
                Setmeal sm = setmealDao.findById(setmeal.getId());
                HashMap data = new HashMap();
                data.put("setmeal", sm);
                generateHtml("mobile_setmeal_detail.ftl", data,
                        "setmeal_detail_" + sm.getId() + ".html");
            }
        }
    }

    /**
     * 提供生成套餐列表页面的模板和数据
     *
     * @param setmealList
     */
    private void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        if (setmealList != null && setmealList.size() > 0) {
            HashMap data = new HashMap();
            data.put("setmealList", setmealList);
            generateHtml("mobile_setmeal.ftl", data, "m_setmeal.html");
        }
    }

    /**
     * 生成静态页面的公共方法
     *
     * @param templateFileName
     * @param data
     * @param outFileName
     */
    private void generateHtml(String templateFileName, HashMap data, String outFileName) {
        Writer writer = null;
        try {
            //第一步：创建一个 Configuration 对象，直接 new 一个对象。构造方法的参数就是 freemarker的版本号。
            Configuration configuration = freeMarkerConfigurer.getConfiguration();

            //第二步：设置模板文件所在的路径。
            configuration.setDirectoryForTemplateLoading(new File(
                    "E:/study/2020_Oct/health_parent/health_service/src/main/webapp/WEB-INF/ftl"));

            //第三步：设置模板文件使用的字符集。一般就是 utf-8。

            //第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate(templateFileName);

            //第五步：创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map。

            //第六步：创建一个 Writer 对象，一般创建 FileWriter 对象，指定生成的文件名。
            writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outPurPath + "/" + outFileName)));
            //第七步：调用模板对象的 process 方法输出文件。
            template.process(data, writer);
            //第八步：关闭流
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 分页查询套餐
     *
     * @param pagination
     * @return
     */
    @Override
    public PageResult findPage(QueryPageBean pagination) {
        PageHelper.startPage(pagination.getCurrentPage(), pagination.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(pagination.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据套餐ID回显数据
     *
     * @param setmealId
     * @return
     */
    @Override
    public Setmeal findSetmealById(Integer setmealId) {
        return setmealDao.findSetmealById(setmealId);
    }

    /**
     * 查询套餐对应的检查组
     *
     * @param setmealId
     * @return
     */
    @Override
    public Integer[] findSetmealAndCheckGroupById(Integer setmealId) {
        return setmealDao.findSetmealAndCheckGroupById(setmealId);
    }

    /**
     * 编辑套餐
     *
     * @param checkGroupIds
     * @param setmeal
     */
    @Transactional
    @Override
    public void editSetmeal(Integer[] checkGroupIds, Setmeal setmeal) {
        setmealDao.editSetmeal(setmeal);
        setmealDao.deleteSetmealAndCheckGroupById(setmeal.getId());
        setSetmealAndCheckGroup(checkGroupIds, setmeal.getId());
    }

    /**
     * 删除套餐
     *
     * @param setmealId
     */
    @Override
    public void deleteSetmeal(Integer setmealId) {
        Integer[] CheckGroupIds = findSetmealAndCheckGroupById(setmealId);
        if (CheckGroupIds.length > 0) {
            throw new RuntimeException(MessageConstant.DELETE_CHECKGROUP_CHECKITEM_FAIL);
        } else {
            setmealDao.deleteSetmeal(setmealId);
        }
    }

    /**
     * 移动端获取所有套餐
     *
     * @return
     */
    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    /**
     * 获取移动端套餐详情
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    /**
     * 查询会员预约的各个套餐占比情况
     *
     * @return
     */
    @Override
    public Map getSetmealReport() {
        Map map = new HashMap<>();
        List<Map> list = setmealDao.getSetmealReport();
        ArrayList<String> names = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Map m : list) {
                names.add(m.get("name").toString());
            }
        }
        map.put("setmealNames", names);
        map.put("setmealCount", list);
        return map;
    }

    /**
     * 往套餐和检查组的中间表添加对应关系
     *
     * @param checkGroupIds
     * @param setmealId
     */
    private void setSetmealAndCheckGroup(Integer[] checkGroupIds, Integer setmealId) {
        if (checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                HashMap map = new HashMap();
                map.put("checkGroupId", checkGroupId);
                map.put("setmealId", setmealId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
