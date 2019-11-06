package com.zzm.tomcatelevendemo.digester;

import org.apache.commons.digester3.Digester;

import java.net.URL;

/**
 * @author zhongzuoming <zhongzuoming, 1299076979@qq.com>
 * @version v1.0
 * @Description baipao
 * @encoding UTF-8
 * @date 2019-11-04
 * @time 09:53
 * @修改记录 <pre>
 * 版本       修改人         修改时间         修改内容描述
 * --------------------------------------------------
 * <p>
 * --------------------------------------------------
 * </pre>
 */
public class DigesterMain {

    public static void main(String[] args) {

        URL url =
                new Digester().getClassLoader().getResource("digester/example.xml");
        String filename = url.getFile();
        Digester d = new Digester();
        AddressBook book = new AddressBook();
        //把book放在栈顶
        d.push(book);
        //增加解析xml规则
        addRules(d);

        try {
            java.io.File srcfile = new java.io.File(filename);
            //解析文件
            d.parse(srcfile);
        } catch (java.io.IOException ioe) {
            System.out.println("Error reading input file:" + ioe.getMessage());
            System.exit(-1);
        } catch (org.xml.sax.SAXException se) {
            System.out.println("Error parsing input file:" + se.getMessage());
            System.exit(-1);
        }


        book.print();
    }

    private static void addRules(Digester d) {


        //增加创建对象规则 并添加
        d.addObjectCreate("address-book/person", Person.class);
        //设置属性规则
        d.addSetProperties("address-book/person");
        //当遇到结束标签时候经过层层递归到元素时候 调用该方法把值设置到上级 方法在接下标签开始时候不操作元素 在遇到结束标签才触发操作
        d.addSetNext("address-book/person", "addPerson");

        //设置该路径下 调用方法
        d.addCallMethod("address-book/person/name", "setName", 0);

        //设置该路径下 调用方法
        d.addCallMethod("address-book/person/email", "addEmail", 2);
        //设置该路径下 方法中参数
        d.addCallParam("address-book/person/email", 0, "type");
        //设置该路径下 方法中参数
        d.addCallParam("address-book/person/email", 1);

        //创建该路径下对象
        d.addObjectCreate("address-book/person/address", Address.class);

        d.addSetNext("address-book/person/address", "addAddress");
        d.addSetNestedProperties("address-book/person/address");
    }
}
