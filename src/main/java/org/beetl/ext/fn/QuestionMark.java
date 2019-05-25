package org.beetl.ext.fn;

import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * <pre>
 * ${qmark(score&lt;60,"及格","不及格")}
 * </pre>
 * @author 一颗草,(最早Beetl使用者提供功能的扩展，其实当时3元表达式已经支持了,留个纪念，闲大赋予，2019)
 */
public class QuestionMark implements Function
{

	public Object call(Object[] paras, Context ctx)
	{

		if (paras.length != 3)
		{
			throw new RuntimeException("请输入问号表达式格式： qmark(a==\"a\",\"yes\",\"no\")");
		}
		else
		{
			if (paras.length == 3)
			{
				if ((Boolean) paras[0])
				{
					return paras[1];
				}
				else
				{
					return paras[2];
				}
			}

			throw new RuntimeException("Parse split Error,Args Object,Object,Object ");
		}
	}

}
