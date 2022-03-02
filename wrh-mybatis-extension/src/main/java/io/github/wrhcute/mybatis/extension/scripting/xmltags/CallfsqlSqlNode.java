package io.github.wrhcute.mybatis.extension.scripting.xmltags;

import io.github.wrhcute.utils.StrUtil;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName CallfsqlSqlNode.java
 * @Description TODO
 * @createTime 2022年03月02日 18:07:00
 */
public class CallfsqlSqlNode implements SqlNode {

    private final Configuration configuration;

    private final String func;

    private final String arg;

    CallfsqlSqlNode(Configuration configuration ,String func, String arg){
        this.func = func;
        this.arg = arg;
        this.configuration = configuration;
    }


    @Override
    public boolean apply(DynamicContext context) {
        XNode node = configuration.getSqlFragments().get(func);
        String argClass = node.getStringAttribute("argClass");
        if (StrUtil.isBlank(argClass)){

        }else{

        }
        return true;
    }
}
