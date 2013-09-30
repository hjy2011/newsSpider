/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ClawerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author SNA3
 */
public class ReglarExpression {

	/**
	 * ����������ʽƥ���ȫ������
	 * @param regex ������ʽ
	 * @param source Դ�ַ���
	 * @return ƥ�����ַ���
	 */
    public static String Regular(String regex, String source) {
        return Regular(regex, source, 0);
    }
    
    /**
     * ��source���ݽ�������ƥ�䣬������ƥ�����ַ���
     * @param regex ������ʽ
     * @param source ƥ������
     * @param groupNum ƥ����飬�ڼ���������������ݡ������0���򷵻�ȫ���������1�򷵻ص�һ��Բ����
     * 					��������� 
     * @return ���ر��ʽƥ�����ַ���
     */
    public static String Regular(String regex, String source, int groupNumber)
    {
    	String content = "";
        final List<String> list = new ArrayList<String>();
        final Pattern pa = Pattern.compile(regex);
        final Matcher ma = pa.matcher(source);
        while (ma.find()) {
            list.add(ma.group(groupNumber));
        }
        for (int i = 0; i < list.size(); i++) {
            content = content + list.get(i);
        }
        return content;
    }

    

    public static ArrayList<String> RegularArray(String regex, String source) {
        return RegularArray(regex, source, 0);
    }
    
    public static ArrayList<String> RegularArray(String regex, String source, int groupNumber)
    {
    	final ArrayList<String> list = new ArrayList<String>();
        final Pattern pa = Pattern.compile(regex);
        final Matcher ma = pa.matcher(source);
        while (ma.find()) {
            list.add(ma.group(groupNumber));
        }
        return list;
    }
}
