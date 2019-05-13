package org.beetl.core.om.asm;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.tree.FieldNode;

/**
 * asm生成的类描述
 * @author laozhaishaozuo@foxmail.com
 *
 */
class ClassDescription {

	Class target = null;

	Map<Integer, List<FieldNode>> fieldMap;

//	Set<String> methodNameDescSet;

	Map<Integer, List<PropertyDescriptor>> propertyMap;


	//0 ,1 get(Object),2 get(String)
	 int  generalGetType = 0;

}
