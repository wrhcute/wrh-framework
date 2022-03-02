package io.github.wrhcute.mybatis.extension.scripting.xmltags;

import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLScriptBuilder;
import org.apache.ibatis.session.Configuration;

import java.util.List;

/**
 * @author 王瑞鸿
 * @version 1.0.0
 * @ClassName CallfsqlHandler.java
 * @Description TODO
 * @createTime 2022年03月02日 17:38:00
 */
public class CallfsqlHandler implements XMLScriptBuilder.NodeHandler {
    private final XMLScriptBuilder builder;
    private final Configuration configuration;


    public CallfsqlHandler(XMLScriptBuilder builder,Configuration configuration) {
        this.builder = builder;
        this.configuration = configuration;
    }

    @Override
    public void handleNode(XNode nodeToHandle, List<SqlNode> targetContents) {
        String func = nodeToHandle.getStringAttribute("fsql");
        String arg = nodeToHandle.getStringAttribute("arg");
        SqlNode node = new CallfsqlSqlNode(configuration,func, arg);
        targetContents.add(node);
    }
}
